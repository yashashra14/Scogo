package com.example.scogo.view_model.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scogo.network.Resource
import com.example.scogo.network.api.CoinRepository
import com.example.scogo.network.api.model.CoinDetailModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CoinDetailViewModel(
    coinId: String,
    private val coinRepository: CoinRepository
) : ViewModel() {

    private val _selectedCoinDetail = MutableLiveData<CoinDetailModel>()
    val selectedCoinDetail: LiveData<CoinDetailModel> get() = _selectedCoinDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    init {
        fetchCoinDetail(coinId)
    }

    private fun fetchCoinDetail(coinId: String) {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            coinRepository.getCoinDetail(coinId).collect { result ->
                when (result) {
                    is Resource.Error -> {
                        withContext(Dispatchers.Main) {
                            _isLoading.value = false
                            _errorMessage.value = "Something wrong happened, please try again later"
                        }
                    }
                    Resource.Loading -> {
                        _isLoading.postValue(true)
                    }
                    is Resource.Success -> {
                        withContext(Dispatchers.Main) {
                            _isLoading.value = false
                            _selectedCoinDetail.value = result.data
                            Log.e("CoinDetailViewModel", "Fetched Coin Detail: ${result.data}")
                        }

                    }
                }
            }
        }
    }
}
