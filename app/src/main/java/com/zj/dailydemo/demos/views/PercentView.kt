package com.zj.dailydemo.demos.views

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateInterpolator
import androidx.annotation.ColorInt
import com.zj.dailydemo.R

/**
 */
class PercentView : View {
    @ColorInt
    private var moneyColor: Int = Color.RED
    private lateinit var moneyPrefix: String
    private var moneyTextSize: Int = 0
    private var moneyPrefixTextSize = 0
    private lateinit var mTitleString: String
    private var outCircleWidth = 30

    @ColorInt
    private var outCircleColor: Int = Color.BLACK

    @ColorInt
    private var innerCircleColor: Int = Color.YELLOW

    @ColorInt
    private var innerShadeColor: Int = Color.GRAY
    private var innerShadeWidth: Int = 0
    private var innerMarginOuter: Int = 0
    private var titleTextHeight: Int = 0
    private var moneyTextHeight: Int = 0
    private var money: Float = 0f

    @ColorInt
    private var mTipsColor: Int = Color.BLACK
    private var mTipsTextSize: Int = 0

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.PercentView, defStyle, 0
        )
        moneyColor = a.getColor(R.styleable.PercentView_moneyColor, Color.BLACK)
        mTipsColor = a.getColor(R.styleable.PercentView_tipsColor, Color.BLACK)
        moneyTextSize = a.getDimensionPixelSize(R.styleable.PercentView_moneyTextSize, 32)
        mTipsTextSize = a.getDimensionPixelSize(R.styleable.PercentView_tipsTextSize, 32)
        moneyPrefix = a.getString(R.styleable.PercentView_moneyPrefix)?.let {
            it
        } ?: kotlin.run {
            "¥"
        }
        moneyPrefixTextSize =
            a.getDimensionPixelSize(R.styleable.PercentView_moneyPrefixTextSize, 16)
        mTitleString = a.getString(R.styleable.PercentView_titleString)?.let {
            it
        } ?: kotlin.run {
            "授信余额"
        }
        outCircleWidth = a.getDimensionPixelOffset(R.styleable.PercentView_outCircleWidth, 30)
        innerCircleColor = a.getColor(R.styleable.PercentView_innerCircleColor, Color.YELLOW)
        innerShadeColor = a.getColor(R.styleable.PercentView_innerShadeColor, Color.GRAY)
        innerShadeWidth = a.getDimensionPixelOffset(R.styleable.PercentView_innerShadeWidth, 30)
        innerMarginOuter = a.getDimensionPixelOffset(R.styleable.PercentView_innerMarginOuter, 30)
        a.recycle()
        val rect = Rect()
        mMoneyPaint.getTextBounds("300", 0, 2, rect)
        moneyTextHeight = rect.bottom - rect.top
        mTitlePaint.getTextBounds("授信余额", 0, 2, rect)
        titleTextHeight = rect.bottom - rect.top
    }

    private val mMoneyPaint: TextPaint by lazy {
        TextPaint().apply {
            isAntiAlias = true
            color = moneyColor
            textAlign = Paint.Align.CENTER
            textSize = moneyTextSize.toFloat()
        }
    }
    private val mMoneyPrefixPaint: TextPaint by lazy {
        TextPaint().apply {
            isAntiAlias = true
            color = moneyColor
            textAlign = Paint.Align.RIGHT
            textSize = moneyPrefixTextSize.toFloat()
        }
    }
    private val mTitlePaint: Paint by lazy {
        Paint().apply {
            isAntiAlias = true
            textAlign = Paint.Align.CENTER
            textSize = mTipsTextSize.toFloat()
            color = mTipsColor
        }
    }
    private val mOutCirclePaint by lazy {
        Paint().apply {
            color = outCircleColor
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
            strokeWidth = outCircleWidth.toFloat()
        }
    }
    private val mInnerCirclePaint by lazy {
        Paint().apply {
            color = innerCircleColor
            setShadowLayer(innerShadeWidth.toFloat(), 0f, 0f, innerShadeColor)
        }
    }

    /**
     * 以空间的宽度为基准 使控件的高度=宽度
     * @param widthMeasureSpec Int
     * @param heightMeasureSpec Int
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }

    /**
     * tips：该控件没有处理View的padding属性，所以设置不生效
     * @param canvas Canvas
     */
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //1、绘制外包的圆环
        drawOutCircle(canvas)
        //2、绘制内部的小圆
        drawInnerCircle(canvas)
        //3、绘制：授信余额
        drawMoney(canvas)
        //4、绘制金额
        drawTitle(canvas)
    }

    private val innerCircleRadius by lazy {
        centerX - innerMarginOuter - innerShadeWidth
    }

    private fun drawInnerCircle(canvas: Canvas) {
        canvas.drawCircle(centerX, centerY, innerCircleRadius, mInnerCirclePaint)
    }

    private var mOutSweepAngle = 0f
    private val parentRect by lazy {
        RectF(
            (outCircleWidth / 2).toFloat(),
            (outCircleWidth / 2).toFloat(),
            width.toFloat() - (outCircleWidth / 2).toFloat(),
            height.toFloat() - (outCircleWidth / 2).toFloat()
        )
    }
    private val valueAnimator by lazy {
        ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 1000
            interpolator = AccelerateInterpolator()
            addUpdateListener { animation ->
                mOutSweepAngle = animation?.let {
                    restPercent * 360f * it.animatedValue as Float
                } ?: kotlin.run {
                    0f
                }
                invalidate()
            }
        }
    }

    private fun drawOutCircle(canvas: Canvas) {
        canvas.drawArc(parentRect, 90f, mOutSweepAngle, false, mOutCirclePaint)
    }

    private val titleYOffset by lazy {
        (height / 2 - titleTextHeight).toFloat()
    }

    private fun drawTitle(canvas: Canvas) {
        canvas.drawText(
            mTitleString,
            (width / 2).toFloat(),
            titleYOffset,
            mTitlePaint
        )
    }

    private val moneyYOffset by lazy {
        (height / 2 + moneyTextHeight).toFloat()
    }
    private val centerX by lazy {
        (width / 2).toFloat()
    }
    private val centerY by lazy {
        (height / 2).toFloat()
    }

    private fun drawMoney(canvas: Canvas) {
        canvas.drawText(
            money.toString(),
            centerX,
            moneyYOffset,
            mMoneyPaint
        )
        val moneyWidth = mMoneyPaint.measureText(money.toString())
        canvas.drawText(moneyPrefix, centerX - moneyWidth / 2, moneyYOffset, mMoneyPrefixPaint)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (valueAnimator.isRunning) {
            valueAnimator.cancel()
        }
    }

    private var totalMoney = 0f
    private val restPercent by lazy {
        money / totalMoney
    }

    fun setMoney(restMoney: Float, totalMoney: Float) {
        this.totalMoney = totalMoney
        money = restMoney
        if (valueAnimator.isRunning) {
            valueAnimator.cancel()
        }
        valueAnimator.start()
    }
}