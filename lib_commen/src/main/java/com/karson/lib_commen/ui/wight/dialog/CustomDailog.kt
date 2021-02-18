package com.karson.lib_commen.ui.wight.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.text.TextUtils
import android.view.*
import android.widget.*
import com.karson.lib_commen.R

/**
 * @Author karson
 * @Date 2020/11/4-15:02
 * @desc dialog 公用
 */
class CustomDailog : Dialog {

    constructor(
        context: Context,
        themRes: Int,
        position: Int?,
        animator: Int?
    ) : super(context, themRes) {
        val window: Window? = this.window
        window?.let {
            //设置显示位置
            if (position == null)
                it.setGravity(Gravity.BOTTOM)
            else
                it.setGravity(position)
            //设置弹出收入动画
            if (animator == null)
                it.setWindowAnimations(R.style.popup_bottom_slideanimation)
            else
                it.setWindowAnimations(animator)
            //设置window属性
            it.decorView.setPadding(0, 0, 0, 0)
            val lp: WindowManager.LayoutParams = it.attributes
            lp.width = WindowManager.LayoutParams.WRAP_CONTENT
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT
            it.attributes = lp
        }
    }

    companion object {
        @JvmStatic
        fun getBuilder(mContext: Context): Builder {
            return Builder(mContext)
        }

        //用Builder模式来构造Dialog
        class Builder(private val mContext: Context) {
            private var position: Int = Gravity.CENTER
            private var contentView: View? = null
            private var title: String? = null
            private var message: String? = null
            private var positiveText: String? = null
            private var positiveColor = 0
            private var negativeText: String? = null
            private var negativeColor = 0
            private var isBackGrund = false
            private var animationId: Int = R.style.popup_bottom_slideanimation //默认底部弹出动画   设置0不弹出动画
            private var positiviOnclickListener: DialogInterface.OnClickListener? = null
            private var negativeOnclickListener: DialogInterface.OnClickListener? = null
            private var backgres: Int = 0
            private var isCancleOutside = false
            private var themRes: Int = R.style.CustomDialog

            fun setThemRes(themRes: Int): Builder {
                this.themRes = themRes
                return this
            }

            fun setIsCancleOutside(isCancle: Boolean): Builder {
                isCancleOutside = isCancle
                return this
            }

            fun setBackGRes(res: Int): Builder {
                backgres = res
                return this
            }

            fun setShowPosition(position: Int): Builder {
                this.position = position
                return this
            }

            fun setContentView(contentView: View?): Builder { //设置dialog的主view
                this.contentView = contentView
                return this
            }

            fun setIsBackGrund(isBackGrund: Boolean): Builder {
                this.isBackGrund = isBackGrund
                return this
            }

            fun setPositiveColor(positiveColor: Int): Builder {
                this.positiveColor = positiveColor
                return this
            }

            fun setNegativeColor(negativeColor: Int): Builder {
                this.negativeColor = negativeColor
                return this
            }

            fun setTitle(title: String?): Builder { //设置dialog的标题
                this.title = title
                return this
            }

            fun setMessage(msg: String?): Builder { //设置dialig的内容
                message = msg
                return this
            }

            fun setPositiveButton(
                text: String?,
                positiviOnclickListener: DialogInterface.OnClickListener?
            ): Builder { //dialog的确认按钮
                positiveText = text
                this.positiviOnclickListener = positiviOnclickListener
                return this
            }

            fun setNegativeButton(
                text: String?,
                negativeOnclickListener: DialogInterface.OnClickListener?
            ): Builder { //dialog的取消按钮
                negativeText = text
                this.negativeOnclickListener = negativeOnclickListener
                return this
            }

            //自定义弹出效果
            fun setAnimationId(animationId: Int): Builder {
                this.animationId = animationId
                return this
            }

            /**
             * 1,加载要显示的布局
             * 2，通过dialog的addContentView将布局添加到window中
             * 3，基本逻辑处理
             * 4，显示dialog的布局
             */
            fun build(): CustomDailog {
                val mInflater =
                    mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val mCustomDialog =
                    CustomDailog(mContext, themRes, position, animationId) //默认调用带style的构造
                mCustomDialog.setCanceledOnTouchOutside(isCancleOutside) //默认点击布局外不能取消dialog
                val view: View = mInflater.inflate(R.layout.layout_revise_dialog, null)
                val bg: LinearLayout = view.findViewById(R.id.revisedialog_layout)
                if (isBackGrund) {
                    if (backgres != 0)
                        bg.setBackgroundColor(backgres)
                    else
                        bg.background =
                            mContext.resources.getDrawable(R.drawable.base_dialogwhiterectangle)
                }
                val titleView = view.findViewById(R.id.dialog_title) as TextView
                val mContentLayout =
                    view.findViewById(R.id.dialog_content) as LinearLayout
                val messageView = view.findViewById(R.id.dialog_msg) as TextView
                //title 显示隐藏控制
                if (!TextUtils.isEmpty(title)) {
                    titleView.text = title
                    titleView.visibility = View.VISIBLE
                } else titleView.visibility = View.GONE
                if (!TextUtils.isEmpty(positiveText)) { //这是确认按钮
                    val btn_cofirm: TextView = view.findViewById(R.id.dialogDetermine) as TextView
                    btn_cofirm.text = positiveText
                    if (positiveColor != 0) {
                        btn_cofirm.setTextColor(mContext.resources.getColor(positiveColor))
                    }
                    btn_cofirm.setOnClickListener { v ->
                        if (positiviOnclickListener != null) {
                            positiviOnclickListener!!.onClick(
                                mCustomDialog,
                                DialogInterface.BUTTON_POSITIVE
                            )
                        }

                    }
                } else {
                    view.findViewById<TextView>(R.id.dialogDetermine).setVisibility(View.GONE)
                }
                if (!TextUtils.isEmpty(negativeText)) { //这是取消按钮逻辑处理
                    val btn_cancle: TextView = view.findViewById(R.id.dialogCancle) as TextView
                    btn_cancle.text = negativeText
                    btn_cancle.visibility = View.VISIBLE
                    if (negativeColor != 0) {
                        btn_cancle.setTextColor(mContext.resources.getColor(negativeColor))
                    }
                    btn_cancle.setOnClickListener { v ->
                        if (negativeOnclickListener != null) {
                            negativeOnclickListener!!.onClick(
                                mCustomDialog,
                                DialogInterface.BUTTON_NEGATIVE
                            )
                        }
                    }

                } else {
                    view.findViewById<TextView>(R.id.dialogCancle)
                        .visibility = View.GONE
                }
                //底部布局显示隐藏控制
                if (!TextUtils.isEmpty(positiveText) && !TextUtils.isEmpty(negativeText)) {
                    view.findViewById<View>(R.id.dialogLine).visibility = View.VISIBLE
                    view.findViewById<View>(R.id.dialogVLine).visibility = View.VISIBLE
                    view.findViewById<View>(R.id.dialog_bottom)
                        .visibility = View.VISIBLE
                } else if (!TextUtils.isEmpty(positiveText) || !TextUtils.isEmpty(negativeText)) {
                    view.findViewById<View>(R.id.dialogLine).visibility = View.VISIBLE
                    view.findViewById<View>(R.id.dialogVLine).visibility = View.GONE
                    view.findViewById<View>(R.id.dialog_bottom)
                        .visibility = View.VISIBLE
                    if (TextUtils.isEmpty(positiveText))
                        view.findViewById<View>(R.id.dialogDetermine).visibility = View.VISIBLE
                    if (TextUtils.isEmpty(negativeText))
                        view.findViewById<View>(R.id.dialogCancle).visibility = View.VISIBLE
                } else {
                    view.findViewById<View>(R.id.dialogLine).visibility = View.GONE
                    view.findViewById<View>(R.id.dialogVLine).visibility = View.GONE
                    view.findViewById<View>(R.id.dialog_bottom).visibility = View.GONE
                }
                //显示内容控制
                if (!TextUtils.isEmpty(message)) {
                    mContentLayout.visibility = View.GONE
                    messageView.text = message //显示的内容
                    messageView.visibility = View.VISIBLE
//                    bg.background = mContext.resources.getDrawable(R.drawable.dialog_iosbg)
//                    view.findViewById<LinearLayout>(R.id.revisedialog_layout)
//                        .setBackground(mContext.resources.getDrawable(R.drawable.dialog_iosbg))
                } else if (contentView != null) { //如果内容区域要显示其他的View的话
                    mContentLayout.removeAllViews()
                    messageView.visibility = View.GONE
                    mContentLayout.addView(contentView)
                    mContentLayout.visibility = View.VISIBLE
                } else {
                    mContentLayout.visibility = View.GONE
                    messageView.visibility = View.GONE
//                    bg.background = mContext.resources.getDrawable(R.drawable.dialog_iosbg)
                }
                mCustomDialog.setContentView(view)
                return mCustomDialog
            }

        }


    }
}