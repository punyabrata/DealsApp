package com.target.dealbrowserpoc.dealbrowser

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.target.dealbrowserpoc.dealbrowser.web.Deal
import com.target.dealbrowserpoc.dealbrowser.web.TargetDealService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : Activity(), DealListFragment.OnFragmentInteractionListener {

    private val TAG: String? = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.container, DealListFragment())
                    .commit()
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
                deal?.data?.forEach { Log.v(TAG, "${it.aisle}, ${it.guid}, ${it.index}, ${it.price}, ${it.salePrice}, ${it.title}") }
            }
        })
    }

}
