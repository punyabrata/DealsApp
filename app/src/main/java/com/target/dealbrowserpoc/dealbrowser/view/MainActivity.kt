package com.target.dealbrowserpoc.dealbrowser.view

import android.app.Activity
import android.app.AlertDialog
import android.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.target.dealbrowserpoc.dealbrowser.R
import com.target.dealbrowserpoc.dealbrowser.app.Constants
import com.target.dealbrowserpoc.dealbrowser.db.DealDatabase
import com.target.dealbrowserpoc.dealbrowser.entity.Datum
import com.target.dealbrowserpoc.dealbrowser.entity.Deal
import com.target.dealbrowserpoc.dealbrowser.repository.DealRepository
import com.target.dealbrowserpoc.dealbrowser.service.TargetDealService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : Activity(), DealListFragment.OnFragmentInteractionListener {

    private val TAG: String? = "MainActivity"
    private lateinit var targetDeals: List<Datum>
    private val listFragment = DealListFragment()
    private val detailFragment = DealDetailFragment()
    private lateinit var currentScreenID: ScreenID
    private var loadAnimation = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            loadAnimation = true
            listFragment.loadAnimation(loadAnimation)
            fragmentManager.beginTransaction()
                    .add(R.id.container, listFragment)
                    .commit()

            currentScreenID = ScreenID.LIST
        }

        listFragment.setOnListItemClickListener(View.OnClickListener {
            val viewHolder = it.tag as RecyclerView.ViewHolder
            val position = viewHolder.adapterPosition
            Log.v(TAG, "Adapter position = $position")
            detailFragment.setDetailData(targetDeals[position])
            replaceFragment(currentScreenID)
        })

        val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        val targetDealService = retrofit.create(TargetDealService::class.java)

        val repository = DealRepository(DealDatabase.getInstance(this).dealDao(), targetDealService)
        repository.getDeals().observeForever {
            targetDeals = it
            listFragment.setDealsData(targetDeals)
        }
    }

    private fun getFragment(fragment: String): Fragment {
        return when (fragment) {
            "DealListFragment" -> listFragment
            "DealDetailFragment" -> detailFragment
            else -> Fragment()
        }
    }

    private fun replaceFragment(screenID: ScreenID) {
        val newScreenID = if (screenID == ScreenID.LIST) ScreenID.DETAIL else ScreenID.LIST
        fragmentManager.beginTransaction()
                .replace(R.id.container, getFragment(newScreenID.fragment))
                .commit()

        currentScreenID = newScreenID
    }

    override fun onBackPressed() {
        loadAnimation = false
        if (currentScreenID == ScreenID.DETAIL) {
            listFragment.loadAnimation(loadAnimation)
            replaceFragment(currentScreenID)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        //Don't create the menu for now
        return false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }

    override fun onFragmentInteraction(id: String) {
        val alertDialog = AlertDialog.Builder(this@MainActivity).create()
        alertDialog.setTitle("Product Id")
        alertDialog.setMessage(id)
        alertDialog.setCancelable(true)
        alertDialog.show()
    }
}
