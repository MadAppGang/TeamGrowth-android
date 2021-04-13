package com.madappgang.teamgrowth.presenters.updateProgress

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.slider.BasicLabelFormatter
import com.google.android.material.slider.Slider
import com.madappgang.teamgrowth.R
import com.madappgang.teamgrowth.databinding.FragmentUpdateProgressBinding
import com.madappgang.teamgrowth.utils.BottomDialogFragment
import kotlin.math.roundToInt


/*
 * Created by Eugene Prytula on 4/12/21.
 * Copyright (c) 2021 MadAppGang. All rights reserved.
 */

class UpdateProgressFragment : BottomDialogFragment() {

    private val updateProgressBinding by viewBinding(FragmentUpdateProgressBinding::bind)
    private val updateProgressArgs by navArgs<UpdateProgressFragmentArgs>()

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
        val goal = updateProgressArgs.goal
        val currentProgress = goal.progress.coerceAtMost(100F)

        updateProgressBinding.sliderProgress.value = currentProgress
        updateProgressBinding.textViewProgress.text = String.format(getString(R.string.progressPercent), currentProgress.roundToInt())

        updateProgressBinding.sliderProgress.setLabelFormatter { currentSliderPosition ->
            transformFloatToPercent(currentSliderPosition)
        }

        updateProgressBinding.sliderProgress.addOnChangeListener(Slider.OnChangeListener { slider, value, fromUser ->
            updateProgressBinding.textViewProgress.text = transformFloatToPercent(value)
        })

        updateProgressBinding.buttonCancel.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun transformFloatToPercent(currentSliderPosition: Float): String {
        val percent = (currentSliderPosition).roundToInt()
        return getString(R.string.progressPercent, percent)
    }
}