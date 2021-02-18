package com.karson.lib_commen.util

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

object IdCardUtil {
    private val ValCodeArr =
        arrayOf("1", "0", "x", "9", "8", "7", "6", "5", "4", "3", "2")
    private val Wi = arrayOf(
        "7",
        "9",
        "10",
        "5",
        "8",
        "4",
        "2",
        "1",
        "6",
        "3",
        "7",
        "9",
        "10",
        "5",
        "8",
        "4",
        "2"
    )

    // 身份证的最小出生日期,1900年1月1日
    private val MINIMAL_BIRTH_DATE = Date(-2209017600000L)
    private const val BIRTH_DATE_FORMAT = "yyyyMMdd"
    private const val NEW_CARD_NUMBER_LENGTH = 18
    private const val OLD_CARD_NUMBER_LENGTH = 15
    private const val LENGTH_ERROR = "身份证长度必须为15或者18位！"
    private const val NUMBER_ERROR = "15位身份证都应该为数字，18位身份证都应该前17位应该都为数字！"
    private const val DATE_ERROR = "身份证日期验证无效！"
    private const val AREA_ERROR = "身份证地区编码错误!"
    private const val CHECKCODE_ERROR = "身份证最后一位校验码有误！"

    //是否需要返回自动补全成的身份证
    private var isNeedReturn_AutoCard = false

    /**
     *
     * @param idcardNumber 需要验证的身份证
     * @param isreturn_AutoCard 验证无误后，是否需要返回自动补全身份证
     * @return 身份证无误返回传入的身份证号
     */
    fun validateEffective(
        idcardNumber: String,
        isreturn_AutoCard: Boolean
    ): String {
        isNeedReturn_AutoCard = isreturn_AutoCard
        return validateEffective(idcardNumber)
    }

    /**
     * 身份证校验
     * @param idcardNumber 需要验证的身份证
     * @return 身份证无误返回传入的身份证号
     */
    fun validateEffective(idcardNumber: String): String {
        var Ai = idcardNumber.trim { it <= ' ' }
        if (Ai.length == 15 || Ai.length == 18) {
            //如果为15位则自动补全到18位
            if (Ai.length == OLD_CARD_NUMBER_LENGTH) {
                Ai = contertToNewCardNumber(Ai)
            }
        } else {
            return LENGTH_ERROR
        }
        // 身份证号的前15,17位必须是阿拉伯数字
        for (i in 0 until NEW_CARD_NUMBER_LENGTH - 1) {
            val ch = Ai[i]
            if (ch < '0' || ch > '9') {
                return NUMBER_ERROR
            }
        }
        //校验身份证日期信息是否有效 ，出生日期不能晚于当前时间，并且不能早于1900年
        try {
            val birthDate = getBirthDate(Ai) ?: return DATE_ERROR
            if (!birthDate.before(Date())) {
                return DATE_ERROR
            }
            if (!birthDate.after(MINIMAL_BIRTH_DATE)) {
                return DATE_ERROR
            }
            /**
             * 出生日期中的年、月、日必须正确,比如月份范围是[1,12],日期范围是[1,31]，还需要校验闰年、大月、小月的情况时，
             * 月份和日期相符合
             */
            val birthdayPart = getBirthDayPart(Ai)
            val realBirthdayPart =
                createBirthDateParser().format(birthDate)
            if (birthdayPart != realBirthdayPart) {
                return DATE_ERROR
            }
        } catch (e: Exception) {
            return DATE_ERROR
        }
        //校验地区码是否正确
        val h = GetAreaCode()
        if (h[Ai.substring(0, 2)] == null) {
            return AREA_ERROR
        }
        //校验身份证最后一位 身份证校验码
        if (calculateVerifyCode(Ai) != Ai[NEW_CARD_NUMBER_LENGTH - 1]
                .toString()
        ) {
            return CHECKCODE_ERROR
        }
        return ""
    }

