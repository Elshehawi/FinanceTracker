package com.example.financetracker.presentation.sign_in

import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class EmailAuthUiClient {
    private val auth = Firebase.auth

    suspend fun createAccountWithEmail(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        dateOfBirth: String,
        phoneNumber: String
    ): AuthResponse {
        return try {
            // 1. Create account with Firebase Auth
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user ?: throw Exception("User not found")
            val uid = user.uid

            // 2. Save extra fields in Firestore
            val userMap = mapOf(
                "firstName" to firstName,
                "lastName" to lastName,
                "dateOfBirth" to dateOfBirth,
                "phoneNumber" to phoneNumber,
                "email" to email
            )

            Firebase.firestore.collection("users")
                .document(uid)
                .set(userMap)
                .await()

            // 3. Update Firebase Auth profile (optional)
            val profileUpdates = userProfileChangeRequest {
                displayName = "$firstName $lastName"
            }
            user.updateProfile(profileUpdates).await()

            AuthResponse.Success
        } catch (e: Exception) {
            AuthResponse.Error(e.message ?: "Unknown error")
        }
    }


    suspend fun logInWithEmail(email: String, password: String): AuthResponse {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            AuthResponse.Success
        } catch (e: Exception) {
            AuthResponse.Error(e.message ?: "Unknown error")
        }
    }

    fun signOut() = auth.signOut()
}
