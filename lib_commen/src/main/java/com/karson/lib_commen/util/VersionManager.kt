package com.karson.lib_commen.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.text.TextUtils
import androidx.core.content.FileProvider
import com.karson.lib_commen.app.BuildConfig
import com.karson.lib_commen.app.CommenApp
import com.karson.lib_commen.net.bean.VersionBean
import com.karson.lib_commen.ui.onclick.UpdataInterface
import com.karson.lib_commen.ui.wight.dialog.CustomDailog
import com.liulishuo.filedownloader.BaseDownloadTask
import com.liulishuo.filedownloader.FileDownloadListener
import com.liulishuo.filedownloader.FileDownloader
import java.io.File


object VersionManager  {

    private var updateDialog: CustomDailog? = null
    private var upData: UpdataInterface?=null

    @Synchronized
    fun updateVersionInfo(context: Context, isTips: Boolean = false) {
        upData?.let { it.queryVersionInfoComplete() }
    }
    /**
     *
     * @param context
     * @param versionInfoBean
     * @param dialogStatus
     * 四种状态
     * 1、不提示
     * 2、提示框 未发现新版本
     * 3、非强制更新
     * 4、强制更新
     */
    private fun showDialog(
        context: Context,
        dialogStatus: Int,
        versionBean: VersionBean
    ) {
        //下载路径
        val downLoadUrl: String = versionBean.downLoadUrl.trimAll()
        createUpdateDialog(context, dialogStatus, versionBean, object : CallBack {
            override fun update(dialogCallBack: DialogCallBack) {
                if (dialogStatus == 4) { //如果是强制更新 退出登录状态
                    UserManager.logout()
                }
                //下载并安装 增加apk缓存删除
                deleteTempApk();

                dowload(
                    context,
                    downLoadUrl,
                    getApkPath(),
                    object : FileDownloadListener() {
                        override fun pending(
                            task: BaseDownloadTask,
                            soFarBytes: Int,
                            totalBytes: Int
                        ) {
                        }

                        override fun progress(
                            task: BaseDownloadTask,
                            soFarBytes: Int,
                            totalBytes: Int
                        ) {
                            dialogCallBack.progress(soFarBytes, totalBytes)
                        }

                        override fun completed(task: BaseDownloadTask) {
                            dialogCallBack.setProgress(task.smallFileSoFarBytes,task.smallFileTotalBytes)
                            dialogCallBack.completed()
                            install(context, getApkPath())
//                            dialogCallBack.dismiss()
                        }

                        override fun paused(
                            task: BaseDownloadTask,
                            soFarBytes: Int,
                            totalBytes: Int
                        ) {
                        }

                        override fun error(
                            task: BaseDownloadTask,
                            e: Throwable
                        ) {
                        }

                        override fun warn(task: BaseDownloadTask) {}
                    })
                //

            }
        })
    }

    private fun createUpdateDialog(
        context: Context,
        dialogStatus: Int,
        versionInfoBean: VersionBean,
        callBack: CallBack
    ) {


        if (dialogStatus == 1) { //不提示更新

            updateDialog?.let {
                if (it.isShowing) {
                    it.dismiss()
                }
            }

            updateDialog = null;
            return;
        }

        // 当前弹框正在显示 返回
        updateDialog?.let {
            if (it.isShowing) return
        }

        updateDialog = null;

        //逻辑开始
//        val rootView: View =
//            LayoutInflater.from(context).inflate(R.layout.layout_updataversion, null, false)
//        val progressBar: NumberProgressBar = rootView.findViewById(R.id.versionupdata_progressbar)
//        progressBar.progress = 0
//        val updataBack: ImageView = rootView.findViewById<ImageView>(R.id.versionupdata_close)
//        val versionUpdata: TextView = rootView.findViewById(R.id.versionupdata_updata)
//        val titleView: TextView = rootView.findViewById(R.id.versionupdata_title)
//        val contentView: TextView = rootView.findViewById(R.id.versionupdata_contents)

        //文案
//        titleView.text = "发现新版本" + versionInfoBean.version
//        contentView.text = versionInfoBean.description
//
//        updataBack.setOnClickListener { view ->
//            updateDialog?.let {
//                if (it.isShowing) {
//                    it.dismiss()
//                }
//            }
//            updateDialog = null
//        }

        /**ersionUpdata.setOnClickListener { view: View? ->
            progressBar.visibility = View.VISIBLE
            versionUpdata.visibility = View.GONE
            //更新下载
            callBack.update(object : DialogCallBack {
                override fun progress(soFarBytes: Int, totalBytes: Int) {
                    updateDialog?.let {
                        if (it.isShowing) {
                            if (totalBytes == -1) progressBar.incrementProgressBy(0)
                            else {
                                progressBar.max = 100
                                progressBar.progress = ((soFarBytes.toDouble()/(totalBytes.toDouble()))*100).toInt()
                            }
                        }
                    }
                }

                override fun setProgress(progress: Int, totalBytes: Int) {

                    updateDialog?.let {
                        if (it.isShowing) {
                            progressBar.progress = (((progress.toDouble())/(totalBytes.toDouble()))*100).toInt()
                        }
                    }
                }

                override fun completed() { //下载完成后 UI变化
                }

                override fun dismiss() {
                    updateDialog?.let {
                        if (it.isShowing) {
                            it.dismiss()
                        }
                    }
                    updateDialog = null;
                }
            })

        }

        //布局切换

        //布局切换
        if (dialogStatus == 2) { //提示框
            titleView.text = "未发现新版本"
            contentView.text = "现在是最新版本"
            versionUpdata.visibility = View.GONE
            progressBar.visibility = View.GONE
            updataBack.setVisibility(View.VISIBLE)
        } else if (dialogStatus == 3) { //非强制更新
            updataBack.setVisibility(View.VISIBLE)
            versionUpdata.visibility = View.VISIBLE
        } else if (dialogStatus == 4) { //强制更新
            updataBack.setVisibility(View.GONE)
            versionUpdata.visibility = View.VISIBLE
        }
*/
//        updateDialog =
//            CustomDailog.Companion.Builder(context).setContentView(rootView).setAnimationId(0)
//                .build()
        updateDialog?.setCanceledOnTouchOutside(false)
        updateDialog?.setCancelable(false)
        updateDialog?.show()
    }