    /**
     * 把15位身份证号码转换到18位身份证号码<br></br>
     * 15位身份证号码与18位身份证号码的区别为：<br></br>
     * 1、15位身份证号码中，"出生年份"字段是2位，转换时需要补入"19"，表示20世纪<br></br>
     * 2、15位身份证无最后一位校验码。18位身份证中，校验码根据根据前17位生成
     *
     * @param cardNumber
     * @return
     */
    private fun contertToNewCardNumber(oldCardNumber: String): String {
        val buf =
            StringBuilder(NEW_CARD_NUMBER_LENGTH)
        buf.append(oldCardNumber.substring(0, 6))
        buf.append("19")
        buf.append(oldCardNumber.substring(6))
        buf.append(calculateVerifyCode(buf))
        return buf.toString()
    }

    /**计算最后一位校验码  加权值%11
     * （1）十七位数字本体码加权求和公式 S = Sum(Ai * Wi), i = 0, ... , 16 ，先对前17位数字的权求和
     * Ai:表示第i位置上的身份证号码数字值 Wi:表示第i位置上的加权因子 Wi: 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4
     * （2）计算模 Y = mod(S, 11)
     * （3）通过模得到对应的校验码 Y: 0 1 2 3 4 5 6 7 8 9 10 校验码: 1 0 X 9 8 7 6 5 4 3 2
     * @param cardNumber
     * @return
     */
    private fun calculateVerifyCode(cardNumber: CharSequence): String {
        var sum = 0
        for (i in 0 until NEW_CARD_NUMBER_LENGTH - 1) {
            val ch = cardNumber[i]
            sum += (ch - '0') * Wi[i].toInt()
        }
        return ValCodeArr[sum % 11]
    }

    /**
     * 功能：设置地区编码
     *
     * @return Hashtable 对象
     */
    private fun GetAreaCode(): Hashtable<String, String?> {
        val hashtable =
            Hashtable<String, String?>()
        hashtable["11"] = "北京"
        hashtable["12"] = "天津"
        hashtable["13"] = "河北"
        hashtable["14"] = "山西"
        hashtable["15"] = "内蒙古"
        hashtable["21"] = "辽宁"
        hashtable["22"] = "吉林"
        hashtable["23"] = "黑龙江"
        hashtable["31"] = "上海"
        hashtable["32"] = "江苏"
        hashtable["33"] = "浙江"
        hashtable["34"] = "安徽"
        hashtable["35"] = "福建"
        hashtable["36"] = "江西"
        hashtable["37"] = "山东"
        hashtable["41"] = "河南"
        hashtable["42"] = "湖北"
        hashtable["43"] = "湖南"
        hashtable["44"] = "广东"
        hashtable["45"] = "广西"
        hashtable["46"] = "海南"
        hashtable["50"] = "重庆"
        hashtable["51"] = "四川"
        hashtable["52"] = "贵州"
        hashtable["53"] = "云南"
        hashtable["54"] = "西藏"
        hashtable["61"] = "陕西"
        hashtable["62"] = "甘肃"
        hashtable["63"] = "青海"
        hashtable["64"] = "宁夏"
        hashtable["65"] = "新疆"
        hashtable["71"] = "台湾"
        hashtable["81"] = "香港"
        hashtable["82"] = "澳门"
        hashtable["91"] = "国外"
        return hashtable
    }

    private fun getBirthDate(idcard: String): Date {
        var cacheBirthDate: Date? = null
        cacheBirthDate = try {
            createBirthDateParser().parse(getBirthDayPart(idcard))
        } catch (e: Exception) {
            throw RuntimeException("身份证的出生日期无效")
        }
        return Date(cacheBirthDate!!.time)
    }

    private fun createBirthDateParser(): SimpleDateFormat {
        return SimpleDateFormat(BIRTH_DATE_FORMAT)
    }

    private fun getBirthDayPart(idcardnumber: String): String {
        return idcardnumber.substring(6, 14)
    }

    fun formatMoney(money: String) :String{
        return if (money.startsWith("0.")){
            money
        }else{
            val df = DecimalFormat(",###.00") //格式化小数
            df.roundingMode = RoundingMode.FLOOR //不四舍五入
            df.format(BigDecimal(money))
        }
    }
}