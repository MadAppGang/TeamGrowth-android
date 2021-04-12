package com.madappgang.teamgrowth.data

import com.madappgang.teamgrowth.domain.User
import com.madappgang.teamgrowth.domain.UserGoal
import retrofit2.http.GET
import retrofit2.http.Headers


/*
 * Created by Eugene Prytula on 4/8/21.
 * Copyright (c) 2021 MadAppGang. All rights reserved.
 */

interface TeamGrowthService {
    @GET("/api/users/me/goals")
    suspend fun getUserGoals(): List<UserGoal>

    @GET("/api/users/me")
    suspend fun getCurrentUser() : User
}