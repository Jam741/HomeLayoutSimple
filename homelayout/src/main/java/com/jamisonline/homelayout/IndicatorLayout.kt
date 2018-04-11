package com.jamisonline.homelayout

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.util.Log
import android.view.View.OnClickListener
import android.widget.LinearLayout

/**
 * Created by hejiaming on 2018/3/22.
 *
 * @desciption:指示器容器
 */

class IndicatorLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), ViewPager.OnPageChangeListener {

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        if (onItemSelectedListener != null) onItemSelectedListener!!.onItemSelected(getChildAt(position) as IndicatorButton, currentItem, position)
        updateTabState(position)
    }

    val STATE_INSTANCE = "instance_state"

    val STATE_IIEM = "state_item"

    var smoothScroll = false

    var viewPager: ViewPager? = null


    fun setMPager(viewPager: ViewPager) {
        this@IndicatorLayout.viewPager = viewPager
        init()

    }

    var currentItem = 0

    private val mChildViews by lazy { ArrayList<IndicatorButton>() }

    var onItemSelectedListener: OnItemSelectedListener? = null

//    var selectedIcons: Array<Int>? = null
//
//    var unselectedIcons: Array<Int>? = null
//
//    var selectedTexts: Array<String>? = null
//
//    var unselectedTexts: Array<String>? = null
//
//    var config: Config? = null
//
//
//    constructor(context: Context, selectedIcons: Array<Int>, unselectedIcons: Array<Int>, selectedTexts: Array<String>, unselectedTexts: Array<String>, config: Config) : super(context) {
//        this@IndicatorLayout.selectedIcons = selectedIcons
//        this@IndicatorLayout.unselectedIcons = unselectedIcons
//        this@IndicatorLayout.selectedTexts = selectedTexts
//        this@IndicatorLayout.unselectedTexts = unselectedTexts
//        this@IndicatorLayout.config = config
//    }

//    constructor(context: Context) : super(context) {}
//
//    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}
//
//
//    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
//
//
////        initChildView()
//    }

//    fun initChildView() {
//        for (icon in selectedIcons!!){
//            val indicatorButtom = IndicatorButton(context)
//        }
//    }

    /**
     * 当1级的子view全部加载完调用，可以用初始化子view的引用
     * 注意，这里无法获取子view的宽高
     */
    override fun onFinishInflate() {
        super.onFinishInflate()
        init()
    }


    private fun init() {
        if (viewPager != null) {
            if (viewPager!!.adapter == null) throw IllegalArgumentException("ViewPager must set adapter")
            if (viewPager!!.adapter.count != childCount) throw IllegalArgumentException("IndicatorButton num must == childCount")
            viewPager!!.addOnPageChangeListener(this)
        }

        Log.d("JAM", "childCount:" + childCount)

        for (i in 0 until childCount) {
            Log.d("JAM", "indicatorButton:" + i)
//            Log.d("JAM", "indicatorButton:" + (getChildAt(i) is IndicatorButton))

            if (getChildAt(i) !is IndicatorButton) throw IllegalArgumentException("IndicatorLayout 的 childView must be IndicatorButton" + i)
            val indicatorButton = getChildAt(i) as IndicatorButton

            indicatorButton.setOnClickListener(indicatorOnClickListener)
        }

        (getChildAt(currentItem) as IndicatorButton).setStatus(true)
    }


    /**
     * 指示器被点击
     */
    private val indicatorOnClickListener: OnClickListener by lazy {
        OnClickListener { v ->
            val clickPosition = indexOfChild(v)
            if (viewPager != null) didHasViewPagerClick(clickPosition, v as IndicatorButton) else didNoViewPagerClick(clickPosition, v as IndicatorButton)
        }
    }

    /**
     * 指示器被点击没有ViewPager的操作
     */
    private fun didNoViewPagerClick(clickPosition: Int, v: IndicatorButton) {
        if (onItemSelectedListener != null) onItemSelectedListener!!.onItemSelected(v, currentItem, clickPosition)
        updateTabState(clickPosition)
    }


    /**
     * 重置指示器状态
     */
    private fun updateTabState(selectedPosition: Int) {
        (getChildAt(currentItem) as IndicatorButton).setStatus(false)
        (getChildAt(selectedPosition) as IndicatorButton).setStatus(true)
        currentItem = selectedPosition
    }

    /**
     * 指示器被点击 设置了ViewPager的操作
     */
    private fun didHasViewPagerClick(clickPosition: Int, v: IndicatorButton) {
        if (clickPosition == currentItem) {//点击已选中的指示器
            if (onItemSelectedListener != null) onItemSelectedListener!!.onItemSelected(v, currentItem, clickPosition)
        } else {
            viewPager!!.setCurrentItem(clickPosition,smoothScroll)
        }

    }

    interface OnItemSelectedListener {
        fun onItemSelected(indicatorButton: IndicatorButton, previousPosition: Int, currentPosition: Int)
    }

    fun setUnRead(index: Int, unreadUnm: Int) {
        getIndicatorButtonChilcAt(index).setUnreadUnm(unreadUnm)
    }

    fun setMsg(index: Int, msg: String) {
        getIndicatorButtonChilcAt(index).setMsg(msg)
    }

    fun hideMsg(index: Int) {
        getIndicatorButtonChilcAt(index).hideMsg()
    }

    fun showNotifyPoint(index: Int) {
        getIndicatorButtonChilcAt(index).showNotifyPoint()
    }

    fun hideNotifyPoint(index: Int) {
        getIndicatorButtonChilcAt(index).hideNotifyPoint()
    }

    fun setUnreadNumThreshold(index: Int, unreadThreshold: Int) {
        getIndicatorButtonChilcAt(index).setUnreadNumThreshold(unreadThreshold)
    }

    fun getIndicatorButtonChilcAt(index: Int): IndicatorButton {
        return getChildAt(index) as IndicatorButton
    }


    /**
     * @return 当View被销毁的时候，保存数据
     */
    override fun onSaveInstanceState(): Parcelable {
        val bundle = Bundle()
        bundle.putParcelable(STATE_INSTANCE, super.onSaveInstanceState())
        bundle.putInt(STATE_IIEM, currentItem)
        return bundle
    }


    /**
     * @param state 用于恢复数据使用
     */
    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state != null && state is Bundle) {
            currentItem = state.getInt(STATE_IIEM)
            updateTabState(currentItem)
            super.onRestoreInstanceState(state.getParcelable(STATE_INSTANCE))
        } else {
            super.onRestoreInstanceState(state)
        }
    }
}
