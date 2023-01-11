package com.example.composenavigation.screens.auth.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.composenavigation.R
import com.example.composenavigation.components.AuthHeader
import com.example.composenavigation.components.TextFieldWithError
import com.example.composenavigation.navigation.graphs.AuthScreen
import com.example.composenavigation.navigation.graphs.Graph
import com.example.composenavigation.navigation.navigateTo

@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel = hiltViewModel()) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(
                Color.LightGray
            )
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            AuthHeader(
                title = "Welcome Back,",
                painterResource = R.drawable.bg_login_head
            )

            Spacer(modifier = Modifier.height(40.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 20.dp, start = 40.dp,
                        end = 40.dp, bottom = 60.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                val passwordVisible = remember {
                    mutableStateOf(false)
                }

                TextFieldWithError(
                    label = "Email",
                    value = viewModel.emailState.text,
                    keyboardType = KeyboardType.Email,
                    isError = viewModel.emailState.error != null,
                    errorMessage = viewModel.emailState.error,
                    onValueChange = {
                        viewModel.onEmailChange(it)
                        viewModel.emailState.validate()
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))

                TextFieldWithError(
                    label = "Password",
                    value = viewModel.passwordState.text,
                    keyboardType = KeyboardType.Password,
                    isError = viewModel.passwordState.error != null,
                    errorMessage = viewModel.passwordState.error,
                    onValueChange = {
                        viewModel.onPasswordChange(it)
                        viewModel.passwordState.validate()
                    },
                    trailingIcon = {
                        val eyeIcon = if (passwordVisible.value) Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff

                        val contentDescription =
                            if (passwordVisible.value) "Hide password" else "Show password"
                        IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                            Icon(imageVector = eyeIcon, contentDescription = contentDescription)
                        }
                    },
                    visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                )

                Spacer(modifier = Modifier.height(20.dp))

                Box(modifier = Modifier.fillMaxWidth()) {
                    ClickableText(modifier = Modifier
                        .align(Alignment.CenterEnd),
                        style = TextStyle(color = Color.Black),
                        text = AnnotatedString(text = "Forgot Password?"),
                        onClick = {
                            navigateTo(navController, AuthScreen.Forgot.route, false)
                        })
                    Spacer(modifier = Modifier.height(20.dp))

                    Button(modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(contentColor = Color.Gray),
                        onClick = {
                            viewModel.signInWithEmailAndPassword {
                                navigateTo(
                                    navController,
                                    Graph.HOME,
                                    clearBackStack = true
                                )
                            }
                        }) {
                        Text(
                            text = "Log in",
                            color = Color.Black
                        )
                    }
                }
            }
        }
        val annotatedText = buildAnnotatedString {
            withStyle(style =
            SpanStyle(color = Color.Gray)) { append("Don't have account? ") }

            pushStringAnnotation(
                tag = "SignUp", // provide tag which will then be provided when you click the text
                annotation = "SignUp"
            )

            withStyle(style =
            SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                append("Sign Up")
            }

            withStyle(style = SpanStyle(color = Color.Gray)) { append(".") }
            pop()
        }
        ClickableText(modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(20.dp),
            text = annotatedText,
            onClick = { offset ->
                annotatedText.getStringAnnotations(
                    tag = "SignUp",// tag which you used in the buildAnnotatedString
                    start = offset, end = offset
                ).firstOrNull()?.let {
                    navigateTo(navController, AuthScreen.SignUp.route, true)
                }
            })
    }
}

@Preview
@Composable
fun LogInPreview() {
    LoginScreen(navController = rememberNavController(), hiltViewModel())
}