    //下载逻辑回调
    private interface CallBack {
        fun update(dialogCallBack: DialogCallBack)
    }

    /**
     * dialog状态回调
     */
    private interface DialogCallBack {
        fun progress(soFarBytes: Int, totalBytes: Int) //当前进度
        fun setProgress(progress: Int, totalBytes: Int) //设置进度
        fun completed() //下载完成 UI变化
        fun dismiss() //关闭弹框
    }

    //apk下载
    private fun dowload(
        mContext: Context,
        url: String,
        path: String,
        downloadListener: FileDownloadListener
    ) {
        FileDownloader.setup(mContext)
        FileDownloader.getImpl().create(url)
            .setPath(path, false)
            .setCallbackProgressTimes(300) //设置下载最大回调progress次数
            .setCallbackProgressMinInterval(1000) //设置每次请求progress时间间隔
            .setAutoRetryTimes(3) //下载失败后重试3次
            .setMinIntervalUpdateSpeed(1000)
            .setListener(downloadListener)
            .start()
    }

    private fun getApkPath(): String {
        return CommenApp.getContext().filesDir
            .toString() + "/download" + "/配电.apk"
    }

    //删除apk缓存
    private fun deleteTempApk() {
        val apkPath = getApkPath()
        val apkFile = File(apkPath)
        if (apkFile.exists()) {
            apkFile.delete()
        }
    }

    private fun install(context: Context, apkPath: String) {
        if (TextUtils.isEmpty(apkPath)) {
            return
        }
        val intent = Intent(Intent.ACTION_VIEW)
        val file = File(apkPath)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        if (Build.VERSION.SDK_INT >= 24) { //大于7.0使用此方法
            val apkUri: Uri = FileProvider.getUriForFile(
                context,
                BuildConfig.APPLICATION_ID + ".fileprovider",
                file
            ) ///-----ide文件提供者名
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive")
        } else { //小于7.0就简单了
            // 由于没有在Activity环境下启动Activity,设置下面的标签
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive")
        }
        context.startActivity(intent)
    }

    fun queryVersionInfoComplete(vararg params: String) {
        var downloadUrl = params[0]
        var upgradeStatus = params[1]
        var forceUpdate = params[2]
        var dialogStatus = 1

        //提示更新状态
        if (upgradeStatus.equalsExt("1")) {
            //url有效
            if (downloadUrl.startsWithExt("http")) {
                if (forceUpdate.equalsExt("1")) {
                    dialogStatus = 4; //强制更新
                } else {
                    dialogStatus = 3; //非强制更新
                }
            } else { //no 不提示
                dialogStatus = 1;
            }

        } /*else {
//            if (Constant.) { //提示框
//                dialogStatus = 2;
//            }
        }*/

//        version.downloadURL = "https://www.hexincaifu.com/download/android_hexinjinfu.apk"
//        dialogStatus = 3
//        mPresent.mContext?.let { it ->
//            showDialog(it, dialogStatus, downloadUrl);
//        }
    }

}