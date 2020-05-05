package com.target.dealbrowserpoc.dealbrowser.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.target.dealbrowserpoc.dealbrowser.db.dao.DealDao
import com.target.dealbrowserpoc.dealbrowser.entity.Datum
import com.target.dealbrowserpoc.dealbrowser.entity.Deal
import com.target.dealbrowserpoc.dealbrowser.service.TargetDealService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DealRepository(val dealDao: DealDao, val targetDealService: TargetDealService) {
    private val TAG: String? = "DealRepository"
    private var dealListLiveData: MutableLiveData<List<Datum>> = MutableLiveData()
    private var dealLiveData: MutableLiveData<Datum> = MutableLiveData()

    fun getDeals(): LiveData<List<Datum>> {
        if (dealListLiveData.value == null) {
            fetchTargetDeals()
            fetchDeals()
        }
        return dealListLiveData
    }

    fun getDeal(id: String): LiveData<Datum> {
        fetchDeal(id)
        return dealLiveData
    }

    private fun fetchDeals() {
        dealDao.getAllDeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    dealListLiveData.value = it
                }
                .subscribe()
    }

    private fun fetchDeal(id: String) {
        dealDao.getDeal(id)
                .subscribeOn(Schedulers.computation())
                .doOnError {
                    throw IllegalStateException("Deal does not exist")
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    dealLiveData.value = it
                }
                .subscribe()
    }

    private fun fetchTargetDeals() {
        val map = mapOf("format" to "json")
        targetDealService.getDeals(map)
                .subscribeOn(Schedulers.io())
                .map(Deal::getData)
                .doOnNext {
                    dealDao.deleteAll()
                    dealDao.insertAll(it)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()

    }
}