package com.madappgang.teamgrowth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.asLiveData
import com.madappgang.IdentifoAuthentication
import com.madappgang.identifolib.entities.AuthState
import com.madappgang.identifolibui.login.IdentifoSignInActivity
import com.madappgang.identifolibui.registration.IdentifoSingUpActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        IdentifoSingUpActivity.openActivity(this)

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