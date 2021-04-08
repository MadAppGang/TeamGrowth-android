package com.madappgang.teamgrowth.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/*
 * Created by Eugene Prytula on 4/8/21.
 * Copyright (c) 2021 MadAppGang. All rights reserved.
 */

@Serializable
data class Goal(
    @SerialName("id") val id: String,
    @SerialName("description") val description: String,
    @SerialName("active") val active: Boolean,
    @SerialName("category") val category: String,
    @SerialName("imageUrl") val image: String,
    @SerialName("link") val link: String,
    @SerialName("month") val month: String,
    @SerialName("assignTo") val assignTo: List<String>,
    @SerialName("progress") val progress: Int,
    @SerialName("weeklyProgress") val weeklyProgress: Int,
    @SerialName("progressUpdatedAt") val progressUpdatedAt: Int,
    @SerialName("createdAt") val createdAt: String,
    @SerialName("updatedAt") val updatedAt: String
)