package com.example.composenavigation.screens.auth.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
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
import com.example.composenavigation.navigation.BottomBarScreen
import com.example.composenavigation.navigation.graphs.AuthScreen
import com.example.composenavigation.navigation.graphs.Graph
import com.example.composenavigation.navigation.navigateTo
import com.example.composenavigation.ui.theme.ComposeNavigationTheme

@Composable
fun LoginScreen(
    navController: NavController = rememberNavController(),
    viewModel: LoginViewModel = hiltViewModel()
) {
    LogInUi(navController = navController,
        email = viewModel.emailState.text,
        emailError = viewModel.emailState.error,
        password = viewModel.passwordState.text,
        passwordError = viewModel.passwordState.error,
        onEmailChanged = {
            viewModel.onEmailChange(it)
            viewModel.emailState.validate()
        },
        onPasswordChanged = {
            viewModel.onPasswordChange(it)
            viewModel.passwordState.validate()
        },
        onLogInClick = {
            viewModel.signInWithEmailAndPassword {
                /*navigateTo(
                    navController, Graph.HOME, clearBackStack = true
                )*/
                navController.navigate(Graph.HOME) {
                    popUpTo(Graph.AUTHENTICATION) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            }
        })
}

@Composable
fun LogInUi(
    navController: NavController = rememberNavController(),
    email: String = "email",
    emailError: String? = null,
    onEmailChanged: (String) -> Unit = {},
    password: String = "Password",
    passwordError: String? = null,
    onPasswordChanged: (String) -> Unit = {},
    onLogInClick: () -> Unit = {},
) {
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
                title = "Log in", painterResource = R.drawable.bg_login_head
            )

            Spacer(modifier = Modifier.height(40.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = 20.dp, start = 40.dp, end = 40.dp, bottom = 60.dp
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                val passwordVisible = remember {
                    mutableStateOf(false)
                }

                TextFieldWithError(
                    label = "Email",
                    value = email,
                    keyboardType = KeyboardType.Email,
                    isError = emailError != null,
                    errorMessage = emailError,
                    onValueChange = onEmailChanged
                )

                Spacer(modifier = Modifier.height(20.dp))

                TextFieldWithError(
                    label = "Password",
                    value = password,
                    keyboardType = KeyboardType.Password,
                    isError = passwordError != null,
                    errorMessage = passwordError,
                    onValueChange = onPasswordChanged,
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

                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {

                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(contentColor = Color.Gray),
                        onClick = onLogInClick,
                    ) {
                        Text(
                            text = "Log in", color = Color.Black
                        )
                    }
                    Spacer(modifier = Modifier.height(5.dp))

                    ClickableText(
                        style = TextStyle(color = Color.Black),
                        text = AnnotatedString(text = "Forgot Password?"),
                        onClick = {
                            navigateTo(navController, AuthScreen.Forgot.route, true)
                        })
                    Spacer(modifier = Modifier.height(20.dp))

                }
            }
        }
        val annotatedText = buildAnnotatedString {
            withStyle(
                style = SpanStyle(color = Color.Gray)
            ) { append("Don't have account? ") }

            pushStringAnnotation(
                tag = "SignUp", // provide tag which will then be provided when you click the text
                annotation = "SignUp"
            )

            withStyle(
                style = SpanStyle(color = MaterialTheme.colorScheme.primary)
            ) {
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
    ComposeNavigationTheme {
        Surface {
            LogInUi()
        }
    }
}