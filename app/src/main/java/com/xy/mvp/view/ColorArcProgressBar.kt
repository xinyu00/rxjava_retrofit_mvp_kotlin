package com.xy.mvp.view

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.PaintFlagsDrawFilter
import android.graphics.RectF
import android.graphics.SweepGradient
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager

import com.xy.mvp.R


/**
 * Created by cxy on 12/4/15.
 */
class ColorArcProgressBar : View {

    private val mWidth: Int = 0
    private val mHeight: Int = 0
    private var diameter = 500  //直径
    private var centerX: Float = 0.toFloat()  //圆心X坐标
    private var centerY: Float = 0.toFloat()  //圆心Y坐标

    private var allArcPaint: Paint? = null
    private var progressPaint: Paint? = null
    private var allArcPaintBig: Paint? = null
    private var progressPaintBig: Paint? = null
    private var vTextPaint: Paint? = null
    private var hintPaint: Paint? = null
    private var degreePaint: Paint? = null
    private var curSpeedPaint: Paint? = null

    private var bgRect: RectF? = null
    private var bgRectBig: RectF? = null

    private var progressAnimator: ValueAnimator? = null
    private var mDrawFilter: PaintFlagsDrawFilter? = null
    private var sweepGradient: SweepGradient? = null
    private var rotateMatrix: Matrix? = null

    private val startAngle = 180f
    private var sweepAngle = 270f
    private var currentAngle = 0f
    private var lastAngle: Float = 0.toFloat()
    private var colors = intArrayOf(Color.GREEN, Color.YELLOW, Color.RED, Color.RED)
    private var maxValues = 100f
    private var curValues = 0f
    private var bgArcWidth = dipToPx(2f).toFloat()
    private var progressWidth = dipToPx(10f).toFloat()
    private var textSize = dipToPx(30f).toFloat()
    private var hintSize = dipToPx(14f).toFloat()
    private val curSpeedSize = dipToPx(13f).toFloat()
    private val aniSpeed = 4000
    private val longdegree = dipToPx(13f).toFloat()
    private val shortdegree = dipToPx(5f).toFloat()
    private val DEGREE_PROGRESS_DISTANCE = dipToPx(8f)

    private val hintColor = "#AAAAAA"
    private val titleColor = "#1C86EE"
    private val longDegreeColor = "#111111"
    private val shortDegreeColor = "#111111"
    private val bgArcColor = "#F2F2F2"
    private var titleString: String? = null
    private var hintString: String? = null

    private val isShowCurrentSpeed = true
    private var isNeedTitle: Boolean = false
    private var isNeedUnit: Boolean = false
    private var isNeedDial: Boolean = false
    private var isNeedContent: Boolean = false

    // sweepAngle / maxValues 的值
    private var k: Float = 0.toFloat()

