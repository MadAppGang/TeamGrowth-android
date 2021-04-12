package com.madappgang.teamgrowth.presenters.updateProgress

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.madappgang.teamgrowth.databinding.FragmentUpdateProgressBinding
import com.madappgang.teamgrowth.utils.BottomDialogFragment


/*
 * Created by Eugene Prytula on 4/12/21.
 * Copyright (c) 2021 MadAppGang. All rights reserved.
 */

class UpdateProgressFragment : BottomDialogFragment() {

    private val updateProgressBinding by viewBinding(FragmentUpdateProgressBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layoutInflater = LayoutInflater.from(requireContext())
        val updateProgressBinding = FragmentUpdateProgressBinding.inflate(
            layoutInflater,
            container,
            false
        )
        return updateProgressBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}