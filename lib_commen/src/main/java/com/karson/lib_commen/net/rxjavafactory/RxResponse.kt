/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.karson.lib_commen.net.rxjavafactory

import com.google.gson.Gson
import okhttp3.Headers
import retrofit2.Response

/**
 *
 * 简单重写方便调用
 * @author chentong
 *
 */
class RxResponse<T> {

    private val UNKNOWN_ERROR = 1000
    var code: Int = 200
    var message: String? = ""
    var data: T? = null
    var error: Throwable? = null
    var errorResp: ErrorResp? = null
    var headers: Headers? = null

    fun create(error: Throwable) {
        this.code = UNKNOWN_ERROR
        this.message = error.message ?: "unknown error"
    }

    fun create(response: Response<T>) {
        this.code = response.code()
        this.message = response.message()
        this.headers = response.headers()

        if (response.isSuccessful) {
            this.data = response.body()
        } else if (response.code() == 400) {
            var errorMsg = response.errorBody()?.string()
            if (!errorMsg.isNullOrEmpty()) {
                try {
                    errorResp = Gson().fromJson(errorMsg, ErrorResp::class.java)
                }catch (e:Exception){
                }
            }
        } else {
            val msg = response.errorBody()?.string()
            val errorMsg = if (msg.isNullOrEmpty()) {
                response.message()
            } else {
                msg
            }
            this.message = errorMsg
        }
    }

    fun isError(): Boolean {
        if (code == UNKNOWN_ERROR) {
            return true
        }
        return false
    }

    fun isSuccessful(): Boolean {
        if (code in 200..299) {
            return true
        }
        return false
    }

}

/**
 * 通用返回错误报文
 */
data class ErrorResp(var code: String = "",
                     var message: String = "",
                     var extra: String = "")