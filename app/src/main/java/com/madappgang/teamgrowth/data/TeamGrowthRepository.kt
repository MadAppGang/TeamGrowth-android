package com.madappgang.teamgrowth.data

import com.madappgang.identifolib.entities.ErrorResponse
import com.madappgang.identifolib.extensions.Result
import com.madappgang.teamgrowth.domain.Progress
import com.madappgang.teamgrowth.domain.ProgressUpdate
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
    suspend fun getUserGoals(
        month: String,
        year: String
    ): Result<List<UserGoal>, ErrorResponse>

    suspend fun getCurrentUser(
        month: String,
        year: String
    ): Result<User, ErrorResponse>

    suspend fun updateProgress(
        progress: ProgressUpdate
    ): Result<Progress, ErrorResponse>
}

class TeamGrowthRepositoryImpl @Inject constructor(
    private val teamGrowthService: TeamGrowthService,
    private val coroutineDispatcher: CoroutineDispatcher
) : TeamGrowthRepository {

    override suspend fun getUserGoals(
        month: String,
        year: String
    ): Result<List<UserGoal>, ErrorResponse> =
        withContext(coroutineDispatcher) {
            suspendApiCall {
                teamGrowthService.getUserGoals(month, year)
            }
        }

    override suspend fun getCurrentUser(
        month: String,
        year: String
    ): Result<User, ErrorResponse> =
        withContext(coroutineDispatcher) {
            suspendApiCall {
                teamGrowthService.getCurrentUser(month, year)
            }
        }

    override suspend fun updateProgress(progress: ProgressUpdate): Result<Progress, ErrorResponse> =
        withContext(coroutineDispatcher) {
            suspendApiCall {
                teamGrowthService.updateGoal(progress)
            }
        }
}