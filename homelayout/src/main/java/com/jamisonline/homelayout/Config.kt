package com.jamisonline.homelayout

import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt

/**
 * Created by hejiaming on 2018/3/23.
 * @desciption:
 */
class Config(@ColorInt val textColorNormal: Int = 0x7f060028, @ColorInt val textColorSelected: Int = 0x7f060029) {

    var textSize: Int? = null

    var drawablePadding: Int? = null

    var openTouchBackground: Boolean? = null

    var touchDrawable: Drawable? = null

    var iconWidth: Int? = null

    var iconHeihgt: Int? = null

    var itemPadding: Int? = null

    var unreadTextSize: Int? = null

    @ColorInt
    var unreadTextColor: Int? = null

    var msgTextSize: Int? = null

    @ColorInt
    var msgTextColor: Int? = null

    var unreadThreshold: Int? = null

}