package com.example.composenavigation.screens.auth.signup

import android.Manifest
import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.composenavigation.R
import com.example.composenavigation.components.AuthHeader
import com.example.composenavigation.components.TextFieldWithError
import com.example.composenavigation.navigation.graphs.Graph
import com.example.composenavigation.utils.PhotosOrGalleryDialog
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SignUpScreen(
    navController: NavController, viewModel: SignUpViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val myImage: Bitmap? =
        BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.ic_person)
    val result = remember {
        mutableStateOf(myImage)
    }
    var requestCameraPermission by remember {
        mutableStateOf(false)
    }
    val loadImage =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) {
            result.value = it
        }


    SignUpUi(
        navController = navController,
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
        confirmPassword = viewModel.confirmPasswordState.text,
        onConfirmPasswordChanged = {
            viewModel.onConfirmPasswordChange(it)
            viewModel.confirmPasswordState.validate()
        },
    ) {

        viewModel.createUserWithEmailAndPassword({
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }) {
            navController.navigate(Graph.HOME) {
                popUpTo(Graph.AUTHENTICATION) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }
    }
}

@SuppressLint("PermissionLaunchedDuringComposition")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SignUpUi(
    navController: NavController = rememberNavController(),
    email: String = "email",
    emailError: String? = null,
    onEmailChanged: (String) -> Unit = {},
    password: String = "Password",
    passwordError: String? = null,
    onPasswordChanged: (String) -> Unit = {},
    confirmPassword: String = "Password",
    onConfirmPasswordChanged: (String) -> Unit = {},
    onSignUpClick: () -> Unit = {}
) {

    var showCameraDialog by remember {
        mutableStateOf(false)
    }

    val permissions = listOf(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
    )

    val multiplePermissionsState = rememberMultiplePermissionsState(permissions)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(
                Color.Cyan
            )
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            AuthHeader(
                title = "Create account", painterResource = R.drawable.bg_login_header
            )

            Spacer(modifier = Modifier.height(40.dp))

            Image(painter = painterResource(id = R.drawable.ic_person),
                contentDescription = "user_image",
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .height(100.dp)
                    .width(100.dp)
                    .background(color = Color.DarkGray)
                    .align(alignment = Alignment.CenterHorizontally)
                    .clickable {

                        multiplePermissionsState.launchMultiplePermissionRequest()
                        multiplePermissionsState.permissions.forEach { perm ->
                            when (perm.permission) {
                                Manifest.permission.CAMERA -> {
                                    when {
                                        perm.status.isGranted -> {
                                            Log.d("TAGPermission", "Camera permission accepted")
                                        }
                                        perm.status.shouldShowRationale -> {
                                            Log.d(
                                                "TAGPermission", "Camera permission is needed" +
                                                        "to access the camera"
                                            )
                                        }
                                    }
                                }
                                Manifest.permission.WRITE_EXTERNAL_STORAGE -> {
                                    when {
                                        perm.status.isGranted -> {
                                            Log.d("TAGPermission", "Gallery permission accepted")
                                        }
                                        perm.status.shouldShowRationale -> {
                                            Log.d(
                                                "TAGPermission", "Gallery permission is needed" +
                                                        "to access the camera"
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        if (multiplePermissionsState.allPermissionsGranted) {
                            showCameraDialog = true
//                            PhotosOrGalleryDialog()
                            Log.d("TAGPermission", "SignUpUi: All Granted ")
                        }

                    })

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = 20.dp, start = 40.dp, end = 40.dp, bottom = 60.dp
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                val passwordVisible = remember {
                    mutableStateOf(false)
                }

                val confirmPasswordVisible = remember {
                    mutableStateOf(false)
                }

                Spacer(modifier = Modifier.height(20.dp))

                TextFieldWithError(
                    label = "Email",
                    value = email,
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                    isError = emailError != null,
                    errorMessage = emailError,
                    onValueChange = onEmailChanged
                )

                Spacer(modifier = Modifier.height(20.dp))

                TextFieldWithError(
                    label = "Password",
                    value = password,
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next,
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

                TextFieldWithError(
                    label = "Confirm Password",
                    value = confirmPassword,
                    keyboardType = KeyboardType.Password,
                    isError = passwordError != null,
                    errorMessage = passwordError,
                    imeAction = ImeAction.Done,
                    onValueChange = onConfirmPasswordChanged,
                    trailingIcon = {
                        val eyeIcon = if (confirmPasswordVisible.value) Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff

                        val contentDescription =
                            if (confirmPasswordVisible.value) "Hide password" else "Show password"
                        IconButton(onClick = {
                            confirmPasswordVisible.value = !confirmPasswordVisible.value
                        }) {
                            Icon(imageVector = eyeIcon, contentDescription = contentDescription)
                        }
                    },
                    visualTransformation = if (confirmPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(Color.DarkGray),
                    onClick = onSignUpClick
                ) {
                    Text(
                        text = "Sign up", color = Color.White
                    )
                }
            }
        }

        val annotatedText = buildAnnotatedString {
            withStyle(
                style = SpanStyle(color = Color.Gray)
            ) { append("Already have account? ") }

            pushStringAnnotation(
                tag = "Login", annotation = "Login"
            )

            withStyle(
                style = SpanStyle(color = MaterialTheme.colorScheme.primary)
            ) {
                append("Login")
            }
            withStyle(style = SpanStyle(color = Color.Gray)) { append(".") }
            // when pop is called it means the end of annotation with current tag
            pop()
        }

        ClickableText(modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(20.dp),
            text = annotatedText,
            onClick = { offset ->
                annotatedText.getStringAnnotations(
                    tag = "Login",// tag which you used in the buildAnnotatedString
                    start = offset, end = offset
                ).firstOrNull()?.let {
                    navController.popBackStack()
                }
            })
    }
    if (showCameraDialog) {
        PhotosOrGalleryDialog()
        showCameraDialog= false
    }
}

@Preview
@Composable
fun SignUpUiPreview() {
    SignUpUi()
}