package com.karson.lib_commen.util

import android.app.Activity
import android.app.Application
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.karson.lib_commen.app.BaseApp
import com.karson.lib_commen.app.CommenApp
import com.karson.lib_commen.app.CommenApp.app

/**
 *
 * 扩展方法
 * @author chentong
 * @date 2020-03-30
 *
 */
fun String?.trimAll(): String {
    if (this == null) return ""
    return this.trim()
}

fun String?.isNotEmptyExt(): Boolean {
    if (this == null) return false
    if (this.trim().isEmpty()) return false
    return true
}

fun String?.isEmptyExt(): Boolean {
    if (this == null) return true
    if (this.trim().isEmpty()) return true
    return false
}

fun String?.equalsExt(str: String?): Boolean {
    if (this == null || str==null) return false
    return this.trim().equals(str.trim())
}

fun String?.startsWithExt(str: String?): Boolean {
    if (this == null || str==null) return false
    return this.trim().startsWith(str.trim())
}

fun String?.append(str: String?): String {
    return this?.trimAll() + str?.trimAll()
}

fun <T> List<T>?.isEmptyExt():Boolean{
    if (this == null) return true
    return  this.isEmpty()
}

fun <T> List<T>?.isNotEmptyExt():Boolean{
    if (this == null) return false
    return  this.isNotEmpty()
}

/**
 * 打卡软键盘
 */
fun Activity?.openKeyBord(mEditText: EditText, mContext: Context) {
    val imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN)
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
}

/**
 * 关闭软键盘
 */
fun Activity?.closeKeyBord(mEditText: EditText, mContext: Context) {
    val imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(mEditText.windowToken, 0)
}

/**
 * 弹出toast
 */
fun Fragment?.showToast(content: String): Toast {
    val toast = android.widget.Toast.makeText(
        this?.activity?.applicationContext,
        content,
        android.widget.Toast.LENGTH_SHORT
    )
    toast.show()
    return toast
}

/**
 * 弹出toast
 */
fun Context?.showToast(content: String): Toast {
    val toast = Toast.makeText(this, content, Toast.LENGTH_SHORT)
    toast.show()
    return toast
}

fun String?.toast() {
    if (this == null) return
    val toast = Toast.makeText(CommenApp.getAppContext(), this, Toast.LENGTH_SHORT)
    toast.show()
}


inline fun <reified T> Context?.startActivity() {
    this?.startActivity<T>(Intent())
}

inline fun <reified T> Context?.startActivity(intent: Intent) {
    if (this == null) return
    when (this) {
        is Activity -> this
        is Fragment -> activity
        else -> (this as Activity)
    }?.let { activity ->
        intent.setClass(activity, T::class.java)
        activity.startActivity(intent)
    }
}

inline fun <reified T> Application?.startActivity(intent: Intent) {
    if (this == null) return
    var intent = Intent(this,T::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    this.startActivity(intent)
}


/**
 * 获得viewmodel
 */
fun <VM : ViewModel> getViewModel(context: Context, clazz: Class<out ViewModel>): VM {
    return when (context) {
        is FragmentActivity -> ViewModelProviders.of(context).get(clazz) as VM
        is Fragment -> ViewModelProviders.of(context).get(clazz) as VM
        else -> ViewModelProviders.of(context as FragmentActivity).get(clazz) as VM
    }
}

//fun <BINDING : ViewDataBinding> dataBind(context: Context, id: Int): BINDING {
////    return when (context) {
////        is Activity -> DataBindingUtil.inflate(context.layoutInflater, id, null, false) as BINDING
////        is Fragment -> DataBindingUtil.inflate(context.layoutInflater, id, null, false) as BINDING
////        is FragmentActivity -> DataBindingUtil.inflate(
////            context.layoutInflater,
////            id,
////            null,
////            false
////        ) as BINDING
////        is AppCompatActivity -> DataBindingUtil.inflate(
////            context.layoutInflater,
////            id,
////            null,
////            false
////        ) as BINDING
////        else -> DataBindingUtil.inflate(
////            (context as Activity).layoutInflater,
////            id,
////            null,
////            false
////        ) as BINDING
////    }
////}

// 获得T.class
inline fun <reified T> classOf() = T::class.java

//获得 T object
inline fun <reified T> instanceOf() = T::class.java.newInstance()

/**
 * 添加到剪贴板
 */
fun copyText(context: Context,str: String){
    var copy : ClipboardManager = context.applicationContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    var data : ClipData = ClipData.newPlainText("Label",str)
    copy.setPrimaryClip(data)

}
