package com.example.scogo.view_model.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scogo.network.Resource
import com.example.scogo.network.api.CoinRepository
import com.example.scogo.network.api.model.CoinsModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CoinsViewModel(
    private val coinRepository: CoinRepository
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    private val _coinsList = MutableLiveData<List<CoinsModel>>()
    val coinsList: LiveData<List<CoinsModel>> get() = _coinsList

    private var initialCoinsList: List<CoinsModel> = emptyList()

    private var debounceJob: Job? = null


    init {
        fetchCoinsList()
    }

    fun searchCoins(search: String) {
        debounceJob?.cancel()

        debounceJob = viewModelScope.launch {
            delay(500)
            val trimmedSearch = search.trim()

            if (trimmedSearch.isNotEmpty()) {
                val filteredList = initialCoinsList.filter {
                    it.name.contains(trimmedSearch, ignoreCase = true) ||
                            it.symbol.contains(trimmedSearch, ignoreCase = true)
                }
                _coinsList.value = filteredList
            } else {
                resetCoinsList()
            }
        }
    }

    private fun resetCoinsList() {
        _coinsList.value = initialCoinsList
    }

    fun fetchCoinsList() {
        viewModelScope.launch(Dispatchers.IO) {
            coinRepository.getCoins().collect{
                when(it){
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
                        viewModelScope.launch {
                            _isLoading.value = false
                            _coinsList.value = it.data
                            initialCoinsList = it.data
                        }
                    }
                }
            }
        }
    }
}
