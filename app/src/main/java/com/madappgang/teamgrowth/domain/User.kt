package com.madappgang.teamgrowth.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/*
 * Created by Eugene Prytula on 4/12/21.
 * Copyright (c) 2021 MadAppGang. All rights reserved.
 */


@Serializable
data class User(
    @SerialName("id") val id: String,
    @SerialName("username") val username: String,
    @SerialName("email") val email: String,
    @SerialName("firstName") val firstName: String,
    @SerialName("lastName") val lastName: String,
    @SerialName("position") val position: String,
    @SerialName("photoURL") val photoUrl: String,
    @SerialName("role") val role: String,
    @SerialName("timezone") val timezone: String,
    @SerialName("overallProgress") val overallProgress: Float,
    @SerialName("weeklyProgress") val weeklyProgress: Float,
    @SerialName("createdAt") val createdAt: String,
    @SerialName("updatedAt") val updatedAt: String
)