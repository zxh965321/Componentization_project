package com.karson.supper.utils.callback

import com.karson.supper.usermanager.IGlobalCallback
import java.util.*

/**
 * Created by Lzy on 2020/8/22.
 */
object CallbackManager {

    private val CALLBACKS =
        WeakHashMap<Any, IGlobalCallback>()


    fun addCallback(tag: String, callback: IGlobalCallback) {
        CALLBACKS[tag] = callback
    }

    fun getCallback(tag: String): IGlobalCallback? {
        return CALLBACKS[tag]
    }
}