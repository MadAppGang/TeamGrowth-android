package com.madappgang.teamgrowth.presenters.updateProgress

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.slider.Slider
import com.madappgang.teamgrowth.R
import com.madappgang.teamgrowth.databinding.FragmentUpdateProgressBinding
import com.madappgang.teamgrowth.utils.BottomDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt


/*
 * Created by Eugene Prytula on 4/12/21.
 * Copyright (c) 2021 MadAppGang. All rights reserved.
 */

@AndroidEntryPoint
class UpdateProgressFragment : BottomDialogFragment() {

    private val updateProgressBinding by viewBinding(FragmentUpdateProgressBinding::bind)
    private val updateProgressArgs by navArgs<UpdateProgressFragmentArgs>()
    private val updateProgressViewModel by viewModels<UpdateProgressViewModel>()

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
        val userGoal = updateProgressArgs.goal
        val goal = userGoal.goal
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

        updateProgressViewModel.updateGoalState.asLiveData().observe(viewLifecycleOwner) { state ->
            when (state) {
                is UpdateProgressViewStates.GoalHasBeenUpdated -> closeDialog()
                UpdateProgressViewStates.Error -> showError()
                UpdateProgressViewStates.Loading -> showLoading()
                UpdateProgressViewStates.IDLE -> showSlider()
            }
        }

        updateProgressBinding.buttonSaveProgress.setOnClickListener {
            val selectedProgress = updateProgressBinding.sliderProgress.value
            updateProgressViewModel.updateGoal(selectedProgress.roundToInt(), userGoal)
        }

        updateProgressBinding.includeErrorLabel.buttonTryAgain.setOnClickListener {
            tryAgain()
        }
    }

    private fun tryAgain() {
        updateProgressViewModel.resetState()
    }

    private fun showSlider() {
        updateProgressBinding.includeErrorLabel.constraintErrorLabelRoot.isVisible = false
        updateProgressBinding.textViewUpdateProgress.isVisible = true
        updateProgressBinding.textViewProgress.isVisible = true
        updateProgressBinding.sliderProgress.isVisible = true
        updateProgressBinding.buttonSaveProgress.isVisible = true
        updateProgressBinding.buttonCancel.isVisible = true
        updateProgressBinding.buttonSaveProgress.isEnabled = true
        updateProgressBinding.buttonCancel.isEnabled = true
    }

    private fun showError() {
        updateProgressBinding.includeErrorLabel.constraintErrorLabelRoot.isVisible = true
        updateProgressBinding.textViewUpdateProgress.isVisible = false
        updateProgressBinding.textViewProgress.isVisible = false
        updateProgressBinding.sliderProgress.isVisible = false
        updateProgressBinding.buttonSaveProgress.isVisible = false
        updateProgressBinding.buttonCancel.isVisible = false
    }

    private fun closeDialog() {
        findNavController().popBackStack()
    }

    private fun showLoading() {
        updateProgressBinding.buttonSaveProgress.isEnabled = false
        updateProgressBinding.buttonCancel.isEnabled = false
    }

    private fun transformFloatToPercent(currentSliderPosition: Float): String {
        val percent = (currentSliderPosition).roundToInt()
        return getString(R.string.progressPercent, percent)
    }
}