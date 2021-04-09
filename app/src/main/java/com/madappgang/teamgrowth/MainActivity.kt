package com.madappgang.teamgrowth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.asLiveData
import com.madappgang.IdentifoAuthentication
import com.madappgang.identifolib.entities.AuthState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window?.run {
            WindowCompat.setDecorFitsSystemWindows(this, false)
        }

        IdentifoAuthentication.authenticationState.asLiveData().observe(this) { state ->
            when (state) {
                is AuthState.Authentificated -> {
                    val accessToken = state.accessToken
                    val user = state.identifoUser
                    // user is authenticated successfully
                }
                else -> {
                    // user is deauthenticated
                }
            }
        }
    }
}