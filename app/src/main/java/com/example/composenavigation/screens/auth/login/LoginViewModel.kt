package com.example.composenavigation.screens.auth.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composenavigation.utils.EmailState
import com.example.composenavigation.utils.PasswordState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _emailState = EmailState()
    val emailState: EmailState = _emailState

    private val _passwordState = PasswordState()
    val passwordState: PasswordState = _passwordState

    fun onEmailChange(email: String) {
        _emailState.text = email
    }

    fun onPasswordChange(password: String) {
        _passwordState.text = password
    }

    private val auth: FirebaseAuth = Firebase.auth

    fun signInWithEmailAndPassword(home: () -> Unit) {
        if (validateInputs()) {
            viewModelScope.launch {
                try {
                    auth.signInWithEmailAndPassword(emailState.text, passwordState.text)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d("TAGMain", "signInWithEmailAndPassword: ${task.result}")
                                home()
                            } else {
                                try {
                                    Log.d("TAGMain", "signInWithEmailAndPassword: ${task.result}")
                                } catch (ex: Exception) {
                                    Log.d(
                                        "TAGMain",
                                        "signInWithEmailAndPassword:${ex.localizedMessage} "
                                    )
                                }

                            }
                        }
                } catch (ex: Exception) {
                    Log.d("FirebaseException", "signInWithEmailAndPassword: ${ex.localizedMessage}")
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
        return true
    }
}