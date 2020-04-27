package com.target.dealbrowserpoc.dealbrowser.web

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface TargetDealService {
    @GET("deals")
    fun getDeals(@QueryMap map: Map<String, String>): Call<Deal>

}