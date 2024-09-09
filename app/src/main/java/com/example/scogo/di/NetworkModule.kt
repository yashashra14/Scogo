package com.example.scogo.di

import com.example.scogo.MainApplication
import com.example.scogo.network.NetworkCall
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface NetworkModule {
    val gson: Gson
    val gsonConverterFactory: GsonConverterFactory
    val okHttpClient: OkHttpClient
    val retrofit: Retrofit
    val networkCall: NetworkCall
}

class NetworkModuleImpl : NetworkModule {

    override val gson: Gson by lazy {
        GsonBuilder().setLenient().create()
    }

    override val gsonConverterFactory: GsonConverterFactory by lazy {
        GsonConverterFactory.create(gson)
    }

    override val okHttpClient: OkHttpClient by lazy {
        val builder = OkHttpClient.Builder()
        builder.build()
    }

    override val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(MainApplication.BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    override val networkCall: NetworkCall by lazy {
        NetworkCall()
    }
}