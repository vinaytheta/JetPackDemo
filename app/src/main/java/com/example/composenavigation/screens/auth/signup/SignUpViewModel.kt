package com.example.composenavigation.screens.auth.signup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composenavigation.utils.EmailState
import com.example.composenavigation.utils.PasswordState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch


class SignUpViewModel : ViewModel() {

    private val auth: FirebaseAuth = Firebase.auth

    private val _emailState = EmailState()
    val emailState: EmailState = _emailState

    private val _passwordState = PasswordState()
    val passwordState: PasswordState = _passwordState

    private val _confirmPasswordState = PasswordState()
    val confirmPasswordState: PasswordState = _confirmPasswordState


    fun onEmailChange(email: String) {
        _emailState.text = email
    }

    fun onPasswordChange(password: String) {
        _passwordState.text = password
    }

    fun onConfirmPasswordChange(password: String) {
        _confirmPasswordState.text = password
    }

    fun createUserWithEmailAndPassword(home: () -> Unit) {
        if (validateInputs()) {
            viewModelScope.launch {
                try {
                    auth.createUserWithEmailAndPassword(emailState.text, passwordState.text)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                home()
                            } else {
                                try {
                                    Log.d(
                                        "TAGMain",
                                        "createUserWithEmailAndPassword: ${task.result}"
                                    )
                                } catch (ex: Exception) {
                                    Log.d(
                                        "TAGMain",
                                        "createUserWithEmailAndPassword: ${ex.localizedMessage}"
                                    )
                                }

                            }
                        }
                } catch (ex: Exception) {
                    Log.d("TAGMain", "createUserWithEmailAndPassword: ")
                }
            }
        }
    }

    private fun validateInputs(): Boolean {
        if (emailState.text.isEmpty()) {
            _emailState.validate()
            return false
        }
        if (passwordState.text.isEmpty()) {
            _passwordState.validate()
            return false
        }
        if (confirmPasswordState.text.isEmpty()) {
            _confirmPasswordState.validate()
            return false
        }
        if (passwordState.text != confirmPasswordState.text) {
            _confirmPasswordState.error = "Password mismatched!"
            return false
        }
        return true
    }
}