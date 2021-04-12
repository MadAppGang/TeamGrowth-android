package com.madappgang.teamgrowth.presenters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madappgang.IdentifoAuthentication
import com.madappgang.identifolib.extensions.onError
import com.madappgang.identifolib.extensions.onSuccess
import com.madappgang.teamgrowth.data.TeamGrowthRepository
import com.madappgang.teamgrowth.domain.UserGoal
import com.madappgang.teamgrowth.utils.extensions.suspendApiCall
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
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

    private val _goals = MutableStateFlow<List<UserGoal>>(listOf())
    val goals: StateFlow<List<UserGoal>> = _goals.asStateFlow()

    private val _error = MutableSharedFlow<String>()
    val error: SharedFlow<String> = _error.asSharedFlow()

    init {
        viewModelScope.launch {
            suspendApiCall {
                teamGrowthRepository.getUserGoals()
            }.onSuccess { userGoals ->
                _goals.emit(userGoals)
            }.onError { error ->
                _error.emit(error.message ?: "")
            }
        }
    }

    fun performLogout() {
        viewModelScope.launch {
            IdentifoAuthentication.logout()
            .onError { error ->
                _error.emit(error.message ?: "")
            }
        }
    }
}