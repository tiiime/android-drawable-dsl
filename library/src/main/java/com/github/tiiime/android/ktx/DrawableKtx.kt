package com.github.tiiime.android.ktx

import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.RotateDrawable
import android.os.Build
import androidx.annotation.ColorInt

inline fun rotate(
    fromDegrees: Float? = null,
    pivotY: Float? = null,
    pivotX: Float? = null,
    toDegrees: Float? = null,
    init: (RotateDrawable.() -> Unit) = {}
): RotateDrawable = RotateDrawable()
    .apply {
        fromDegrees?.run(this::setFromDegrees)
        toDegrees?.run(this::setToDegrees)
        pivotX?.run(this::setPivotX)
        pivotY?.run(this::setPivotY)
    }
    .apply(init)

inline fun shape(
    shape: Int = GradientDrawable.RECTANGLE,
    innerRadiusRatio: Float? = null,
    thicknessRatio: Float? = null,
    thickness: Int? = null,
    init: (ShapeSetter.() -> Unit) = {}
): GradientDrawable = ShapeSetter()
    .apply(init)
    .let(ShapeSetter::drawable)
    .apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            innerRadiusRatio?.run(::setInnerRadiusRatio)
            thicknessRatio?.run(::setThicknessRatio)
            thickness?.run(::setThickness)
        }
        setShape(shape)
    }

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
        angel: GradientDrawable.Orientation = GradientDrawable.Orientation.TOP_BOTTOM,
        centerX: Float = 0.5F,
        centerY: Float = 0.5F,
        useLevel: Boolean = false
    ) {
        val array = if (centerColor == null) {
            intArrayOf(startColor, endColor)
        } else {
            intArrayOf(startColor, centerColor, endColor)
        }

        gradient(type, array, angel, centerX, centerY, useLevel)
    }

    fun gradient(
        type: Int = GradientDrawable.LINEAR_GRADIENT,
        colorArray: IntArray,
        angel: GradientDrawable.Orientation = GradientDrawable.Orientation.TOP_BOTTOM,
        centerX: Float = 0.5F,
        centerY: Float = 0.5F,
        useLevel: Boolean = false
    ) {
        drawable.colors = colorArray
        drawable.gradientType = type
        drawable.orientation = angel
        drawable.setGradientCenter(centerX, centerY)
        drawable.useLevel = useLevel
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

    fun size(width: Int, height: Int) = drawable.setSize(width, height)

    fun solid(@ColorInt color: Int) = drawable.setColor(color)

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
