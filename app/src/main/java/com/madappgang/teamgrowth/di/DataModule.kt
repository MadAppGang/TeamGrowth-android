package com.madappgang.teamgrowth.di

import com.madappgang.IdentifoAuthentication
import com.madappgang.teamgrowth.BuildConfig
import com.madappgang.teamgrowth.extensions.createRetrofitInstance
import com.madappgang.teamgrowth.data.TeamGrowthRepository
import com.madappgang.teamgrowth.data.TeamGrowthRepositoryImpl
import com.madappgang.teamgrowth.data.TeamGrowthService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton


/*
 * Created by Eugene Prytula on 4/8/21.
 * Copyright (c) 2021 MadAppGang. All rights reserved.
 */

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideTeamGrowthService(): TeamGrowthService {
        return OkHttpClient.Builder()
            .addInterceptor(IdentifoAuthentication.getIdentifoInterceptor())
            .build()
            .createRetrofitInstance(BuildConfig.API_URL)
    }

    @Singleton
    @Provides
    fun providesBackgroundCoroutineDispatcher() = Dispatchers.IO

    @Singleton
    @Provides
    fun providesTeamGrowthRepositoryImpl(
        teamGrowthService: TeamGrowthService,
        backgroundCoroutineDispatcher: CoroutineDispatcher
    ) = TeamGrowthRepositoryImpl(teamGrowthService, backgroundCoroutineDispatcher)
}