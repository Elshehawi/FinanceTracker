package com.example.financetracker.presentation.sign_in

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp

private fun onTermsClick() {
    TODO("Not yet implemented")
}

private fun onPrivacyClick() {
    TODO("Not yet implemented")
}

@Composable

fun RegisterScreen(
    onRegisterClick: (firstName: String, lastName: String, email: String, password: String) -> Unit,
    onLoginClick: () -> Unit
) {
    val scrollState = rememberScrollState()
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var dateOfBrith by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Scaffold { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF00C896))
                    .padding(top = 40.dp), // green
                contentAlignment = Alignment.TopCenter
            ) {
                Text(
                    text = "Create Account",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        color = Color(0xFF093030),
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(top = 40.dp)
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f)
                    .align(Alignment.BottomCenter)
                    .clip(RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
                    .background(Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                        .verticalScroll(scrollState),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)

                ) {
                    TextField(
                        value = firstName,
                        onValueChange = { firstName = it },
                        placeholder = { Text("Omar") },
                        label = { Text("First Name") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(23.dp))
                            .background(color = Color(0xFFCBF6D0))
                    )

                    TextField(
                        value = lastName,
                        onValueChange = { lastName = it },
                        placeholder = { Text("ElShehawi") },
                        label = { Text("Last Name") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(23.dp))
                            .background(color = Color(0xFFCBF6D0))
                    )

                    TextField(
                        value = firstName,
                        onValueChange = { firstName = it },
                        placeholder = { Text("+20 101 234 5678") },
                        label = { Text("Phone Number") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(23.dp))
                            .background(color = Color(0xFFCBF6D0))
                    )

                    TextField(
                        value = firstName,
                        onValueChange = { firstName = it },
                        placeholder = { Text("DD / MM / YYYY") },
                        label = { Text("Date of Birth") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(23.dp))
                            .background(color = Color(0xFFCBF6D0))
                    )

                    TextField(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = { Text("example@example.com") },

                        label = { Text("Email") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(23.dp))
                            .background(color = Color(0xFFCBF6D0))

                    )

                    // State variables for confirm password
                    var confirmPassword by remember { mutableStateOf("") }
                    var confirmPasswordVisible by remember { mutableStateOf(false) }

// Password field
                    TextField(
                        value = password,
                        onValueChange = { password = it },
                        placeholder = { Text("••••••••") },
                        label = { Text("Password") },
                        singleLine = true,
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector = if (passwordVisible) Icons.Default.Visibility
                                    else Icons.Default.VisibilityOff,
                                    contentDescription = null
                                )
                            }
                        }, modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(23.dp))
                            .background(color = Color(0xFFCBF6D0))

                    )

// Confirm Password field
                    TextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        placeholder = { Text("••••••••") },
                        label = { Text("Confirm Password") },
                        singleLine = true,
                        visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = {
                                confirmPasswordVisible = !confirmPasswordVisible
                            }) {
                                Icon(
                                    imageVector = if (confirmPasswordVisible) Icons.Default.Visibility
                                    else Icons.Default.VisibilityOff,
                                    contentDescription = null
                                )
                            }
                        }, modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(23.dp))
                            .background(color = Color(0xFFCBF6D0))
                    )


                    val termsText = buildAnnotatedString {
                        append("By continuing, you agree to ")
                        pushStringAnnotation(tag = "TERMS", annotation = "terms")
                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                        ) { append("Terms of Use") }
                        pop()

                        append(" and ")

                        pushStringAnnotation(tag = "PRIVACY", annotation = "privacy")
                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                        ) { append("Privacy Policy") }
                        pop()

                        append(".")
                    }
                    ClickableText(
                        text = termsText,
                        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onBackground),
                        onClick = { offset ->
                            termsText.getStringAnnotations("TERMS", offset, offset)
                                .firstOrNull()?.let { onTermsClick() }
                            termsText.getStringAnnotations("PRIVACY", offset, offset)
                                .firstOrNull()?.let { onPrivacyClick() }
                        }
                    )

                    Button(
                        onClick = { onRegisterClick(firstName, lastName, email, password) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(25.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00C896))
                    ) {
                        Text("Register", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                    val loginText = buildAnnotatedString {
                        append("Already have an account? ")
                        pushStringAnnotation(tag = "LOGIN", annotation = "login")
                        withStyle(
                            style = SpanStyle(
                                color = Color(0xFF1A73E8), // Google blue
                                fontWeight = FontWeight.Bold
                            )
                        ) { append("Log In") }
                        pop()
                    }

                    ClickableText(
                        text = loginText,
                        style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground),
                        onClick = { offset ->
                            loginText.getStringAnnotations("LOGIN", offset, offset)
                                .firstOrNull()?.let { onLoginClick() }
                        }
                    )
                }
            }
        }
    }

}

