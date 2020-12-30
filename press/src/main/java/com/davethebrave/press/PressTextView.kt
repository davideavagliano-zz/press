package com.davethebrave.press

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color.BLACK
import android.graphics.Typeface
import android.graphics.Typeface.DEFAULT
import android.text.Layout.Alignment
import android.text.Layout.Alignment.ALIGN_NORMAL
import android.text.StaticLayout
import android.text.TextDirectionHeuristic
import android.text.TextDirectionHeuristics.LTR
import android.text.TextPaint
import android.text.TextUtils.TruncateAt
import android.util.AttributeSet
import android.view.Gravity.START
import android.view.Gravity.TOP
import android.view.View
import android.view.View.MeasureSpec.*
import com.davethebrave.press.PressLayout.getLayout
import kotlin.Int.Companion.MAX_VALUE
import kotlin.math.min
import android.text.TextUtils.TruncateAt.END as ELLIPSIS_END

class PressTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {

    private lateinit var layout: StaticLayout

    private var text: String = ""
    private var textSize: Float = 16f
    private var textColor: Int = BLACK
    private var typeface: Typeface = DEFAULT
    private var gravity = START or TOP
    private var textAlignment: Alignment = ALIGN_NORMAL
    private var textDirection: TextDirectionHeuristic = LTR
    private var lineSpacing: Float = 0f
    private var lineSpacingMultiplier: Float = 1f
    private var includeFontPadding: Boolean = false
    private var ellipsis: TruncateAt = ELLIPSIS_END
    private var maxLines: Int = MAX_VALUE
    private var paint = TextPaint().also { it.isAntiAlias = true }

    init {

        attrs.read(context, R.styleable.PressTextView, defStyleAttr, defStyleRes) {
            text = getString(R.styleable.PressTextView_text) ?: "Test"
            textSize = getDimension(R.styleable.PressTextView_textSize, 16f)
            textColor = getColor(R.styleable.PressTextView_textColor, BLACK)
            typeface = getResourceId(R.styleable.PressTextView_fontFamily, 0).asTypeface(context)
            gravity = getInt(R.styleable.PressTextView_gravity, START or TOP).applyGravity()
            textAlignment = getInt(R.styleable.PressTextView_textAlignment, 0).toAlignment(gravity)
            textDirection = getInt(R.styleable.PressTextView_textDirection, 0).toDirection()
            lineSpacing = getDimension(R.styleable.PressTextView_lineSpacing, 0f)
            lineSpacingMultiplier = getFloat(R.styleable.PressTextView_lineSpacingMultiplier, 1f)
            includeFontPadding = getBoolean(R.styleable.PressTextView_includeFontPadding, false)
            ellipsis = getInt(R.styleable.PressTextView_ellipsize, 2).toEllipsis()
            maxLines = getInt(R.styleable.PressTextView_maxLines, MAX_VALUE)
        }

        paint.textSize = textSize
        paint.color = textColor
        paint.typeface = typeface
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val widthSize = getSize(widthMeasureSpec)
        val heightSize = getSize(heightMeasureSpec)

        layout = getLayout(
            source = text,
            paint = paint,
            outerWidth = widthSize - paddingStart - paddingEnd,
            align = textAlignment,
            textDirection = textDirection,
            spacingMult = lineSpacingMultiplier,
            spacingAdd = lineSpacing,
            includePad = includeFontPadding,
            ellipsis = ellipsis,
            ellipsisWidth = widthSize - paddingStart - paddingEnd,
            maxLines = maxLines
        )

        val layoutWidth = layout.width + paddingStart + paddingEnd
        val layoutHeight = layout.height + paddingTop + paddingBottom

        setMeasuredDimension(
            when (getMode(widthMeasureSpec)) {
                EXACTLY -> widthSize
                AT_MOST -> min(layoutWidth, widthSize)
                else -> layoutWidth
            }, when (getMode(heightMeasureSpec)) {
                EXACTLY -> heightSize
                AT_MOST -> min(layoutHeight, heightSize)
                else -> layoutHeight
            }
        )
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        layout.draw(canvas)
    }
}