package com.madappgang.teamgrowth.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/*
 * Created by Eugene Prytula on 4/14/21.
 * Copyright (c) 2021 MadAppGang. All rights reserved.
 */

@Serializable
data class ProgressUpdate(
    @SerialName("userId") val userId: String,
    @SerialName("goalId") val goalId: String,
    @SerialName("value") val value: Int
)