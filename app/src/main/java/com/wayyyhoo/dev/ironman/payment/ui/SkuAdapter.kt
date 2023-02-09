//package com.wayyyhoo.dev.ironman.payment.ui
//
//import android.content.Context
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.CheckBox
//import android.widget.TextView
//import androidx.core.content.res.ResourcesCompat
//import androidx.recyclerview.widget.RecyclerView
//import com.boost.iap.R
//import com.wayyyhoo.dev.ironman.payment.bean.SubscribeDetail
//
//open class SkuAdapter(private val context: Context) : RecyclerView.Adapter<SkuAdapter.ListItemHolder>() {
//
//    private var itemClickable: Boolean = true
//    var dataList: List<SubscribeDetail>? = null
//
//    override fun getItemCount(): Int {
//        if (dataList == null) {
//            return 0
//        }
//        return dataList!!.size
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemHolder {
//        val view: View = LayoutInflater.from(context).inflate(R.layout.item_iap_sku, parent, false)
//        return ListItemHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ListItemHolder, position: Int) {
//        if (dataList == null) {
//            return
//        }
//        val listBean = dataList!![position]
//        holder.itemView.setOnClickListener(View.OnClickListener {
//            //item点击事件是否生效
//            if(!itemClickable){
//                return@OnClickListener
//            }
//            mListener?.onItemClick(position, listBean)
//
//            listBean.isItemSelect = true
//            holder.cbItem.isChecked = true
//
//            dataList!!.forEachIndexed { index, subscribeDetail ->
//                if (index != position && subscribeDetail.isItemSelect) {
//                    subscribeDetail.isItemSelect = false
//                    notifyItemChanged(index)
//                }
//            }
//        })
//
//        holder.cbItem.isChecked = listBean.isItemSelect
//        holder.cbItem.isClickable = false
//        holder.tvName.text = listBean.productTitle
//        holder.tvDesc.text = listBean.productPrice + listBean.productDesc
//
//        if (listBean.paid) {
//            holder.cbItem.background = ResourcesCompat.getDrawable(context.resources, R.drawable.bg_paid_check, null)
//            holder.tvName.setTextColor(context.resources.getColor(R.color.color_product_title_paid, null))
//            holder.tvDesc.setTextColor(context.resources.getColor(R.color.color_product_desc_paid, null))
//        }
//    }
//
//    class ListItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        var cbItem: CheckBox
//        var tvName: TextView
//        var tvDesc: TextView
//
//        init {
//            cbItem = itemView.findViewById(R.id.cb_product)
//            tvName = itemView.findViewById(R.id.tv_product_title)
//            tvDesc = itemView.findViewById(R.id.tv_product_desc)
//        }
//    }
//
//    private var mListener: OnItemClickListener? = null
//
//    interface OnItemClickListener {
//        fun onItemClick(position: Int, bean: SubscribeDetail)
//    }
//
//    fun setOnItemClickListener(li: OnItemClickListener?) {
//        mListener = li
//    }
//
//    /**
//     * item是否可以点击
//     * true：可以点击  false：不可以点击
//     */
//    open fun setItemClickable(clickable: Boolean) {
//        this.itemClickable = clickable
//    }
//}