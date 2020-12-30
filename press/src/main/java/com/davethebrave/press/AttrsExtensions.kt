package com.davethebrave.press

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Typeface
import android.graphics.Typeface.DEFAULT
import android.text.Layout.Alignment
import android.text.Layout.Alignment.*
import android.text.TextDirectionHeuristic
import android.text.TextDirectionHeuristics.LTR
import android.text.TextDirectionHeuristics.RTL
import android.text.TextUtils.TruncateAt
import android.util.AttributeSet
import android.view.Gravity.*
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.annotation.StyleableRes
import androidx.core.content.res.ResourcesCompat
import android.text.TextUtils.TruncateAt.END as ELLIPSIS_END
import android.text.TextUtils.TruncateAt.MARQUEE as ELLIPSIS_MARQUEE
import android.text.TextUtils.TruncateAt.MIDDLE as ELLIPSIS_MIDDLE
import android.text.TextUtils.TruncateAt.START as ELLIPSIS_START
import android.view.Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK as HORIZONTAL_MASK
import android.view.Gravity.VERTICAL_GRAVITY_MASK as VERTICAL_MASK

internal fun AttributeSet?.read(
    context: Context,
    @StyleableRes attrs: IntArray,
    @AttrRes defStyleAttr: Int,
    @StyleRes defStyleRes: Int,
    block: TypedArray.() -> Unit
) = context.theme.obtainStyledAttributes(
    this,
    attrs,
    defStyleAttr,
    defStyleRes
).apply {
    try {
        block(this)
    } finally {
        recycle()
    }
}

internal fun Int.applyGravity(): Int {
    var gravity = 0
    when {
        this and HORIZONTAL_MASK == 0 -> gravity = this or START
        this and VERTICAL_MASK == 0 -> gravity = this or TOP
    }
    return gravity
}

internal fun Int.asTypeface(context: Context): Typeface = when (this) {
    0 -> DEFAULT
    else -> ResourcesCompat.getFont(context, this) ?: DEFAULT
}

internal fun Int.toAlignment(gravity: Int): Alignment = when (this) {
    1 -> ALIGN_OPPOSITE
    2 -> ALIGN_CENTER
    3 -> when (gravity and HORIZONTAL_MASK) {
        END -> ALIGN_OPPOSITE
        CENTER, CENTER_HORIZONTAL -> ALIGN_CENTER
        else -> ALIGN_NORMAL
    }
    else -> ALIGN_NORMAL
}

internal fun Int.toDirection(): TextDirectionHeuristic = when (this) {
    1 -> RTL
    else -> LTR
}

internal fun Int.toEllipsis(): TruncateAt = when (this) {
    0 -> ELLIPSIS_START
    1 -> ELLIPSIS_MIDDLE
    3 -> ELLIPSIS_MARQUEE
    else -> ELLIPSIS_END
}