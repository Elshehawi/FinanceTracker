package com.example.financetracker.presentation.sign_in

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.example.financetracker.R
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await

class GoogleAuthUiClient(
    private val context: Context,
    private val oneTapClient: SignInClient
) {
    private val auth = Firebase.auth

    /**
     * Launch the Google One Tap sign-in flow
     */
    suspend fun signIn(): IntentSender? {
        return try {
            val result = oneTapClient.beginSignIn(buildSignInRequest()).await()
            result?.pendingIntent?.intentSender
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            null
        }
    }

    /**
     * Handle result after user selects an account
     */
    suspend fun signInWithIntent(intent: Intent): SignInResult {
        return try {
            val credential = oneTapClient.getSignInCredentialFromIntent(intent)
            val googleIdToken = credential.googleIdToken
            if (googleIdToken == null) {
                return SignInResult(null, "Google ID Token is null")
            }

            val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
            val user = auth.signInWithCredential(googleCredentials).await().user

            SignInResult(
                data = user?.run {
                    UserData(
                        userId = uid,
                        username = displayName,
                        profilePicture = photoUrl?.toString()
                    )
                },
                errorMessage = null
            )
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            SignInResult(
                data = null,
                errorMessage = e.message ?: "Unknown error during Google sign-in"
            )
        }
    }


    /**
     * Sign out of both One Tap and Firebase
     */
    suspend fun signOut() {
        try {
            oneTapClient.signOut().await()
            auth.signOut()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
        }
    }

    /**
     * Return currently signed-in user, if any
     */
    fun getSignedInUser(): UserData? = auth.currentUser?.run {
        UserData(
            userId = uid,
            username = displayName,
            profilePicture = photoUrl?.toString()
        )
    }

    /**
     * Request configuration for One Tap
     */
    private fun buildSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.Builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false) // Show all accounts
                    .setServerClientId(context.getString(R.string.web_client_id)) // 👈 Use Web Client ID from Firebase console
                    .build()
            )
            .setAutoSelectEnabled(false) // Avoid auto-login issues
            .build()
    }
}