    constructor(context: Context) : super(context, null) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs, 0) {
        initCofig(context, attrs)
        initView()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initCofig(context, attrs)
        initView()
    }

    /**
     * 初始化布局配置

     * @param context
     * *
     * @param attrs
     */
    private fun initCofig(context: Context, attrs: AttributeSet) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.ColorArcProgressBar)
        val color1 = a.getColor(R.styleable.ColorArcProgressBar_front_color1, Color.GREEN)
        val color2 = a.getColor(R.styleable.ColorArcProgressBar_front_color2, color1)
        val color3 = a.getColor(R.styleable.ColorArcProgressBar_front_color3, color1)
        colors = intArrayOf(color1, color2, color3, color3)

        sweepAngle = a.getInteger(R.styleable.ColorArcProgressBar_total_engle, 270).toFloat()
        bgArcWidth = a.getDimension(R.styleable.ColorArcProgressBar_back_width, dipToPx(2f).toFloat())
        progressWidth = a.getDimension(R.styleable.ColorArcProgressBar_front_width, dipToPx(10f).toFloat())
        isNeedTitle = a.getBoolean(R.styleable.ColorArcProgressBar_is_need_title, false)
        isNeedContent = a.getBoolean(R.styleable.ColorArcProgressBar_is_need_content, false)
        isNeedUnit = a.getBoolean(R.styleable.ColorArcProgressBar_is_need_unit, false)
        isNeedDial = a.getBoolean(R.styleable.ColorArcProgressBar_is_need_dial, false)
        hintString = a.getString(R.styleable.ColorArcProgressBar_string_unit)
        titleString = a.getString(R.styleable.ColorArcProgressBar_string_title)
        curValues = a.getFloat(R.styleable.ColorArcProgressBar_current_value, 0f)
        maxValues = a.getFloat(R.styleable.ColorArcProgressBar_max_value, 60f)
        setCurrentValues(curValues)
        setMaxValues(maxValues)
        a.recycle()

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = (2 * longdegree + progressWidth + diameter.toFloat() + (2 * DEGREE_PROGRESS_DISTANCE).toFloat()).toInt()
        val height = (2 * longdegree + progressWidth + diameter.toFloat() + (2 * DEGREE_PROGRESS_DISTANCE).toFloat()).toInt()
        setMeasuredDimension(width, height)
    }

    private fun initView() {

        diameter = 3 * screenWidth / 5
        //弧形的矩阵区域
        bgRect = RectF()
        bgRect!!.top = longdegree + progressWidth / 2 + DEGREE_PROGRESS_DISTANCE.toFloat()
        bgRect!!.left = longdegree + progressWidth / 2 + DEGREE_PROGRESS_DISTANCE.toFloat()
        bgRect!!.right = diameter + (longdegree + progressWidth / 2 + DEGREE_PROGRESS_DISTANCE.toFloat())
        bgRect!!.bottom = diameter + (longdegree + progressWidth / 2 + DEGREE_PROGRESS_DISTANCE.toFloat())
        //大弧形的矩阵区域
        bgRectBig = RectF()
        bgRectBig!!.top = longdegree + progressWidth / 2 + DEGREE_PROGRESS_DISTANCE.toFloat() - dipToPx(15f)
        bgRectBig!!.left = longdegree + progressWidth / 2 + DEGREE_PROGRESS_DISTANCE.toFloat() - dipToPx(15f)
        bgRectBig!!.right = diameter.toFloat() + (longdegree + progressWidth / 2 + DEGREE_PROGRESS_DISTANCE.toFloat()) + dipToPx(15f).toFloat()
        bgRectBig!!.bottom = diameter.toFloat() + (longdegree + progressWidth / 2 + DEGREE_PROGRESS_DISTANCE.toFloat()) + dipToPx(15f).toFloat()

        //圆心
        centerX = (2 * longdegree + progressWidth + diameter.toFloat() + (2 * DEGREE_PROGRESS_DISTANCE).toFloat()) / 2
        centerY = (2 * longdegree + progressWidth + diameter.toFloat() + (2 * DEGREE_PROGRESS_DISTANCE).toFloat()) / 2

        //外部刻度线
        degreePaint = Paint()
        degreePaint!!.color = Color.parseColor(longDegreeColor)

        //整个弧形
        allArcPaint = Paint()
        allArcPaint!!.isAntiAlias = true
        allArcPaint!!.style = Paint.Style.STROKE
        allArcPaint!!.strokeWidth = bgArcWidth
        allArcPaint!!.color = Color.parseColor(bgArcColor)
        allArcPaint!!.strokeCap = Paint.Cap.ROUND
        //外弧度
        allArcPaintBig = Paint()
        allArcPaintBig!!.isAntiAlias = true
        allArcPaintBig!!.style = Paint.Style.STROKE
        allArcPaintBig!!.strokeWidth = dipToPx(2f).toFloat()
        allArcPaintBig!!.color = Color.parseColor(bgArcColor)
        allArcPaintBig!!.strokeCap = Paint.Cap.ROUND

        //当前进度的弧形
        progressPaint = Paint()
        progressPaint!!.isAntiAlias = true
        progressPaint!!.style = Paint.Style.STROKE
        progressPaint!!.strokeCap = Paint.Cap.ROUND
        progressPaint!!.strokeWidth = progressWidth
        progressPaint!!.color = Color.GREEN

        //当前进度的外弧
        progressPaintBig = Paint()
        progressPaintBig!!.isAntiAlias = true
        progressPaintBig!!.style = Paint.Style.STROKE
        progressPaintBig!!.strokeCap = Paint.Cap.ROUND
        progressPaintBig!!.strokeWidth = dipToPx(3f).toFloat()
        progressPaintBig!!.color = Color.GREEN

        //内容显示文字
        vTextPaint = Paint()
        vTextPaint!!.textSize = textSize
        vTextPaint!!.color = Color.parseColor("#1C86EE")
        vTextPaint!!.textAlign = Paint.Align.CENTER

        //显示单位文字
        hintPaint = Paint()
        hintPaint!!.textSize = hintSize
        hintPaint!!.color = Color.parseColor(hintColor)
        hintPaint!!.textAlign = Paint.Align.CENTER

        //显示标题文字
        curSpeedPaint = Paint()
        curSpeedPaint!!.textSize = curSpeedSize
        curSpeedPaint!!.color = Color.parseColor(titleColor)
        curSpeedPaint!!.textAlign = Paint.Align.CENTER

        mDrawFilter = PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
        sweepGradient = SweepGradient(centerX, centerY, colors, null)
        rotateMatrix = Matrix()

    }

    override fun onDraw(canvas: Canvas) {
        //抗锯齿
        canvas.drawFilter = mDrawFilter

        if (isNeedDial) {
            //画刻度线
            for (i in 0..39) {
                if (i > 15 && i < 25) {
                    canvas.rotate(9f, centerX, centerY)
                    continue
                }
                if (i % 5 == 0) {
                    degreePaint!!.strokeWidth = dipToPx(2f).toFloat()
                    degreePaint!!.color = Color.parseColor(longDegreeColor)
                    canvas.drawLine(centerX, centerY - (diameter / 2).toFloat() - progressWidth / 2 - DEGREE_PROGRESS_DISTANCE.toFloat(),
                            centerX, centerY - (diameter / 2).toFloat() - progressWidth / 2 - DEGREE_PROGRESS_DISTANCE.toFloat() - longdegree, degreePaint!!)
                } else {
                    degreePaint!!.strokeWidth = dipToPx(1.4f).toFloat()
                    degreePaint!!.color = Color.parseColor(shortDegreeColor)
                    canvas.drawLine(centerX, centerY - (diameter / 2).toFloat() - progressWidth / 2 - DEGREE_PROGRESS_DISTANCE.toFloat() - (longdegree - shortdegree) / 2,
                            centerX, centerY - (diameter / 2).toFloat() - progressWidth / 2 - DEGREE_PROGRESS_DISTANCE.toFloat() - (longdegree - shortdegree) / 2 - shortdegree, degreePaint!!)
                }
                canvas.rotate(9f, centerX, centerY)
            }
        }

        //整个弧
        canvas.drawArc(bgRect!!, startAngle, sweepAngle, false, allArcPaint!!)
        //整个弧
        canvas.drawArc(bgRectBig!!, startAngle, sweepAngle, false, allArcPaintBig!!)

        //设置渐变色
        rotateMatrix!!.setRotate(130f, centerX, centerY)
        sweepGradient!!.setLocalMatrix(rotateMatrix)
        progressPaint!!.shader = sweepGradient
        progressPaintBig!!.shader = sweepGradient

        //当前进度
        canvas.drawArc(bgRect!!, startAngle, currentAngle, false, progressPaint!!)
        //当前进度
        canvas.drawArc(bgRectBig!!, startAngle, currentAngle, false, progressPaintBig!!)

        if (isNeedContent) {
            canvas.drawText(String.format("%.0f", curValues), centerX - textSize / 3, centerY - textSize * 1.5f, vTextPaint!!)
        }
        if (isNeedUnit) {
            canvas.drawText(hintString!!, centerX, centerY - textSize, hintPaint!!)
        }
        if (isNeedTitle) {
            canvas.drawText(titleString!!, centerX + textSize / 3 * 2, centerY - textSize * 1.5f, curSpeedPaint!!)
        }

        invalidate()

    }

    /**
     * 设置最大值

     * @param maxValues
     */
    fun setMaxValues(maxValues: Float) {
        this.maxValues = maxValues
        k = sweepAngle / maxValues
    }

    /**
     * 设置当前值

     * @param currentValues
     */
    fun setCurrentValues(currentValues: Float) {
        var currentValues = currentValues
        if (currentValues > maxValues) {
            currentValues = maxValues
        }
        if (currentValues < 0) {
            currentValues = 0f
        }
        this.curValues = currentValues
        lastAngle = currentAngle
        setAnimation(lastAngle, currentValues * k, aniSpeed)
    }

    /**
     * 设置整个圆弧宽度

     * @param bgArcWidth
     */
    fun setBgArcWidth(bgArcWidth: Int) {
        this.bgArcWidth = bgArcWidth.toFloat()
    }

    /**
     * 设置进度宽度

     * @param progressWidth
     */
    fun setProgressWidth(progressWidth: Int) {
        this.progressWidth = progressWidth.toFloat()
    }

    /**
     * 设置速度文字大小

     * @param textSize
     */
    fun setTextSize(textSize: Int) {
        this.textSize = textSize.toFloat()
    }

    /**
     * 设置单位文字大小

     * @param hintSize
     */
    fun setHintSize(hintSize: Int) {
        this.hintSize = hintSize.toFloat()
    }

    /**
     * 设置单位文字

     * @param hintString
     */
    fun setUnit(hintString: String) {
        this.hintString = hintString
        invalidate()
    }

    /**
     * 设置直径大小

     * @param diameter
     */
    fun setDiameter(diameter: Int) {
        this.diameter = dipToPx(diameter.toFloat())
    }

    /**
     * 设置标题

     * @param title
     */
    private fun setTitle(title: String) {
        this.titleString = title
    }

    /**
     * 设置是否显示标题

     * @param isNeedTitle
     */
    private fun setIsNeedTitle(isNeedTitle: Boolean) {
        this.isNeedTitle = isNeedTitle
    }

    /**
     * 设置是否显示单位文字

     * @param isNeedUnit
     */
    private fun setIsNeedUnit(isNeedUnit: Boolean) {
        this.isNeedUnit = isNeedUnit
    }

    /**
     * 设置是否显示外部刻度盘

     * @param isNeedDial
     */
    private fun setIsNeedDial(isNeedDial: Boolean) {
        this.isNeedDial = isNeedDial
    }

    /**
     * 为进度设置动画

     * @param last
     * *
     * @param current
     */
    private fun setAnimation(last: Float, current: Float, length: Int) {
        progressAnimator = ValueAnimator.ofFloat(last, current)
        progressAnimator!!.duration = length.toLong()
        progressAnimator!!.setTarget(currentAngle)
        progressAnimator!!.addUpdateListener { animation ->
            currentAngle = animation.animatedValue as Float
            curValues = currentAngle / k
        }
        progressAnimator!!.start()
    }

    /**
     * dip 转换成px

     * @param dip
     * *
     * @return
     */
    private fun dipToPx(dip: Float): Int {
        val density = context.resources.displayMetrics.density
        return (dip * density + 0.5f * if (dip >= 0) 1 else -1).toInt()
    }

    /**
     * 得到屏幕宽度

     * @return
     */
    private val screenWidth: Int
        get() {
            val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            return displayMetrics.widthPixels
        }
}
