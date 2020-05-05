package com.target.dealbrowserpoc.dealbrowser.view

import android.content.Context
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.target.dealbrowserpoc.dealbrowser.R
import com.target.dealbrowserpoc.dealbrowser.deals.DealContent
import com.target.dealbrowserpoc.dealbrowser.entity.Datum

class DealListAdapter(val context: Context, val loadAnimation: Boolean) : RecyclerView.Adapter<DealListAdapter.DealViewHolder>() {
    //    private var deal = Deal()
    private var deals: List<Datum>? = null
    private val TAG: String? = "DealListFragment"
    private lateinit var onItemClickListener: View.OnClickListener

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): DealViewHolder {
        val constraintLayout = LayoutInflater.from(p0.context)
                .inflate(R.layout.deal_item, p0, false) as ConstraintLayout
        return DealViewHolder(constraintLayout)
    }

    override fun getItemCount(): Int {
        Log.v(TAG, "Data size ${deals?.size ?: 0}")
        return deals?.size ?: return 0
    }

    override fun onBindViewHolder(p0: DealViewHolder, p1: Int) {
        Log.v(TAG, "Data index = $p1")
        Glide.with(context)
                .load(R.drawable.spinner_1s_200px)
                .into(p0.itemImage)

        p0.itemTitle.text = deals!![p1].title
        p0.itemPrice.text = deals!![p1].price
        p0.itemAisle.text = deals!![p1].aisle.toUpperCase()

        val runnable = Runnable {
            loadFromLocal(p1, p0)
        }

        Handler().postDelayed(runnable, if (loadAnimation) 2000 else 0)
    }

    private fun loadFromNetwork(index: Int, viewHolder: DealViewHolder) {
        Glide.with(context)
                .setDefaultRequestOptions(RequestOptions().timeout(5000))
                .load(deals!![index].image)
                .into(viewHolder.itemImage)
    }

    private fun loadFromLocal(index: Int, viewHolder: DealViewHolder) {
        Glide.with(context)
                .load(DealContent.ITEMS[index % 10].image)
                .into(viewHolder.itemImage)
    }

    fun setItemClickListener(listener: View.OnClickListener) {
        onItemClickListener = listener
    }

    fun setDealsData(deals: List<Datum>) {
        this.deals = deals
    }

    inner class DealViewHolder(constraintLayout: ConstraintLayout) : RecyclerView.ViewHolder(constraintLayout) {
        val itemImage: ImageView = constraintLayout.findViewById(R.id.item_image)
        val itemTitle: TextView = constraintLayout.findViewById(R.id.item_title)
        val itemPrice: TextView = constraintLayout.findViewById(R.id.item_price)
        val itemAisle: TextView = constraintLayout.findViewById(R.id.item_aisle)

        init {
            constraintLayout.tag = this
            constraintLayout.setOnClickListener(onItemClickListener)
        }
    }
}