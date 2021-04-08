package com.madappgang.teamgrowth.presenters

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.madappgang.teamgrowth.R
import com.madappgang.teamgrowth.databinding.FragmentGoalsBinding


/*
 * Created by Eugene Prytula on 4/8/21.
 * Copyright (c) 2021 MadAppGang. All rights reserved.
 */

class GoalsFragment : Fragment(R.layout.fragment_goals) {

    private val goalsViewBinding by viewBinding(FragmentGoalsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}