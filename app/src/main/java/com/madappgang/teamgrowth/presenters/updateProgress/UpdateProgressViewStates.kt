package com.madappgang.teamgrowth.presenters.updateProgress

import com.madappgang.teamgrowth.domain.Progress


/*
 * Created by Eugene Prytula on 4/13/21.
 * Copyright (c) 2021 MadAppGang. All rights reserved.
 */

sealed class UpdateProgressViewStates {
    data class GoalHasBeenUpdated(val progress: Progress) : UpdateProgressViewStates()
    object Loading : UpdateProgressViewStates()
    object IDLE : UpdateProgressViewStates()
    object Error : UpdateProgressViewStates()
}