package com.madappgang.teamgrowth

import android.app.Application
import com.madappgang.IdentifoAuthentication
import dagger.hilt.android.HiltAndroidApp

/*
 * Created by Eugene Prytula on 4/8/21.
 * Copyright (c) 2021 MadAppGang. All rights reserved.
 */

@HiltAndroidApp
class TeamGrowthApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        IdentifoAuthentication.initAuthenticator(
                context = this,
                baseUrl = BuildConfig.IDENTIFO_URL,
                secretKey = BuildConfig.SHARED_SECRET,
                applicationId = BuildConfig.APPLICATION_ID
        )
    }
}