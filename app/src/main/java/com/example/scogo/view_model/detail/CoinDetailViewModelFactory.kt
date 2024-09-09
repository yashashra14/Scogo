package com.example.scogo.view_model.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.scogo.network.api.CoinRepository

class CoinDetailViewModelFactory(
    private val coinId: String,
    private val coinRepository: CoinRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(CoinDetailViewModel::class.java)) {
            return CoinDetailViewModel(
                coinId = coinId,
                coinRepository = coinRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
