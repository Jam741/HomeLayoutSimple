package com.jamisonline.homelayout

import android.content.Context

/**
 * Created by hejiaming on 2018/3/18.
 */
object UIUtils {


    /**
     * dip-->px
     */
    fun dip2Px(context: Context, dip: Int?): Int {
        if (dip == null) return 0
        val density = context.resources.displayMetrics.density
        return (dip * density + 0.5f).toInt()
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    fun sp2Px(context: Context, spValue: Float?): Int {
        if (spValue == null) return 0
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }

}