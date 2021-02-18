package com.karson.lib_commen.util

import java.math.BigDecimal

object DoubleUtil {

    // 需要精确至小数点后几位
    private const val DECIMAL_POINT_NUMBER: Int = 2

    fun toBigDecimal(data: Any): BigDecimal {
        return when (data) {
            is Float -> BigDecimal(data.toString())
            is Double -> BigDecimal(data)
            is Int -> BigDecimal(data)
            is String -> BigDecimal(data)
            is Long -> BigDecimal(data)
            is Short -> BigDecimal(data.toInt())
            else -> BigDecimal(data.toString())
        }.apply {
            setScale(
                DECIMAL_POINT_NUMBER, BigDecimal.ROUND_DOWN
            )
        }
    }

    // 加法运算
    @JvmStatic
    fun add(d1: Any, d2: Any): String = toBigDecimal(d1).add(toBigDecimal(d2)).setScale(
        DECIMAL_POINT_NUMBER, BigDecimal.ROUND_DOWN
    ).toString()

    // 减法运算
    @JvmStatic
    fun sub(d1: Any, d2: Any): String = toBigDecimal(d1).subtract(toBigDecimal(d2)).setScale(
        DECIMAL_POINT_NUMBER, BigDecimal.ROUND_DOWN
    ).toString()

    // 乘法运算
    @JvmStatic
    fun mul(d1: Any, d2: Any): String = toBigDecimal(d1).multiply(toBigDecimal(d2)).setScale(
        DECIMAL_POINT_NUMBER, BigDecimal.ROUND_DOWN
    ).toString()

    // 除法运算
    @JvmStatic
    fun div(d1: Any, d2: Any): String = toBigDecimal(d1).divide(toBigDecimal(d2)).setScale(
        DECIMAL_POINT_NUMBER, BigDecimal.ROUND_DOWN
    ).toString()

}

//支持bigDecimal相加
public inline fun <T> Iterable<T>.sumByBigDecimal(selector: (T) -> Double): Double {
    var sum: String = "0.00"
    for (element in this) {
        //避免截短
        sum = DoubleUtil.add(sum, element.toString())
    }
    return sum.toDouble()
}