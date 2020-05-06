package com.target.dealbrowserpoc.dealbrowser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.target.dealbrowserpoc.dealbrowser.entity.Datum
import com.target.dealbrowserpoc.dealbrowser.repository.DealRepository

class DealViewModel(private val repository: DealRepository) : ViewModel() {
    private val deals: MutableLiveData<List<Datum>> = MutableLiveData()

    fun getDeals(): LiveData<List<Datum>> {
        if (deals.value == null) {
            fetchDeals()
        }
        return deals
    }

    private fun fetchDeals() {
        repository.getDeals().observeForever {
            deals.value = it
        }
    }
}