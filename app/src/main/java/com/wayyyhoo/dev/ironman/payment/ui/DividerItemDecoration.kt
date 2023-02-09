//package com.wayyyhoo.dev.ironman.payment.ui
//
//import android.graphics.Rect
//import android.view.View
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//
//class DividerItemDecoration: RecyclerView.ItemDecoration() {
//
//    override fun getItemOffsets(
//        outRect: Rect,
//        view: View,
//        parent: RecyclerView,
//        state: RecyclerView.State
//    ) {
//        val manager = parent.layoutManager
//        val childPosition = parent.getChildAdapterPosition(view)
//        if (manager is LinearLayoutManager) {
//            if (manager.orientation == LinearLayoutManager.VERTICAL) {
//                if (childPosition != 0) {
//                    val margin = view.context.resources.getDimensionPixelOffset(R.dimen.sku_item_margin)
//                    outRect.top = margin
//                }
//            }
//        }
//    }
//
//
//}