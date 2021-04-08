package com.madappgang.teamgrowth.di

import com.madappgang.teamgrowth.data.TeamGrowthRepository
import com.madappgang.teamgrowth.data.TeamGrowthRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


/*
 * Created by Eugene Prytula on 4/8/21.
 * Copyright (c) 2021 MadAppGang. All rights reserved.
 */

@Module
@InstallIn(SingletonComponent::class)
abstract class DataBindsModule {
    @Binds
    abstract fun providesTeamGrowthRepository(
        teamGrowthRepositoryImpl: TeamGrowthRepositoryImpl
    ): TeamGrowthRepository
}