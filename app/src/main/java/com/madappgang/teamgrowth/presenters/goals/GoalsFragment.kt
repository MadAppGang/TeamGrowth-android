package com.madappgang.teamgrowth.presenters.goals

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import com.madappgang.BaseClickListener
import com.madappgang.teamgrowth.R
import com.madappgang.teamgrowth.databinding.FragmentGoalsBinding
import com.madappgang.teamgrowth.domain.Progress
import com.madappgang.teamgrowth.domain.User
import com.madappgang.teamgrowth.domain.UserGoal
import com.madappgang.teamgrowth.utils.extensions.addSystemBottomPadding
import com.madappgang.teamgrowth.utils.extensions.addSystemTopPadding
import com.madappgang.teamgrowth.utils.extensions.animateTo
import com.madappgang.teamgrowth.utils.extensions.safeNavigate
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt


/*
 * Created by Eugene Prytula on 4/8/21.
 * Copyright (c) 2021 MadAppGang. All rights reserved.
 */

@AndroidEntryPoint
class GoalsFragment : Fragment(R.layout.fragment_goals) {

    companion object {
        const val PROGRESS_REQUEST_KEY = "progress_request_key"
        const val PROGRESS_BUNDLE_KEY = "progress_bundle_key"
    }

    private val goalsViewBinding by viewBinding(FragmentGoalsBinding::bind)
    private val goalsViewModel by viewModels<GoalsViewModel>()

    private val clickBlock = BaseClickListener<UserGoal> { userGoal ->
        val action = GoalsFragmentDirections.actionGoalsFragmentToUpdateProgressFragment(userGoal)
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
                is GoalsViewStates.DataFetchedSuccessfully -> showGoals(
                    viewState.user,
                    viewState.goals
                )
                GoalsViewStates.Loading -> showLoading()
                GoalsViewStates.Error -> showError()
            }
        }

        goalsViewBinding.imageViewLogout.setOnClickListener {
            goalsViewModel.performLogout()
        }

        goalsViewBinding.includeErrorLabel.buttonTryAgain.setOnClickListener {
            goalsViewModel.loadCurrentProgressAndGoals()
        }

        setFragmentResultListener(PROGRESS_REQUEST_KEY) { requestKey, bundle ->
            val result = bundle.getParcelable<Progress>(PROGRESS_BUNDLE_KEY)
            Toast.makeText(requireContext(), result?.value.toString(), Toast.LENGTH_LONG).show()
        }
    }

    private fun showLoading() {
        goalsViewBinding.nestedScrollRoot.isVisible = false
        goalsViewBinding.progressGoals.isVisible = true
        goalsViewBinding.includeErrorLabel.constraintErrorLabelRoot.isVisible = false
    }

    private fun showError() {
        goalsViewBinding.nestedScrollRoot.isVisible = false
        goalsViewBinding.progressGoals.isVisible = false
        goalsViewBinding.includeErrorLabel.constraintErrorLabelRoot.isVisible = true
    }

    private fun showGoals(user: User, goals: List<UserGoal>) {
        goalsViewBinding.nestedScrollRoot.isVisible = true
        goalsViewBinding.progressGoals.isVisible = false
        goalsViewBinding.includeErrorLabel.constraintErrorLabelRoot.isVisible = false

        goalsViewBinding.includeProgress.tpProgress.animateTo(user.overallProgress)
        goalsViewBinding.includeProgress.textViewProgress.text = String.format(
            getString(R.string.progressPercent),
            user.overallProgress.roundToInt()
        )
        adapter.submitList(goals)
    }
}