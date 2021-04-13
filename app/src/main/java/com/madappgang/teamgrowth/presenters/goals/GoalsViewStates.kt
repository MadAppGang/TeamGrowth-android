package com.madappgang.teamgrowth.presenters.goals

import com.madappgang.teamgrowth.domain.User
import com.madappgang.teamgrowth.domain.UserGoal


/*
 * Created by Eugene Prytula on 4/13/21.
 * Copyright (c) 2021 MadAppGang. All rights reserved.
 */

sealed class GoalsViewStates {
    data class DataFetchedSuccessfully(
        val user: User,
        val goals: List<UserGoal>
    ) : GoalsViewStates()

    object Error : GoalsViewStates()
    object Loading : GoalsViewStates()
    object IDLE : GoalsViewStates()
}