package com.madappgang.teamgrowth.presenters.goals

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.madappgang.BaseClickListener
import com.madappgang.teamgrowth.R
import com.madappgang.teamgrowth.databinding.FragmentGoalsBinding
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        goalsViewBinding.constraintLayoutRootGoals.addSystemTopPadding()
        goalsViewBinding.recycleViewGoals.addSystemBottomPadding()

        val adapter = GoalsAdapter(BaseClickListener { userGoal ->
            safeNavigate(R.id.action_goalsFragment_to_updateProgressFragment)
        })
        goalsViewBinding.recycleViewGoals.adapter = adapter

        goalsViewModel.goals.asLiveData().observe(viewLifecycleOwner) { goals ->
            adapter.submitList(goals)
        }

        goalsViewModel.error.asLiveData().observe(viewLifecycleOwner) { errorMessage ->
            goalsViewBinding.constraintLayoutRootGoals.showMessage(errorMessage)
        }

        goalsViewBinding.imageViewLogout.setOnClickListener {
            goalsViewModel.performLogout()
        }

        goalsViewModel.currentUser.asLiveData().observe(viewLifecycleOwner) { user ->
            user?.let {
                goalsViewBinding.includeProgress.tpProgress.animateTo(user.overallProgress)
                goalsViewBinding.includeProgress.textViewProgress.text = String.format(getString(R.string.progressPercent), user.overallProgress.roundToInt())
            }
        }
    }
}