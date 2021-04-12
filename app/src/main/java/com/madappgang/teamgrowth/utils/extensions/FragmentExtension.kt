package com.madappgang.teamgrowth.utils.extensions

import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController


/*
 * Created by Eugene Prytula on 4/12/21.
 * Copyright (c) 2021 MadAppGang. All rights reserved.
 */

fun Fragment.safeNavigate(navDirections: NavDirections) {
    val navController = findNavController()
    try {
        navController.navigate(navDirections)
    } catch (exception: Exception) {
        // Ignore exception, tricky way to avoid crash if current destination doesn't have that direction
    }
}

fun Fragment.safeNavigate(directionId: Int) {
    val navController = findNavController()
    try {
        navController.navigate(directionId)
    } catch (exception: Exception) {
        // Ignore exception, tricky way to avoid crash if current destination doesn't have that direction
    }
}