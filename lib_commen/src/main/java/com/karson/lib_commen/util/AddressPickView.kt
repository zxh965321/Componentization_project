package com.karson.lib_commen.util

import android.content.Context
import android.content.res.AssetManager
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.os.Message
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.bigkoo.pickerview.view.OptionsPickerView
import com.contrarywind.interfaces.IPickerViewData
import com.google.gson.Gson
import com.karson.lib_commen.app.CommenApp
import org.json.JSONArray
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.*

/**
 *
 * TODO pickview 不建议用kotlin写
 * @author chentong
 *
 */
object AddressPickView {
    //国家 中国
    val country = 1

    var option1Item = ArrayList<AddressItem>()
    var option2Item = ArrayList<ArrayList<AddressItem>>()
    var option3Item = ArrayList<ArrayList<ArrayList<AddressItem>>>()

    private val MSG_LOAD_DATA = 0x0001
    private val MSG_LOAD_VIEW = 0x0002

    private var callBack: CallBack? = null

    private var isFinshLoaded = false

    private var isReloadView = false

    private var mHandler = object : Handler(Looper.getMainLooper()) {

        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MSG_LOAD_DATA -> if (!isFinshLoaded) {
                    Thread(Runnable { initJsonData() }).start()
                }
                MSG_LOAD_VIEW -> if (isFinshLoaded) {
                    callBack?.onOptionShow(option1Item, option2Item, option3Item)
                } else {
                    isReloadView = true
                    sendEmptyMessage(MSG_LOAD_DATA)
                }
            }
        }

    }

    init {
        mHandler.sendEmptyMessage( MSG_LOAD_DATA )
    }

    fun loadData() {
        mHandler.sendEmptyMessage(MSG_LOAD_DATA)
    }

    fun showView() {
        mHandler.sendEmptyMessage(MSG_LOAD_VIEW)
    }

    fun setCallBack(callBack: CallBack?) {
        this.callBack = callBack
    }

    fun showPickerView(
        context: Context?,
        listener: OnOptionsSelectListener
    ): OptionsPickerView<AddressItem>? { // 弹出选择器
        return OptionsPickerBuilder(context, listener)
            .setTitleText("")
            .setCancelText("取消")
            .setSubmitText("完成")
            .setDividerColor(Color.BLACK)
            .setTextColorCenter(Color.BLACK)
            .setContentTextSize(20)
            .build<AddressItem>()
    }

    interface CallBack {
        fun onOptionShow(
            opt1: List<AddressItem>,
            opt2: List<List<AddressItem>>,
            opt3: List<List<List<AddressItem>>>
        )
    }

    fun initJsonData() {

        if (isFinshLoaded) {
            if (isReloadView) {
                isReloadView = false;
                mHandler.sendEmptyMessage( MSG_LOAD_VIEW );
            }
            return;
        }

        var jsonStr = getJson(CommenApp.getContext(), "address.json")
        jsonStr?.let {
            var jsonBean = parseData(jsonStr)

            //省数据
            option1Item = jsonBean

            //遍历身份
            for (i in 0..(jsonBean.size - 1)) {
                //该省下city列表
                var cityList = ArrayList<AddressItem>()
                //该省下city的区列表
                var areaList = ArrayList<ArrayList<AddressItem>>()

                var citys = jsonBean[i].children
                //当该省下无city时填写默认值bean类
                if (citys == null || citys.isEmpty()) {
                    option2Item.add(cityList)
                    option3Item.add(areaList)
                    continue
                }

                for (c in 0..(citys.size - 1)) {
                    var city = citys[c]
                    cityList.add(city)
                    var city_areas = ArrayList<AddressItem>()

                    var areas = city.children
                    if (areas == null || areas.isEmpty()) {
                        //加入空值
                        city_areas.add(AddressItem(null, "", "",""))
                    } else {
                        city_areas.addAll(areas)
                    }

                    areaList.add(city_areas)
                }

                //城市数据
                option2Item.add(cityList)
                //添加区县数据
                option3Item.add(areaList)
            }

        }

        isFinshLoaded = true
    }


    /**
     * 解析JsOn
     */
    fun parseData(json: String): ArrayList<AddressItem> {
        var detail = ArrayList<AddressItem>()
        try {
            var data = JSONArray(json)
            var length = data.length()
            var gson = Gson()
            for (i in 0..(length - 1)) {
                var provinceItem = gson.fromJson(
                    data.optJSONObject(i).toString(),
                    AddressItem::class.java
                )
                detail.add(provinceItem)
            }
        } catch (e: Exception) {
        }
        return detail
    }

    private fun getJson(context: Context, fileName: String): String? {
        val stringBuilder = StringBuilder()
        try {
            val assetManager: AssetManager = context.getAssets()
            val bf = BufferedReader(
                InputStreamReader(
                    assetManager.open(fileName)
                )
            )
            var line: String? = null

            do {
                line = bf.readLine()
                line?.let {
                    stringBuilder.append(it)
                }
            } while (line != null)

        } catch (e: IOException) {
            e.printStackTrace()
        }
        return stringBuilder.toString()
    }

}

//json解析
data class AddressItem(
    var children: List<AddressItem>?,
    val parentId: String?,
    val regionId: String,
    val regionName: String
) : IPickerViewData {
    override fun getPickerViewText(): String {
        return regionName
    }
}

//
//data class ProvinceItem(
//    var children: List<CityItem>?,
//    val parentId: String?,
//    val regionId: String,
//    val regionName: String
//) : IPickerViewData {
//    override fun getPickerViewText(): String {
//        return regionName
//    }
//}
//
//data class CityItem(
//    var children: List<AreaItem>?,
//    val parentId: String?,
//    val regionId: String,
//    val regionName: String
//) : IPickerViewData {
//    override fun getPickerViewText(): String {
//        return regionName
//    }
//}
//
//data class AreaItem(
//    val parentId: String?,
//    val regionId: String,
//    val regionName: String
//) : IPickerViewData {
//    override fun getPickerViewText(): String {
//        return regionName
//    }
//}