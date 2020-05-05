package com.target.dealbrowserpoc.dealbrowser.view

import android.app.Activity
import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.target.dealbrowserpoc.dealbrowser.R
import com.target.dealbrowserpoc.dealbrowser.entity.Datum

class DealListFragment : Fragment() {

    private val TAG: String? = "DealListFragment"
    private var mListener: OnFragmentInteractionListener? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: DealListAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var itemClickListener: View.OnClickListener
    private lateinit var dealsData: List<Datum>
    private var loadAnimation = false

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
        viewAdapter = DealListAdapter(activity, loadAnimation)
        recyclerView = view!!.findViewById(R.id.deal_list)
        recyclerView.layoutManager = viewManager
        recyclerView.adapter = viewAdapter

        viewAdapter.setItemClickListener(itemClickListener)

        return view
    }

    fun setOnListItemClickListener(listener: View.OnClickListener) {
        itemClickListener = listener
    }

    fun setDealsData(deals: List<Datum>) {
        dealsData = deals
        populateDeals()
    }

    fun loadAnimation(load: Boolean) {
        loadAnimation = load
    }

    private fun populateDeals() {
        if (this::dealsData.isInitialized) {
            viewAdapter.setDealsData(dealsData)
            viewAdapter.notifyDataSetChanged()
        }
    }

    override fun onResume() {
        super.onResume()
        populateDeals()
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