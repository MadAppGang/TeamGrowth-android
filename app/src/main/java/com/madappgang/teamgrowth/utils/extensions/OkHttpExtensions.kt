package com.madappgang.teamgrowth.utils.extensions

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit


/*
 * Created by Eugene Prytula on 4/8/21.
 * Copyright (c) 2021 MadAppGang. All rights reserved.
 */

inline fun <reified T> OkHttpClient.createRetrofitInstance(url: String): T {
    val contentType = "application/json".toMediaType()
    val jsonConverterFactory = Json { ignoreUnknownKeys = true }.asConverterFactory(contentType)
    return Retrofit.Builder()
        .baseUrl(url)
        .client(this)
        .addConverterFactory(jsonConverterFactory)
        .build()
        .create(T::class.java)
}