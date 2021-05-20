package com.madappgang.teamgrowth.data

import com.madappgang.teamgrowth.domain.*
import retrofit2.http.*


/*
 * Created by Eugene Prytula on 4/8/21.
 * Copyright (c) 2021 MadAppGang. All rights reserved.
 */

interface TeamGrowthService {
    @GET("/api/users/me/goals")
    suspend fun getUserGoals(
        @Query("month") month: String,
        @Query("year") year: String
    ): List<UserGoal>

    @GET("/api/users/me")
    suspend fun getCurrentUser(
        @Query("month") month: String,
        @Query("year") year: String
    ): User

    @PUT("/api/goals/{id}/progress")
    suspend fun updateGoal(
        @Body progress: ProgressUpdate,
        @Path("id") id: String = progress.goalId
    ): Progress
}