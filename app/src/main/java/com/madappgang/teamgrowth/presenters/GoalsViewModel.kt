package com.madappgang.teamgrowth.presenters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madappgang.teamgrowth.data.TeamGrowthRepository
import com.madappgang.teamgrowth.domain.Goal
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

    private val _goals = MutableStateFlow<List<Goal>>(listOf())
    val goals: StateFlow<List<Goal>> = _goals.asStateFlow()

    init {
        viewModelScope.launch {
            _goals.emit(teamGrowthRepository.getGoals())
        }
    }
}