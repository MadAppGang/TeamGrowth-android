package com.madappgang.teamgrowth.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/*
 * Created by Eugene Prytula on 4/14/21.
 * Copyright (c) 2021 MadAppGang. All rights reserved.
 */

@Parcelize
@Serializable
data class Progress(
    @SerialName("id") val id: String,
    @SerialName("userId") val userId: String,
    @SerialName("goalId") val goalId: String,
    @SerialName("active") val active: Boolean,
    @SerialName("value") val value: Int,
    @SerialName("weeklyValue") val weeklyValue: Int,
    @SerialName("createdAt") val createdAt: String,
    @SerialName("updatedAt") val updatedAt: String
) : Parcelable