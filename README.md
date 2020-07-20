# Android Drawable Dsl
[![Release](https://jitpack.io/v/tiiime/android-drawable-dsl.svg)](https://jitpack.io/#tiiime/android-drawable-dsl) ![](https://img.shields.io/github/languages/code-size/tiiime/android-drawable-dsl.svg) ![](https://img.shields.io/github/languages/top/tiiime/android-drawable-dsl.svg)

## Example

### shape
```kotlin
private fun createShapeDrawable() = shape(shape = GradientDrawable.RECTANGLE) {

    corners(
        corner = 20F,
        cornerBottomLeft = 80F
    )

    gradient(
        startColor = Color.RED,
        endColor = Color.WHITE
    )

    stroke(
        color = Color.BLACK,
        width = 10
    )
}
```

![](./screenshot/shape.png)


### layer-list
```kotlin
private fun createLayerList() = layerList(
    item {
        shape = shape {
            corners(
                cornerTopRight = 80F,
                cornerBottomLeft = 80F
            )

            gradient(
                startColor = Color.RED,
                centerColor = Color.GREEN,
                endColor = Color.BLUE
            )
        }
    },
    item(
        left = 30,
        top = 30,
        right = 30,
        bottom = 30
    ) {
        shape = shape {
            color = Color.WHITE
            corners(80F)
        }
    }
)

```

![](./screenshot/layer.png)



### more 

![](screenshot/animate.gif)


## Install

```groovy
allprojects {
    repositories {
       jcenter()
        maven { url "https://jitpack.io" }
    }
}
```

[![Release](https://jitpack.io/v/tiiime/android-drawable-dsl.svg)](https://jitpack.io/#tiiime/android-drawable-dsl)


```groovy
compile "com.github.tiiime:android-drawable-dsl:0.2.0"
```