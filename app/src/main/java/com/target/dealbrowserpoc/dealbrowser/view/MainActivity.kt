package com.target.dealbrowserpoc.dealbrowser.view

import android.app.AlertDialog
import android.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.target.dealbrowserpoc.dealbrowser.R
import com.target.dealbrowserpoc.dealbrowser.entity.Datum
import com.target.dealbrowserpoc.dealbrowser.factory.DealViewModelFactory

class MainActivity : AppCompatActivity(), DealListFragment.OnFragmentInteractionListener {

    private val TAG: String? = "MainActivity"
    private lateinit var targetDeals: List<Datum>
    private val listFragment = DealListFragment()
    private val detailFragment = DealDetailFragment()
    private lateinit var currentScreenID: ScreenID
    private var loadAnimation = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Set action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.mipmap.target_logo)

        //Show list fragment
        if (savedInstanceState == null) {
            loadAnimation = true
            listFragment.loadAnimation(loadAnimation)
            fragmentManager.beginTransaction()
                    .add(R.id.container, listFragment)
                    .commit()

            currentScreenID = ScreenID.LIST
        }

        //Set deal item click listener
        listFragment.setOnListItemClickListener(View.OnClickListener {
            val viewHolder = it.tag as RecyclerView.ViewHolder
            val position = viewHolder.adapterPosition
            Log.v(TAG, "Adapter position = $position")
            detailFragment.setDetailData(targetDeals[position])
            replaceFragment(currentScreenID)
        })

        //Observe the deals from the view model
        val dealViewModel = DealViewModelFactory.getDealViewModel(this, this)
        dealViewModel.getDeals().observeForever {
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
