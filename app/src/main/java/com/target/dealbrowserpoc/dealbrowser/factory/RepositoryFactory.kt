package com.target.dealbrowserpoc.dealbrowser.factory

import android.content.Context
import com.target.dealbrowserpoc.dealbrowser.db.DealDatabase
import com.target.dealbrowserpoc.dealbrowser.repository.DealRepository

class RepositoryFactory {
    companion object {
        @Volatile private var instance: DealRepository? = null

        fun getDealRepository(context: Context) = instance ?: buildDealRepository(context)

        private fun buildDealRepository(context: Context): DealRepository =
                DealRepository(DealDatabase.getInstance(context).dealDao(), ServiceFactory.getTargetDealService())
    }
}