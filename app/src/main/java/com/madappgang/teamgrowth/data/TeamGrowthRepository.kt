package com.madappgang.teamgrowth.data

import com.madappgang.teamgrowth.domain.UserGoal
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject


/*
 * Created by Eugene Prytula on 4/8/21.
 * Copyright (c) 2021 MadAppGang. All rights reserved.
 */

interface TeamGrowthRepository {
    suspend fun getUserGoals(): List<UserGoal>
}

class TeamGrowthRepositoryImpl @Inject constructor(
    private val teamGrowthService: TeamGrowthService,
    private val coroutineDispatcher: CoroutineDispatcher
) : TeamGrowthRepository {
    override suspend fun getUserGoals(): List<UserGoal> = withContext(coroutineDispatcher) {
        teamGrowthService.getUserGoals()
    }
}