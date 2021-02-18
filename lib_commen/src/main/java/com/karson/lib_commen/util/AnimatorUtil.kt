package com.karson.lib_commen.util

import android.animation.Keyframe
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.view.View
import android.view.animation.RotateAnimation
import android.widget.ImageView
import com.karson.lib_commen.R

/**
 * 简单动画工具类
 */
object AnimatorUtil {

    /**
     * 抖动动画
     */
    fun shakeAnimator(view: View): ObjectAnimator? {
        val delta = view.resources.getDimensionPixelOffset(R.dimen.dp_10).toFloat()
        val pvhTranslateX: PropertyValuesHolder = PropertyValuesHolder.ofKeyframe(
            View.TRANSLATION_X,
            Keyframe.ofFloat(0f, 0f),
            Keyframe.ofFloat(.10f, -delta),
            Keyframe.ofFloat(.26f, delta),
            Keyframe.ofFloat(.42f, -delta),
            Keyframe.ofFloat(.58f, delta),
            Keyframe.ofFloat(.74f, -delta),
            Keyframe.ofFloat(.90f, delta),
            Keyframe.ofFloat(1f, 0f)
        )
        return ObjectAnimator.ofPropertyValuesHolder(view, pvhTranslateX)
            .setDuration(300)
    }


    /**
     * 根据当前的状态来旋转箭头。
     */
    fun rotateArrow(arrow: ImageView, flag: Boolean) {
        val pivotX: Float = arrow.width / 2f
        val pivotY: Float = arrow.height / 2f
        val fromDegrees: Float
        val toDegrees: Float
        // flag为true则向上
        if (flag) {
            fromDegrees = 180f
            toDegrees = 360f
        } else {
            fromDegrees = 0f
            toDegrees = 180f
        }
        //旋转动画效果   参数值 旋转的开始角度  旋转的结束角度  pivotX x轴伸缩值
        val animation = RotateAnimation(
            fromDegrees, toDegrees,
            pivotX, pivotY
        )
        //该方法用于设置动画的持续时间，以毫秒为单位
        animation.duration = 260
        //动画终止时停留在最后一帧
        animation.fillAfter = true
        //启动动画
        arrow.startAnimation(animation)
    }
}