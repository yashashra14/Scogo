package com.example.scogo

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scogo.adapter.CoinsAdapter
import com.example.scogo.databinding.ActivityMainBinding
import com.example.scogo.view_model.home.CoinsViewModel
import com.example.scogo.view_model.home.CoinsViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var searchBar: SearchView
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: CoinsViewModel
    private lateinit var adapter: CoinsAdapter

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        searchBar = binding.searchView

        val factory = CoinsViewModelFactory(MainApplication.repositoryModule.coinRepository)
        viewModel = ViewModelProvider(this, factory)[CoinsViewModel::class.java]
        binding.viewModel = viewModel

        adapter = CoinsAdapter(viewModel)
        binding.cryptoRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.cryptoRecyclerView.adapter = adapter

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.fetchCoinsList()
        }

        binding.searchView.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val x = event.x.toInt()
                val y = event.y.toInt()

                if (isTouchOutsideSearchBar(x, y)) {
                    hideSearchBar()
                }
            }
            false
        }
        viewModel.coinsList.observe(this) { coins ->
            adapter.updateCoins(coins)
            binding.swipeRefreshLayout.isRefreshing = false
        }

        setupSearchView()
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.searchCoins(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    viewModel.searchCoins(it)
                }
                return true
            }
        })
    }
    private fun isTouchOutsideSearchBar(x: Int, y: Int): Boolean {
        val rect = android.graphics.Rect()
        searchBar.getHitRect(rect)
        return !rect.contains(x, y)
    }

    private fun showSearchBar() {
        searchBar.visibility = View.VISIBLE
        searchBar.requestFocus()
    }

    private fun hideSearchBar() {
        searchBar.visibility = View.GONE
        searchBar.clearFocus()
    }
}
