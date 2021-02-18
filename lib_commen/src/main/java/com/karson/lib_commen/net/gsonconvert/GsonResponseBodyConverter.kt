package com.karson.lib_commen.net.gsonconvert

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import okhttp3.ResponseBody
import retrofit2.Converter
import java.io.IOException
import java.lang.reflect.*
import java.lang.reflect.Array
import kotlin.jvm.Throws

/**
 *
 * 兼容返回报文不是json
 * 数据类型为String.class  Any.class
 * 返回string字符串
 *
 */
internal class GsonResponseBodyConverter<T> : Converter<ResponseBody, T> {

    var gson: Gson
    var adapter: TypeAdapter<T>
    var type: Type

    constructor(gson: Gson, adapter: TypeAdapter<T>, type: Type) {
        this.gson = gson
        this.adapter = adapter
        this.type = type
    }

    @Throws(IOException::class)
    override fun convert(value: ResponseBody): T {

        var clazz = getRawType(type)

        try {
            if (clazz == String::class.java || clazz == Any::class.java) {
                return value.string().toString() as T
            } else {
                val jsonReader = gson.newJsonReader(value.charStream())
                return adapter.read(jsonReader)
            }
        } finally {
            value.close()
        }
    }

    //根据type获得数据类型
    private fun getRawType(type: Type): Class<*> {
//        checkNotNull(type, "type == null")

        if (type is Class<*>) {
            // Type is a normal class.
            return type
        }
        if (type is ParameterizedType) {

            // I'm not exactly sure why getRawType() returns Type instead of Class. Neal isn't either but
            // suspects some pathological case related to nested classes exists.
            return type.rawType as? Class<*> ?: throw IllegalArgumentException()
        }
        if (type is GenericArrayType) {
            val componentType = type.genericComponentType
            return Array.newInstance(getRawType(componentType), 0).javaClass
        }
        if (type is TypeVariable<*>) {
            // We could use the variable's bounds, but that won't work if there are multiple. Having a raw
            // type that's more general than necessary is okay.
            return Any::class.java
        }
        if (type is WildcardType) {
            return getRawType(type.upperBounds[0])
        }

        throw IllegalArgumentException("Expected a Class, ParameterizedType, or "
                + "GenericArrayType, but <" + type + "> is of type " + type.javaClass.name)
    }

}