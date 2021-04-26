package com.madappgang.teamgrowth.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/*
 * Created by Eugene Prytula on 4/8/21.
 * Copyright (c) 2021 MadAppGang. All rights reserved.
 */

@Parcelize
@Serializable
data class Goal(
    @SerialName("id") val id: String,
    @SerialName("title") val title: String,
    @SerialName("description") val description: String,
    @SerialName("active") val active: Boolean,
    @SerialName("private") val private: Boolean,
    @SerialName("category") val category: String,
    @SerialName("imageUrl") val image: String,
    @SerialName("link") val link: String,
    @SerialName("month") val month: String,
    @SerialName("year") val year: Int,
    @SerialName("assignTo") val assignTo: List<String>,
    @SerialName("progress") val progress: Float,
    @SerialName("weeklyProgress") val weeklyProgress: Float,
    @SerialName("progressUpdatedAt") val progressUpdatedAt: String,
    @SerialName("deleted") val deleted : Boolean,
    @SerialName("createdAt") val createdAt: String,
    @SerialName("updatedAt") val updatedAt: String
) : Parcelable