package com.madappgang.teamgrowth.presenters.goals

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.madappgang.BaseClickListener
import com.madappgang.teamgrowth.R
import com.madappgang.teamgrowth.databinding.FragmentGoalsBinding
import com.madappgang.teamgrowth.domain.Goal
import com.madappgang.teamgrowth.domain.User
import com.madappgang.teamgrowth.domain.UserGoal
import com.madappgang.teamgrowth.presenters.GoalsAdapter
import com.madappgang.teamgrowth.utils.extensions.*
import com.madappgang.teamgrowth.utils.extensions.showMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt


/*
 * Created by Eugene Prytula on 4/8/21.
 * Copyright (c) 2021 MadAppGang. All rights reserved.
 */

@AndroidEntryPoint
class GoalsFragment : Fragment(R.layout.fragment_goals) {

    private val goalsViewBinding by viewBinding(FragmentGoalsBinding::bind)
    private val goalsViewModel by viewModels<GoalsViewModel>()

    private val clickBlock = BaseClickListener<UserGoal> { userGoal ->
        val action = GoalsFragmentDirections.actionGoalsFragmentToUpdateProgressFragment(userGoal.goal)
        safeNavigate(action)
    }

    private val adapter = GoalsAdapter(clickBlock)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        goalsViewBinding.constraintLayoutRootGoals.addSystemTopPadding()
        goalsViewBinding.recycleViewGoals.addSystemBottomPadding()

        goalsViewBinding.recycleViewGoals.adapter = adapter

        goalsViewModel.goalsViewStates.asLiveData().observe(viewLifecycleOwner) { viewState ->
            when (viewState) {
                is GoalsViewStates.DataFetchedSuccessfully -> showGoals(viewState.user, viewState.goals)
                GoalsViewStates.Loading -> showLoading()
                GoalsViewStates.Error -> {}
            }
        }

        goalsViewBinding.imageViewLogout.setOnClickListener {
            goalsViewModel.performLogout()
        }
    }

    private fun showLoading() {
        goalsViewBinding.nestedScrollRoot.isVisible = false
        goalsViewBinding.progressGoals.isVisible = true
    }

    private fun showGoals(user: User, goals: List<UserGoal>) {
        goalsViewBinding.nestedScrollRoot.isVisible = true
        goalsViewBinding.progressGoals.isVisible = false

        goalsViewBinding.includeProgress.tpProgress.animateTo(user.overallProgress)
        goalsViewBinding.includeProgress.textViewProgress.text = String.format(
            getString(R.string.progressPercent),
            user.overallProgress.roundToInt()
        )
        adapter.submitList(goals)
    }
}