package com.davethebrave.press

import android.text.Layout
import android.text.StaticLayout
import android.text.TextDirectionHeuristic
import android.text.TextPaint
import android.text.TextUtils.TruncateAt
import java.lang.reflect.Constructor

internal object PressLayout {

    private val constructor: Constructor<StaticLayout> =
        StaticLayout::class.java.getDeclaredConstructor(
            CharSequence::class.java,
            Int::class.javaPrimitiveType,
            Int::class.javaPrimitiveType,
            TextPaint::class.java,
            Int::class.javaPrimitiveType,
            Layout.Alignment::class.java,
            TextDirectionHeuristic::class.java,
            Float::class.javaPrimitiveType,
            Float::class.javaPrimitiveType,
            Boolean::class.javaPrimitiveType,
            TruncateAt::class.java,
            Int::class.javaPrimitiveType,
            Int::class.javaPrimitiveType
        ).apply {
            isAccessible = true
        }

    fun getLayout(
        source: CharSequence,
        paint: TextPaint,
        outerWidth: Int,
        align: Layout.Alignment,
        textDirection: TextDirectionHeuristic,
        spacingMult: Float,
        spacingAdd: Float,
        includePad: Boolean,
        ellipsis: TruncateAt,
        ellipsisWidth: Int,
        maxLines: Int
    ): StaticLayout = constructor.newInstance(
        source,
        0,
        source.length,
        paint,
        outerWidth,
        align,
        textDirection,
        spacingMult,
        spacingAdd,
        includePad,
        ellipsis,
        ellipsisWidth,
        maxLines
    )
}