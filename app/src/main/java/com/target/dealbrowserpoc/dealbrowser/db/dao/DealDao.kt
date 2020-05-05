package com.target.dealbrowserpoc.dealbrowser.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.target.dealbrowserpoc.dealbrowser.entity.Datum
import io.reactivex.Observable

@Dao
interface DealDao {

    @Query("Select * from datum")
    fun getAllDeals(): Observable<List<Datum>>

    @Query("Select * from datum where id = :id limit 1")
    fun getDeal(id: String): Observable<Datum>

    @Insert
    fun insertAll(deals: List<Datum>)

    @Query("Delete from datum")
    fun deleteAll()
}