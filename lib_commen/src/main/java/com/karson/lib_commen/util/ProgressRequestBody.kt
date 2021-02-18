package com.karson.lib_commen.util

import android.annotation.SuppressLint
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import okio.*
import java.nio.Buffer


class ProgressRequestBody(
    private val requestBody: RequestBody,
    private val listener: ((Long) -> Unit)
) : RequestBody() {

    private var byteBufferSink: BufferedSink? = null

    override fun contentType(): MediaType? {
        return requestBody.contentType()
    }

    override fun contentLength(): Long {
        return requestBody.contentLength()
    }

    override fun writeTo(sink: BufferedSink) {
        if (sink is Buffer) {
            // Log Interceptor
            requestBody.writeTo(sink);
            return
        }
        if (byteBufferSink == null)
            byteBufferSink = sink(sink).buffer()
        requestBody.writeTo(byteBufferSink!!)
        byteBufferSink?.flush()
    }

    private fun sink(sink: Sink) = object : ForwardingSink(sink) {

        private var count = 0L
        private var total = -1L
        override fun write(source: okio.Buffer, byteCount: Long) {
            if (total == -1L) total = contentLength()
            super.write(source, byteCount)
            count += byteCount
            val progress = count / total.toDouble() * 100
            Observable.just(count).observeOn(AndroidSchedulers.mainThread()).subscribe {
                listener.invoke(progress.toLong())
            }
        }
    }
}