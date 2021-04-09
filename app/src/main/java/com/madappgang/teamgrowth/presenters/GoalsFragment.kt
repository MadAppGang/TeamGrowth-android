package com.madappgang.teamgrowth.presenters

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import by.kirich1409.viewbindingdelegate.viewBinding
import com.madappgang.teamgrowth.R
import com.madappgang.teamgrowth.databinding.FragmentGoalsBinding
import com.madappgang.teamgrowth.extensions.addSystemTopBottomPadding
import dagger.hilt.android.AndroidEntryPoint


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
        goalsViewBinding.constraintLayoutRootGoals.addSystemTopBottomPadding()

        goalsViewModel.goals.asLiveData().observe(viewLifecycleOwner) {
            Log.i("GoalsFragment", "${it}")
        }
    }
}