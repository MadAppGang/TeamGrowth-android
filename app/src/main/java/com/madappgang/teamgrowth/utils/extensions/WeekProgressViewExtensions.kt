package com.madappgang.teamgrowth.utils.extensions

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.animation.AccelerateDecelerateInterpolator
import com.madappgang.teamgrowth.utils.customView.WeekProgressView


/*
 * Created by Eugene Prytula on 4/12/21.
 * Copyright (c) 2021 MadAppGang. All rights reserved.
 */


fun WeekProgressView.animateProgressBar(
    totalProgress: Float,
    weekProgress: Float
) {
    val totalProgressAnimator = ObjectAnimator.ofFloat(
        this,
        "totalProgress",
        totalProgress
    ).apply {
        duration = 500
        interpolator = AccelerateDecelerateInterpolator()
    }

    val weekProgressAnimator = ObjectAnimator.ofFloat(
        this,
        "weekProgress",
        weekProgress
    ).apply {
        duration = 500
        interpolator = AccelerateDecelerateInterpolator()
    }

    AnimatorSet().apply {
        playSequentially(totalProgressAnimator, weekProgressAnimator)
        start()
    }
}