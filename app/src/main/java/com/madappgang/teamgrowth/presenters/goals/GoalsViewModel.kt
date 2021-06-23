package com.madappgang.teamgrowth.presenters.goals

import android.text.format.DateFormat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madappgang.IdentifoAuthentication
import com.madappgang.identifolib.extensions.Result
import com.madappgang.identifolib.extensions.isSuccessful
import com.madappgang.identifolib.extensions.onError
import com.madappgang.identifolib.extensions.onSuccess
import com.madappgang.teamgrowth.data.TeamGrowthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.time.Month
import java.time.format.TextStyle
import java.util.*
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

    private val _logOut = MutableSharedFlow<Boolean>()
    val logOut : SharedFlow<Boolean> = _logOut.asSharedFlow()

    init {
        _goalsViewStates.value = GoalsViewStates.Loading
    }

    fun loadCurrentProgressAndGoals(month : String, year : String) {
        viewModelScope.launch {

            val resultCurrentUser = teamGrowthRepository.getCurrentUser(month, year)
            val resultUserGoals = teamGrowthRepository.getUserGoals(month, year)

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
            IdentifoAuthentication.logout().onSuccess {
                _logOut.emit(true)
            }.onError {
                _logOut.emit(false)
            }
        }
    }
}