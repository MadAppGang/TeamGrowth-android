package com.madappgang.teamgrowth.data

import com.madappgang.teamgrowth.domain.Goal
import retrofit2.http.GET
import retrofit2.http.POST


/*
 * Created by Eugene Prytula on 4/8/21.
 * Copyright (c) 2021 MadAppGang. All rights reserved.
 */

interface TeamGrowthService {
    @GET("/api/goals")
    suspend fun getGoals(): List<Goal>
}