package com.example.scogo.view_model.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.scogo.network.api.CoinRepository

class CoinsViewModelFactory(
    private val coinRepository: CoinRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(CoinsViewModel::class.java)) {
            return CoinsViewModel(
                coinRepository = coinRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
