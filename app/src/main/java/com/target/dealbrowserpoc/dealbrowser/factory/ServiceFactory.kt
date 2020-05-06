package com.target.dealbrowserpoc.dealbrowser.factory

import com.target.dealbrowserpoc.dealbrowser.app.Constants
import com.target.dealbrowserpoc.dealbrowser.service.TargetDealService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ServiceFactory {
    companion object {
        @Volatile private var instance: TargetDealService? = null

        fun getTargetDealService() = instance ?: buildService().also { instance = it }

        private fun buildService(): TargetDealService =
            Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create(TargetDealService::class.java)

    }
}