package com.jamisonline.homelayout

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import java.lang.IllegalStateException

/**
 * Created by hejiaming on 2018/3/18.
 */

class IndicatorButton @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, 0) {


    private var iconNormal: Int = 0

    private var iconSelected: Int = 0

    private var text: String = ""

    private var textSize = 12 //文字大小，默认12sp

    private val textColorNormalDefault = 0xFF999999 //文本默认颜色
    private var textColorNormal = 0 //文本默认颜色

    private val textColorSelectedDefault = 0xFF46C01B //文本选中颜色
    private var textColorSelected = 0 //文本选中颜色

    private var drawablePadding = 5 //图文间距，默认5dp

    private var openTouchBg = false //是否开启触摸背景，默认关闭

    private var touchDrawable: Drawable? = null

    private var iconWidth = 20

    private var iconHeight = 20

    private var itemPadding: Int = 0

    private val containerView by lazy { LayoutInflater.from(context).inflate(R.layout.indicator_button_layout, null) }

    private val ivIcon by lazy { containerView.findViewById<ImageView>(R.id.iv_icon) }

    private val tvContext by lazy { containerView.findViewById<TextView>(R.id.tv_text) as TextView }

    private val tvUnreadNum by lazy { containerView.findViewById<TextView>(R.id.tv_unread_num) as TextView }

    private val tvMsg by lazy { containerView.findViewById<TextView>(R.id.tv_msg) as TextView }

    private val tvNotifyPoint by lazy { containerView.findViewById<TextView>(R.id.tv_point) as TextView }

    private var unreadTextSize = 10 //未读数字体大小默认为10sp

    private var msgTextSize = 6 //消息字体大小默认为 6 sp

    private var unreadNumThreshold = 99 //未读消息数默认阈值


    init {

        val ta = context.obtainStyledAttributes(attrs, R.styleable.IndicatorButton)

        iconNormal = ta.getResourceId(R.styleable.IndicatorButton_iconNormal, -1)
        iconSelected = ta.getResourceId(R.styleable.IndicatorButton_iconSelected, -1)

        text = ta.getString(R.styleable.IndicatorButton_itemText)
        textSize = ta.getDimensionPixelSize(R.styleable.IndicatorButton_itemTextSize, UIUtils.sp2Px(context, textSize.toFloat()))

        textColorNormal = ta.getColor(R.styleable.IndicatorButton_textColorNormal, textColorNormalDefault.toInt())
        textColorSelected = ta.getColor(R.styleable.IndicatorButton_textColorSelected, textColorSelectedDefault.toInt())

        drawablePadding = ta.getDimensionPixelSize(R.styleable.IndicatorButton_paddingToDrawable, drawablePadding)

        openTouchBg = ta.getBoolean(R.styleable.IndicatorButton_openTouchBg, openTouchBg)
        touchDrawable = ta.getDrawable(R.styleable.IndicatorButton_touchDrawable)

        iconWidth = ta.getDimensionPixelSize(R.styleable.IndicatorButton_iconWidth, UIUtils.sp2Px(context, iconWidth.toFloat()))
        iconHeight = ta.getDimensionPixelSize(R.styleable.IndicatorButton_iconHight, UIUtils.sp2Px(context, iconHeight.toFloat()))
        itemPadding = ta.getDimensionPixelSize(R.styleable.IndicatorButton_itemPadding, 0)

        unreadTextSize = ta.getDimensionPixelSize(R.styleable.IndicatorButton_unreadTextSize, UIUtils.sp2Px(context, unreadTextSize.toFloat()))
        msgTextSize = ta.getDimensionPixelSize(R.styleable.IndicatorButton_msgTextSize, UIUtils.sp2Px(context, msgTextSize.toFloat()))
        unreadNumThreshold = ta.getInteger(R.styleable.IndicatorButton_unreadThreshold, unreadNumThreshold)

        ta.recycle()

        checkValues()

        init()
    }

    @SuppressLint("NewApi")
    private fun init() {
        orientation = VERTICAL
        gravity = Gravity.CENTER
        ivIcon.setImageResource(iconNormal)
        if (iconWidth != 0 && iconHeight != 0) {
            ivIcon.layoutParams = ivIcon.layoutParams.apply {
                width = iconWidth
                height = iconHeight
            }
        } else {
            ivIcon.layoutParams = ivIcon.layoutParams.apply {
                width = ViewGroup.LayoutParams.WRAP_CONTENT
                height = ViewGroup.LayoutParams.WRAP_CONTENT
            }
        }

        tvContext.run {
            setTextSize(TypedValue.COMPLEX_UNIT_PX, this@IndicatorButton.textSize.toFloat())
            text = this@IndicatorButton.text
            setTextColor(this@IndicatorButton.textColorNormal)
            setPadding(0,itemPadding,0,0)
        }
        tvUnreadNum.run { setTextSize(TypedValue.COMPLEX_UNIT_PX, unreadTextSize.toFloat()) }
        tvMsg.run { setTextSize(TypedValue.COMPLEX_UNIT_PX, msgTextSize.toFloat()) }
        if (openTouchBg) background = touchDrawable
        addView(containerView)
    }

    /**
     * 检查输入的值是否完善
     */
    private fun checkValues() {
        if (iconNormal == -1) throw IllegalStateException("must set attr iconNormal")
        if (iconSelected == -1) throw IllegalStateException("must set attr iconSelected")
        if (openTouchBg && touchDrawable == null) throw IllegalStateException("开启触摸效果，必须指定touchDrawable")
    }

    fun setStatus(isSelected: Boolean) {
        ivIcon.setImageResource(if (isSelected) iconSelected else iconNormal)
        tvContext.setTextColor(if (isSelected) textColorSelected else textColorNormal)
    }

    fun setUnreadNumThreshold(unreadNumThreshold: Int) {
        this@IndicatorButton.unreadNumThreshold = unreadNumThreshold
    }

    /**
     * 设置未读数
     *
     * @param unreadUnm
     */
    fun setUnreadUnm(unreadUnm: Int) {
        setVisibleWith(tvUnreadNum)
        when {
            unreadUnm <= 0 -> tvUnreadNum.visibility = View.GONE
            unreadUnm in 1..unreadNumThreshold -> tvUnreadNum.text = unreadUnm.toString()
            else -> tvUnreadNum.text = unreadNumThreshold.toString() + "+"
        }
    }


    fun setMsg(msg: String) {
        setVisibleWith(tvMsg)
        tvMsg.text = msg
    }

    fun hideMsg() {
        tvMsg.visibility = View.GONE
    }

    fun hideNotifyPoint() {
        tvNotifyPoint.visibility = View.GONE
    }

    fun showNotifyPoint() {
        setVisibleWith(tvNotifyPoint)
    }

    fun setVisibleWith(tv: TextView) {
        tvUnreadNum.visibility = View.GONE
        tvMsg.visibility = View.GONE
        tvNotifyPoint.visibility = View.GONE
        tv.visibility = View.VISIBLE
    }


}
