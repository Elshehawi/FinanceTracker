package com.example.financetracker.presentation.sign_in

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lint.kotlin.metadata.Visibility
import com.example.financetracker.R

@Composable
fun SignInScreen(
    onLoginClick: (String, String) -> Unit,
    onSignUpClick: () -> Unit,
    onGoogleSignInClick: () -> Unit,
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Scaffold { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF00C896))
                    .padding(top = 40.dp), // green
                contentAlignment = Alignment.TopCenter
            ) {
                Text(
                    text = "Welcome",
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
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    TextField(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = { Text("example@example.com") },
                        label = { Text("Username Or Email") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(23.dp))
                            .background(color = Color(0xFFCBF6D0))
                    )

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
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(23.dp))
                            .background(color = Color(0xFFCBF6D0))
                    )

                    Button(
                        onClick = { onLoginClick(email, password) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(25.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00C896))
                    ) {
                        Text("Log In", color = Color.White, fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = onSignUpClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(25.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE6F8F3))
                    ) {
                        Text("Sign Up", color = Color.Black, fontWeight = FontWeight.Bold)
                    }

                    // Social sign-in
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = onGoogleSignInClick) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_google),
                                contentDescription = "Google",
                                tint = Color.Unspecified,
                                modifier = Modifier.size(40.dp)
                            )
                        }
                    }

                    // Bottom Sign Up link
                    TextButton(onClick = onSignUpClick) {
                        Text(
                            text = "Don’t have an account? Sign Up",
                            style = MaterialTheme.typography.bodySmall.copy(color = Color.Blue)
                        )
                    }
                }
            }
        }
    }
}
