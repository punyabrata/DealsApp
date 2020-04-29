package com.target.dealbrowserpoc.dealbrowser

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.target.dealbrowserpoc.dealbrowser.deals.DealContent
import com.target.dealbrowserpoc.dealbrowser.web.Deal

class DealListAdapter(val context: Context) : RecyclerView.Adapter<DealListAdapter.DealViewHolder>() {
    private var deal = Deal()
    private val TAG: String? = "DealListFragment"

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): DealViewHolder {
        val constraintLayout = LayoutInflater.from(p0.context)
                .inflate(R.layout.deal_item, p0, false) as ConstraintLayout
        return DealViewHolder(constraintLayout)
    }

    override fun getItemCount(): Int {
        Log.v(TAG, "Data size ${deal.data?.size ?: 0}")
        return deal.data?.size ?: return 0
    }

    override fun onBindViewHolder(p0: DealViewHolder, p1: Int) {
        Log.v(TAG, "Data index = $p1")
//        Glide.with(context)
//                .load(R.drawable.spinner_1s_200px)
//                .into(p0.itemImage)

        p0.itemTitle.text = deal.data[p1].title
        p0.itemPrice.text = deal.data[p1].price
        p0.itemAisle.text = deal.data[p1].aisle.toUpperCase()

//        Glide.with(context)
//                .setDefaultRequestOptions(RequestOptions().timeout(5000))
//                .load(deal.data[p1].image)
//                .into(p0.itemImage)

        Glide.with(context)
                .load(DealContent.ITEMS[p1%10].image)
                .into(p0.itemImage)
    }

    fun setDealsData(deal: Deal) {
        this.deal = deal
    }

    class DealViewHolder(constraintLayout: ConstraintLayout) : RecyclerView.ViewHolder(constraintLayout) {
        val itemImage = constraintLayout.findViewById<ImageView>(R.id.item_image)
        val itemTitle = constraintLayout.findViewById<TextView>(R.id.item_title)
        val itemPrice = constraintLayout.findViewById<TextView>(R.id.item_price)
        val itemAisle = constraintLayout.findViewById<TextView>(R.id.item_aisle)
    }
}