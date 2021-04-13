package com.madappgang.teamgrowth.presenters.goals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madappgang.IdentifoAuthentication
import com.madappgang.identifolib.extensions.Result
import com.madappgang.identifolib.extensions.isSuccessful
import com.madappgang.teamgrowth.data.TeamGrowthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


/*
 * Created by Eugene Prytula on 4/8/21.
 * Copyright (c) 2021 MadAppGang. All rights reserved.
 */

@HiltViewModel
class GoalsViewModel @Inject constructor(
    private val teamGrowthRepository: TeamGrowthRepository
) : ViewModel() {

    private val _goalsViewStates = MutableStateFlow<GoalsViewStates>(GoalsViewStates.IDLE)
    val goalsViewStates: StateFlow<GoalsViewStates> = _goalsViewStates.asStateFlow()

    init {
        loadCurrentProgressAndGoals()
    }

    fun loadCurrentProgressAndGoals() {
        viewModelScope.launch {
            _goalsViewStates.emit(GoalsViewStates.Loading)
            val resultCurrentUser = teamGrowthRepository.getCurrentUser()
            val resultUserGoals = teamGrowthRepository.getUserGoals()

            if (resultCurrentUser.isSuccessful() and resultUserGoals.isSuccessful()) {
                val currentUser = (resultCurrentUser as Result.Success).value
                val goals = (resultUserGoals as Result.Success).value
                _goalsViewStates.emit(GoalsViewStates.DataFetchedSuccessfully(currentUser, goals))
            } else {
                _goalsViewStates.emit(GoalsViewStates.Error)
            }
        }
    }

    fun performLogout() {
        viewModelScope.launch {
            IdentifoAuthentication.logout()
        }
    }
}