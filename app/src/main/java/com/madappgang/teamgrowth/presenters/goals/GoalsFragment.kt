package com.madappgang.teamgrowth.presenters.goals

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import com.madappgang.BaseClickListener
import com.madappgang.IdentifoAuthentication
import com.madappgang.identifolib.entities.AuthState
import com.madappgang.identifolibui.login.WelcomeLoginFragment
import com.madappgang.identifolibui.login.options.LoginOptions
import com.madappgang.identifolibui.login.options.LoginProviders
import com.madappgang.identifolibui.login.options.Style
import com.madappgang.identifolibui.login.options.UseConditions
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

        goalsViewModel.loadCurrentProgressAndGoals()
        goalsViewModel.goalsViewStates.asLiveData().observe(viewLifecycleOwner) { viewState ->
            when (viewState) {
                is GoalsViewStates.DataFetchedSuccessfully -> showGoals(viewState.user, viewState.goals)
                GoalsViewStates.Loading -> showLoading()
                GoalsViewStates.Error -> showError()
            }
        }

        goalsViewModel.logOut.asLiveData().observe(viewLifecycleOwner) { isLogOutSuccess ->
            if (isLogOutSuccess) {
                checkLoggingState()
            } else {
                Snackbar.make(
                    goalsViewBinding.constraintLayoutRootGoals,
                    getString(R.string.somethingWentWrong),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        goalsViewBinding.imageViewLogout.setOnClickListener {
            goalsViewModel.performLogout()
            checkLoggingState()
        }

        goalsViewBinding.includeErrorLabel.buttonTryAgain.setOnClickListener {
            goalsViewModel.loadCurrentProgressAndGoals()
        }

        setFragmentResultListener(PROGRESS_REQUEST_KEY) { requestKey, bundle ->
            goalsViewModel.loadCurrentProgressAndGoals()
        }
    }

    override fun onStart() {
        super.onStart()
        checkLoggingState()
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

    private fun checkLoggingState() {
        IdentifoAuthentication.fetchAuthState { state: AuthState ->
            when (state) {
                is AuthState.Authentificated -> {
                    val accessToken = state.accessToken
                    val user = state.identifoUser

                }
                else -> {
                    openSignInFlow()
                }
            }
        }
    }

    private fun openSignInFlow() {
        val style = Style(
            companyLogo = R.drawable.ic_teamgrowth,
            companyName = getString(R.string.app_name),
            greetingsText = getString(R.string.congrats)
        )

        val userConditions = UseConditions(
            userAgreement = "https://madappgang.com/",
            privacyPolicy = "https://madappgang.com/"
        )

        val providers = listOf(LoginProviders.EMAIL, LoginProviders.PHONE)

        val loginOptions = LoginOptions(
            commonStyle = style,
            providers = providers,
            useConditions = userConditions
        )

        findNavController().navigate(
            R.id.action_goalsFragment_to_navigation_graph_login,
            WelcomeLoginFragment.putArguments(loginOptions)
        )
    }
}