package com.karson.lib_commen.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * @Author karson
 * @Date 2020/8/18-09:34
 * @desc 获取当前时间
 */
object CalendarUtils {

    private val calendar:Calendar = Calendar.getInstance()

    /**
     * 获取当前年
     */
    fun getYear():Int{
        return calendar.get(Calendar.YEAR)
    }

    /**
     * 获取当前月
     */
    fun getMonth():Int{
        return calendar.get(Calendar.MONTH)+1
    }

    /**
     * 获取当前日
     */
    fun getDay():Int{
        return calendar.get(Calendar.DAY_OF_MONTH)
    }

    /**
     * 获取当前小时
     */
    fun getTimeHour():Int{
        return calendar.get(Calendar.HOUR_OF_DAY)
    }

    /**
     * 获取当前分钟数
     */
    fun getTimeMinute():Int{
        return calendar.get(Calendar.MINUTE)
    }

    /**
     * 获取当前秒
     */
    fun getSecond():Int{
        return calendar.get(Calendar.SECOND)
    }
    /**
     * 获取当前年月
     */
    fun getData(splitStr:String):String = getYear().toString()+splitStr+ getMonth().toString()

    /**
     * 获取当前年月日
     */
    fun getDataM(splitStr:String):String = getYear().toString()+splitStr+ getMonth().toString() +splitStr+ getDay().toString()

    /**
     * 获取当前时分
     */
    fun getDataTb(splitStr:String):String = getTimeHour().toString()+splitStr+ getTimeMinute().toString()

    /**
     * 获取当前时分秒
     */
    fun getDataTall(splitStr:String):String = getTimeHour().toString()+splitStr+ getTimeMinute().toString() +splitStr+ getSecond().toString()

    /**
     * 获取当前时间
     */
    fun getTimeAll():String{
        val dataformat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        calendar.timeInMillis = System.currentTimeMillis()
        return  dataformat.format(calendar.time)
    }
     /**
     * 获取当前时间
     */
    fun getTimeYearToDay():String{
        val dataformat = SimpleDateFormat("yyyy-MM-dd")
        calendar.timeInMillis = System.currentTimeMillis()
        return  dataformat.format(calendar.time)
    }
     /**
     * 获取当前时间
     */
    fun getTimeYearAndMonth():String{
        val dataformat = SimpleDateFormat("yyyy-MM")
        calendar.timeInMillis = System.currentTimeMillis()
        return  dataformat.format(calendar.time)
    }
     /**
     * 获取当前时间
     */
    fun getTimeHoreToSecond():String{
        val dataformat = SimpleDateFormat("HH:mm:ss")
        calendar.timeInMillis = System.currentTimeMillis()
        return  dataformat.format(calendar.time)
    }
     /**
     * 获取当前时间
     */
    fun getTimeHoreAndMill():String{
        val dataformat = SimpleDateFormat("HH:mm")
        calendar.timeInMillis = System.currentTimeMillis()
        return  dataformat.format(calendar.time)
    }


}