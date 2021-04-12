package com.madappgang.teamgrowth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.asLiveData
import com.madappgang.IdentifoAuthentication
import com.madappgang.identifolib.entities.AuthState
import com.madappgang.identifolibui.login.IdentifoSignInActivity
import com.madappgang.identifolibui.login.options.LoginOptions
import com.madappgang.identifolibui.login.options.LoginProviders
import com.madappgang.identifolibui.login.options.LoginProviders.*
import com.madappgang.identifolibui.login.options.Style
import com.madappgang.identifolibui.login.options.UseConditions
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
                    openSignInFlow()
                }
            }
        }
    }

    private fun openSignInFlow() {
        val style = Style(
            companyLogo = R.drawable.ic_teamgrowth,
            companyName = getString(R.string.app_name),
            greetingsText = "Greetings text"
        )

        val userConditions = UseConditions(
            userAgreement = "https://userAgreement.com/",
            privacyPolicy = "https://privacyPolicy.com/"
        )

        val providers = listOf(EMAIL, PHONE)

        val loginOptions = LoginOptions(
            commonStyle = style,
            providers = providers,
            useConditions = userConditions
        )
        IdentifoSignInActivity.openActivity(this, loginOptions)
    }
}