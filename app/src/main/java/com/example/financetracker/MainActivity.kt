package com.example.financetracker

import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.financetracker.presentation.profile.ProfileScreen
import com.example.financetracker.presentation.sign_in.*
import com.example.financetracker.ui.theme.FinanceTrackerTheme
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    private val emailAuthUiClient by lazy { EmailAuthUiClient() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FinanceTrackerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    // Launcher for Google sign-in result
                    val launcher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.StartIntentSenderForResult(),
                        onResult = { result ->
                            if (result.resultCode == RESULT_OK) {
                                lifecycleScope.launch {
                                    val signInResult = googleAuthUiClient.signInWithIntent(result.data ?: return@launch)
                                    if (signInResult.data != null) {
                                        navController.navigate("profile") {
                                            popUpTo("login") { inclusive = true }
                                        }
                                    } else {
                                        Toast.makeText(
                                            this@MainActivity,
                                            "Google sign-in failed: ${signInResult.errorMessage}",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                            }
                        }
                    )


                    NavHost(
                        navController = navController,
                        startDestination = "login"
                    ) {
                        // Login Screen
                        composable("login") {
                            SignInScreen(
                                onLoginClick = { email, password ->
                                    lifecycleScope.launch {
                                        when (val response =
                                            emailAuthUiClient.logInWithEmail(email, password)) {
                                            is AuthResponse.Success -> {
                                                Toast.makeText(
                                                    this@MainActivity,
                                                    "Login Successful!",
                                                    Toast.LENGTH_LONG
                                                ).show()
                                                navController.navigate("profile") {
                                                    popUpTo("login") { inclusive = true }
                                                }
                                            }
                                            is AuthResponse.Error -> {
                                                Toast.makeText(
                                                    this@MainActivity,
                                                    response.message ?: "Login failed",
                                                    Toast.LENGTH_LONG
                                                ).show()
                                            }
                                        }
                                    }
                                },
                                onSignUpClick = {
                                    navController.navigate("register")
                                },
                                onGoogleSignInClick = {
                                    lifecycleScope.launch {
                                        val intentSender = googleAuthUiClient.signIn()
                                        if (intentSender != null) {
                                            launcher.launch(
                                                IntentSenderRequest.Builder(intentSender).build()
                                            )
                                        } else {
                                            Toast.makeText(this@MainActivity, "Google sign-in failed to start", Toast.LENGTH_LONG).show()
                                        }
                                    }

                                },
                            )
                        }

                        // Register Screen
                        composable("register") {
                            RegisterScreen(
                                onRegisterClick = { firstName, lastName, email, password ->
                                    lifecycleScope.launch {
                                        when (val response =
                                            emailAuthUiClient.createAccountWithEmail(
                                                email, password,
                                                firstName = firstName,
                                                lastName = lastName,
                                                dateOfBirth = "", // TODO: add proper date
                                                phoneNumber = ""  // TODO: add phone input
                                            )) {
                                            is AuthResponse.Success -> {
                                                Toast.makeText(
                                                    this@MainActivity,
                                                    "Account Created!",
                                                    Toast.LENGTH_LONG
                                                ).show()
                                                navController.navigate("profile") {
                                                    popUpTo("login") { inclusive = true }
                                                }
                                            }
                                            is AuthResponse.Error -> {
                                                Toast.makeText(
                                                    this@MainActivity,
                                                    response.message
                                                        ?: "Registration failed",
                                                    Toast.LENGTH_LONG
                                                ).show()
                                            }
                                        }
                                    }
                                },
                                onLoginClick = {
                                    navController.popBackStack("login", inclusive = false)
                                }
                            )
                        }

                        // Profile Screen
                        composable("profile") {
                            ProfileScreen(
                                userData = googleAuthUiClient.getSignedInUser(),
                                onSignOut = {
                                    lifecycleScope.launch {
                                        googleAuthUiClient.signOut()
                                        emailAuthUiClient.signOut()
                                        Toast.makeText(
                                            this@MainActivity,
                                            "Signed out",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        navController.navigate("login") {
                                            popUpTo("profile") { inclusive = true }
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
