package com.target.dealbrowserpoc.dealbrowser.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.target.dealbrowserpoc.dealbrowser.DealViewModel
import com.target.dealbrowserpoc.dealbrowser.repository.DealRepository

class DealViewModelFactory(private val repository: DealRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DealViewModel(repository) as T
    }

    companion object {
        fun getDealViewModel(context: Context, storeOwner: ViewModelStoreOwner): DealViewModel =
                ViewModelProvider(storeOwner,
                        DealViewModelFactory(RepositoryFactory.getDealRepository(context))).get(DealViewModel::class.java)
    }
}