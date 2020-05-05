package com.target.dealbrowserpoc.dealbrowser.view

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.target.dealbrowserpoc.dealbrowser.R
import com.target.dealbrowserpoc.dealbrowser.deals.DealContent
import com.target.dealbrowserpoc.dealbrowser.entity.Datum

class DealDetailFragment : Fragment() {
    private lateinit var detailData: Datum

    private lateinit var detailImage: ImageView
    private lateinit var detailPrice: TextView
    private lateinit var regularText: View
    private lateinit var regularPrice: TextView
    private lateinit var detailTitle: TextView
    private lateinit var detailDescription: TextView
    private lateinit var detailAddToCart: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.deal_detail, container, false)
        detailImage = view.findViewById(R.id.detail_image)
        detailPrice = view.findViewById(R.id.detail_price)
        regularText = view.findViewById(R.id.detail_regular_text)
        regularPrice = view.findViewById(R.id.detail_regular_price)
        detailTitle = view.findViewById(R.id.detail_title)
        detailDescription = view.findViewById(R.id.detail_desc)
        detailAddToCart = view.findViewById(R.id.detail_add_to_cart)

        detailAddToCart.setOnClickListener {
            Toast.makeText(activity, "Item added to Cart", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        populateDetail(detailData)
    }

    fun setDetailData(dealDetail: Datum) {
        detailData = dealDetail
    }

    private fun populateDetail(dealDetail: Datum) {
        if (dealDetail.salePrice == null || dealDetail.salePrice == "") {
            detailPrice.text = dealDetail.price
            regularPrice.visibility = View.GONE
            regularText.visibility = View.GONE
        } else {
            detailPrice.text = dealDetail.salePrice
            regularPrice.visibility = View.VISIBLE
            regularText.visibility = View.VISIBLE
            regularPrice.text = dealDetail.price
        }
        detailTitle.text = dealDetail.title
        detailDescription.text = dealDetail.description

        activity?.let {
            loadFromLocal(it, dealDetail)
        }
    }

    private fun loadFromNetwork(context: Context, dealDetail: Datum) {
        Glide.with(context)
                .load(dealDetail.image)
                .into(detailImage)
    }

    private fun loadFromLocal(context: Context, dealDetail: Datum) {
        Glide.with(context)
                .load(DealContent.ITEMS[dealDetail.index % 10].image)
                .into(detailImage)
    }
}
