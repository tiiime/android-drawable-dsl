package com.github.tiiime.android.ktx

import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import androidx.annotation.ColorInt

inline fun shape(
    shape: Int = GradientDrawable.RECTANGLE,
    init: (ShapeSetter.() -> Unit) = {}
): GradientDrawable = ShapeSetter()
    .apply(init)
    .let(ShapeSetter::drawable)
    .apply { setShape(shape) }

fun layerList(vararg array: LayerListItem): LayerDrawable = LayerDrawable(
    array.map(LayerListItem::drawable).toTypedArray()
).apply {
    array.forEachIndexed { index, item ->
        setLayerInset(index, item.left, item.top, item.right, item.bottom)
    }
}

inline fun item(
    shape: Drawable? = null,
    left: Int = 0,
    top: Int = 0,
    right: Int = 0,
    bottom: Int = 0,
    init: LayerListItem.() -> Unit
): LayerListItem = LayerListItem(shape, left, top, right, bottom).apply(init)

class ShapeSetter(val drawable: GradientDrawable = GradientDrawable()) {

    fun gradient(
        type: Int = GradientDrawable.LINEAR_GRADIENT,
        @ColorInt startColor: Int,
        @ColorInt centerColor: Int? = null,
        @ColorInt endColor: Int,
        angel: GradientDrawable.Orientation = GradientDrawable.Orientation.TOP_BOTTOM
    ) {
        drawable.colors = if (centerColor == null) {
            intArrayOf(startColor, endColor)
        } else {
            intArrayOf(startColor, centerColor, endColor)
        }
        drawable.gradientType = type
        drawable.orientation = angel
    }

    fun corners(
        corner: Float = 0F,
        cornerBottomLeft: Float = corner,
        cornerBottomRight: Float = corner,
        cornerTopLeft: Float = corner,
        cornerTopRight: Float = corner
    ) {
        drawable.cornerRadii = floatArrayOf(
            cornerTopLeft, cornerTopLeft,
            cornerTopRight, cornerTopRight,
            cornerBottomRight, cornerBottomRight,
            cornerBottomLeft, cornerBottomLeft
        )
    }

    fun stroke(
        @ColorInt color: Int,
        dashGap: Float = 0F,
        dashWidth: Float = 0F,
        width: Int = 0
    ) = drawable.setStroke(width, color, dashWidth, dashGap)

    @ColorInt
    var color: Int? = null
        set(value) = value.apply { field = value }?.run(drawable::setColor) ?: Unit
}

class LayerListItem(
    var drawable: Drawable? = null,
    var left: Int = 0,
    var top: Int = 0,
    var right: Int = 0,
    var bottom: Int = 0
)