package com.madappgang.teamgrowth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_TeamGrowth)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window?.run {
            WindowCompat.setDecorFitsSystemWindows(this, false)
        }
    }
}