package com.khair.appforitis.presentation.base

import android.content.Context
import android.content.res.Resources
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class VerticalItemDecoration(
    private val context: Context,
    private val topMarginInDp: Int,
    private val bottomMarginInDp: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val resources: Resources = context.resources
        if(parent.getChildAdapterPosition(view) == 0){
            outRect.top = mapDpToPx(resources, topMarginInDp)
        }
        outRect.bottom = mapDpToPx(resources, bottomMarginInDp)
//        parent.adapter?.let {
//            if(parent.getChildAdapterPosition(view) < it.itemCount - 1){
//                outRect.bottom = mapDpToPx(resources, bottomMarginInDp)
//            }
//        }
    }

    private fun mapDpToPx(resources: Resources, dp: Int): Int{
        val px = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            resources.displayMetrics
        )
        return px.toInt()
    }
}