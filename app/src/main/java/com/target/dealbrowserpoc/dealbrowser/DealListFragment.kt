package com.target.dealbrowserpoc.dealbrowser

import android.app.Activity
import android.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.target.dealbrowserpoc.dealbrowser.web.Deal
import com.target.dealbrowserpoc.dealbrowser.web.TargetDealService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DealListFragment : Fragment() {

    private val TAG: String? = "DealListFragment"
    private var mListener: OnFragmentInteractionListener? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: DealListAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        try {
            mListener = activity as OnFragmentInteractionListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$activity must implement OnFragmentInteractionListener")
        }

    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_deal_list, container, false)
        viewManager = LinearLayoutManager(activity)
        viewAdapter = DealListAdapter(activity)
        recyclerView = view!!.findViewById(R.id.deal_list)
        recyclerView.layoutManager = viewManager
        recyclerView.adapter = viewAdapter
        return view
    }

    override fun onResume() {
        super.onResume()
        val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val targetDealService = retrofit.create(TargetDealService::class.java)

        val map = mapOf("format" to "json")
        targetDealService.getDeals(map).enqueue(object: Callback<Deal> {
            override fun onFailure(call: Call<Deal>, t: Throwable) {
                Log.e(TAG, "Call failed")
                Log.e(TAG, t.message)
            }

            override fun onResponse(call: Call<Deal>, response: Response<Deal>) {
                Log.v(TAG, "Call succeeded")
                val deal = response.body()
                deal?.let { viewAdapter.setDealsData(it) }
                viewAdapter.notifyDataSetChanged()
            }
        })
    }

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(id: String)
    }

    companion object {

        fun newInstance(): DealListFragment {
            return DealListFragment()
        }
    }

}