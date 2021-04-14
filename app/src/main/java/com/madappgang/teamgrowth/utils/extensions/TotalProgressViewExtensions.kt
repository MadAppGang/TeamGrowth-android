package com.madappgang.teamgrowth.utils.extensions

import android.animation.ObjectAnimator
import android.view.animation.AccelerateDecelerateInterpolator
import com.madappgang.teamgrowth.utils.customView.TotalProgressView


/*
 * Created by Eugene Prytula on 4/12/21.
 * Copyright (c) 2021 MadAppGang. All rights reserved.
 */

fun TotalProgressView.animateTo(progressTo: Float) {
    ObjectAnimator.ofFloat(this, "progress", this.progress, progressTo).apply {
        interpolator = AccelerateDecelerateInterpolator()
        duration = 2000
        start()
    }
}