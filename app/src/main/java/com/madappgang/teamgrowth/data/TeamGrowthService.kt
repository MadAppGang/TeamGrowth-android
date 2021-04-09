package com.madappgang.teamgrowth.data

import com.madappgang.teamgrowth.domain.UserGoal
import retrofit2.http.GET


/*
 * Created by Eugene Prytula on 4/8/21.
 * Copyright (c) 2021 MadAppGang. All rights reserved.
 */

interface TeamGrowthService {
    @GET("/api/users/me/goals")
    suspend fun getUserGoals(): List<UserGoal>
}