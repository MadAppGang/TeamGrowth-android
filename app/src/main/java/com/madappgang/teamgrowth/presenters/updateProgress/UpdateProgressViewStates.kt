package com.madappgang.teamgrowth.presenters.updateProgress

import com.madappgang.teamgrowth.domain.Goal


/*
 * Created by Eugene Prytula on 4/13/21.
 * Copyright (c) 2021 MadAppGang. All rights reserved.
 */

sealed class UpdateProgressViewStates {
    data class GoalHasBeenUpdated(val goal: Goal) : UpdateProgressViewStates()
    object Loading : UpdateProgressViewStates()
    object IDLE : UpdateProgressViewStates()
    object Error : UpdateProgressViewStates()
}