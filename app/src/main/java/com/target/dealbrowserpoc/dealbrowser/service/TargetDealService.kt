package com.target.dealbrowserpoc.dealbrowser.service

import com.target.dealbrowserpoc.dealbrowser.entity.Deal
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface TargetDealService {
    @GET("deals")
    fun getDeals(@QueryMap map: Map<String, String>): Observable<Deal>

}