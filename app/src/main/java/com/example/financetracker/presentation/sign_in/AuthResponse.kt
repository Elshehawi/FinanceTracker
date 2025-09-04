package com.example.financetracker.presentation.sign_in

import android.os.Message

sealed interface AuthResponse {
    data object Success: AuthResponse
    data class Error(val message: String): AuthResponse
}