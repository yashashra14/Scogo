package com.example.scogo

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.scogo.databinding.ActivityDetailBinding
import com.example.scogo.view_model.detail.CoinDetailViewModel
import com.example.scogo.view_model.detail.CoinDetailViewModelFactory

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: CoinDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        binding.lifecycleOwner = this

        val factory = CoinDetailViewModelFactory(intent.getStringExtra("coinId")?: "",MainApplication.repositoryModule.coinRepository)
        viewModel = ViewModelProvider(this, factory)[CoinDetailViewModel::class.java]
        binding.viewModel = viewModel
    }
}