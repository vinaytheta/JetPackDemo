@file:JvmName("TextFieldStateKt")

package com.example.composenavigation.utils

import android.util.Patterns
import java.util.regex.Pattern

class EmailState : TextFieldState(validator = ::isEmailValid, errorMessage = ::emailErrorMessage)

class PasswordState :
    TextFieldState(validator = ::isPasswordValid, errorMessage = ::passwordErrorMessage)

private fun isEmailValid(email: String): Boolean =
    Pattern.matches(Patterns.EMAIL_ADDRESS.toString(), email)

private fun emailErrorMessage(email: String) =
    if (email.isEmpty()) "Email field is required." else "Email $email is invalid."

private fun isPasswordValid(password: String): Boolean =
    password.length >= 8 && Pattern.matches(PASSWORD, password)

private fun passwordErrorMessage(password: String) =
    if (password.isEmpty()) {
        "Password field is required."
    } else if (password.length <= 8) {
        "Password should be at least 8 characters"
    } else {
        "Password must contain Uppercase, Lowercase, Digit & Special Character."
    }

private const val PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"
