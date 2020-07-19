package com.github.tiiime.android.drawable.dsl

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.tiiime.android.ktx.item
import com.github.tiiime.android.ktx.layerList
import com.github.tiiime.android.ktx.shape
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val showcaseList: MutableList<Drawable> = mutableListOf()
        showcaseList.add(createShapeDrawable())
        showcaseList.add(createCorner())
        showcaseList.add(createLayerList())
        showcaseList.add(createMoreColor())

        list.adapter = Adapter(showcaseList)
        list.layoutManager = LinearLayoutManager(this)
    }

    //<editor-fold desc="Adapter">
    private inner class Adapter(val showcaseList: MutableList<Drawable>) :
        RecyclerView.Adapter<DrawableHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DrawableHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.drawable_item, parent, false)
        )

        override fun getItemCount(): Int = showcaseList.count()

        override fun onBindViewHolder(holder: DrawableHolder, position: Int) {
            holder.itemView.background = showcaseList[position]
        }
    }

    private class DrawableHolder(view: View) : RecyclerView.ViewHolder(view)
    //</editor-fold>

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

    private fun createCorner() = shape {
        color = Color.RED

        corners(
            corner = 20F
        )

        stroke(
            color = Color.GREEN,
            width = 10,
            dashGap = 10F,
            dashWidth = 10F
        )
    }

    private fun createLayerList() = layerList(
        item {
            drawable = shape {
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
            drawable = shape {
                color = Color.WHITE
                corners(80F)
            }
        }
    )

    private fun createMoreColor(): Drawable = shape(shape = GradientDrawable.OVAL) {
        drawable.colors = intArrayOf(
            Color.RED,
            Color.TRANSPARENT,
            Color.BLUE,
            Color.TRANSPARENT,
            Color.YELLOW,
            Color.TRANSPARENT,
            Color.GREEN
        )
    }
}