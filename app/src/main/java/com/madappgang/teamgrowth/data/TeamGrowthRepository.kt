package com.madappgang.teamgrowth.data

import com.madappgang.identifolib.entities.ErrorResponse
import com.madappgang.identifolib.extensions.Result
import com.madappgang.teamgrowth.domain.Goal
import com.madappgang.teamgrowth.domain.User
import com.madappgang.teamgrowth.domain.UserGoal
import com.madappgang.teamgrowth.utils.extensions.suspendApiCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject


/*
 * Created by Eugene Prytula on 4/8/21.
 * Copyright (c) 2021 MadAppGang. All rights reserved.
 */

interface TeamGrowthRepository {
    suspend fun getUserGoals(): Result<List<UserGoal>, ErrorResponse>
    suspend fun getCurrentUser(): Result<User, ErrorResponse>
    suspend fun updateGoal(goal: Goal): Result<Goal, ErrorResponse>
}

class TeamGrowthRepositoryImpl @Inject constructor(
    private val teamGrowthService: TeamGrowthService,
    private val coroutineDispatcher: CoroutineDispatcher
) : TeamGrowthRepository {

    override suspend fun getUserGoals(): Result<List<UserGoal>, ErrorResponse> =
        withContext(coroutineDispatcher) {
            suspendApiCall {
                teamGrowthService.getUserGoals()
            }
        }

    override suspend fun getCurrentUser(): Result<User, ErrorResponse> =
        withContext(coroutineDispatcher) {
            suspendApiCall {
                teamGrowthService.getCurrentUser()
            }
        }

    override suspend fun updateGoal(goal: Goal): Result<Goal, ErrorResponse> =
        withContext(coroutineDispatcher) {
            suspendApiCall {
                teamGrowthService.updateGoal(goal)
            }
        }
}