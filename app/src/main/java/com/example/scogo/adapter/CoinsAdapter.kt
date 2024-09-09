package com.example.scogo.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.scogo.DetailActivity
import com.example.scogo.R
import com.example.scogo.network.api.model.CoinsModel
import com.example.scogo.view_model.home.CoinsViewModel

class CoinsAdapter(coinsViewModel: CoinsViewModel) :
    RecyclerView.Adapter<CoinsAdapter.CoinViewHolder>() {
        private var coinList: List<CoinsModel> = coinsViewModel.coinsList.value?: emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_coin, parent, false)
        return CoinViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        val coin = coinList[position]
        holder.bind(coin)
    }

    override fun getItemCount(): Int {
        Log.e("CoinsAdapter", "getItemCount: ${coinList.size}")
        return coinList.size
    }

    inner class CoinViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val coinName: TextView = itemView.findViewById(R.id.coinName)
        private val coinSymbol: TextView = itemView.findViewById(R.id.coinSymbol)

        fun bind(coin: CoinsModel) {
            coinName.text = coin.name
            coinSymbol.text = coin.symbol

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra("coinId", coin.id)
                startActivity(itemView.context, intent, null)
            }
        }
    }

    fun updateCoins(newCoinList: List<CoinsModel>) {
        coinList = newCoinList
        notifyDataSetChanged()
    }
}
