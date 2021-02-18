package com.karson.lib_commen.util

import android.graphics.Paint
import android.os.Build
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.regex.Matcher
import java.util.regex.Pattern
import java.util.regex.Pattern.*

/**
 * @Author karson
 * @Date 2020/4/23-上午9:07
 * @desc
 */
object StringNullUtil {

    /**
     * checknull
     */
    fun checkNull(obj: String?): Boolean {
        return obj == null || obj.equals("")
    }

    /**
     * 手机号脱敏
     */
    fun phoneNumDesensitization(obj: String?): String {
        if (obj != "") {
            obj?.let {
                return if (obj.length == 11)
                    it.substring(0, 3) + " **** " + it.substring(it.length - 4, it.length)
                else obj
            }
        }
        return ""
    }

    /**
     * @return 返回指定笔和指定字符串的长度
     */
    @JvmStatic
    fun getFontLength(paint: Paint, str: String?): Float {
        return paint.measureText(str)
    }

    /**
     * @return 返回指定笔的文字高度
     */
    @JvmStatic
    fun getFontHeight(paint: Paint): Float {
        val fm = paint.fontMetrics
        return fm.descent - fm.ascent
    }

    /**
     * @return 返回指定笔离文字顶部的基准距离
     */
    @JvmStatic
    fun getFontLeading(paint: Paint): Float {
        val fm = paint.fontMetrics
        return fm.leading - fm.ascent
    }

    /**
     *字符串yyyy-MM-dd HH:mm:ss格式化为yyyy-MM-dd日期格式
     */
    fun getFormatDate(date: String?): String? {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                var formatter =
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                val newDate = formatter.parse(date)
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                formatter.format(newDate)
            } else {
                var formatter =
                    SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                val newDate = formatter.parse(date)
                formatter = SimpleDateFormat("yyyy-MM-dd")
                newDate?.let {
                    formatter.format(newDate)
                }
            }
        } catch (e: ParseException) {
            e.printStackTrace()
            ""
        }
    }

    /**
     *字符串yyyy-MM-dd HH:mm:ss格式化为yyyy-MM-dd HH:mm日期格式
     */
    fun getFormatDate1(date: String?): String? {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                var formatter =
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                val newDate = formatter.parse(date)
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                formatter.format(newDate)
            } else {
                var formatter =
                    SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                val newDate = formatter.parse(date)
                formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
                newDate?.let {
                    formatter.format(newDate)
                }
            }
        } catch (e: ParseException) {
            e.printStackTrace()
            ""
        }
    }

    /**
     * 手机号3位一空格
     */
    fun phoneNumFormat(obj: String?): String {
        obj?.let {
            return if (it.length == 11) {
                it.substring(0, 3) + " " + it.substring(3, 7) + " " + it.substring(
                    7, it.length
                )
            } else {
                it
            }
        }
        return ""
    }

    /**
     * 4位一空格
     */
    fun numFormat(obj: String?): String {
        obj?.let {
            val sb = StringBuilder(obj)
            run {
                var i = 4
                while (i < sb.length) {
                    sb.insert(i, ' ')
                    i += 5
                }
            }
            return sb.toString()
        }
        return ""
    }

    /**
     * 获取字符串的长度，如果有中文，则每个中文字符计为2位
     * @param value 指定的字符串
     * @return 字符串的长度
     */
    fun length(value: String?): Int {
        var valueLength = 0
        val chinese = "[\u4E00-\u9FBF]"
        /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        for (i in value!!.indices) {
            /* 获取一个字符 */
            val temp = value.substring(i, i + 1)
            /* 判断是否为中文字符 */
            valueLength += if (temp.matches(Regex(chinese))) {
                /* 中文字符长度为2 */
                2
            } else {
                /* 其他字符长度为1 */
                1
            }
        }
        return valueLength
    }

    fun deTailedZero(str: String?): String {
        str?.let {
            if (it.contains(".")) {
                var st = it
                lopp@ for (i in st.length - 1 downTo 0) {
                    if (st.lastIndexOf("0") == i || str.lastIndexOf(".") == i) {
                        st = it.substring(0, i)
                    } else break@lopp
                }
                return st
            } else
                return it
        }
        return ""
    }

    /**
     * [卡号] 前4位+********+后4位，其余隐藏为*号
     * <例子></例子>：6229**********1115>
     * @param cardNo 身份证号、银行卡号、证件编号
     * @return 小于等于8位的直接返回
     */
    fun hideCarNo(cardNo: String): String? {
        val regex = "(\\w{4})(.*)(\\w{4})"
        val m: Matcher = compile(regex).matcher(cardNo)
        return if (m.find()) {
            val rep: String = m.group(2)
            val sb = java.lang.StringBuilder()
            for (i in rep.indices) {
                sb.append("*")
            }
            cardNo.replace(rep, sb.toString())
        } else cardNo
    }

    /**
     * 身份证号：中间年月日脱敏
     */
    fun hideIdCarNo(cardNo: String): String? {
        val regex = "(\\w{6})(.*)(\\w{4})"
        val m: Matcher = compile(regex).matcher(cardNo)
        return if (m.find()) {
            val rep: String = m.group(2)
            val sb = java.lang.StringBuilder()
            for (i in rep.indices) {
                sb.append("*")
            }
            cardNo.replace(rep, sb.toString())
        } else cardNo
    }
}