package com.example.composenavigation.screens

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.composenavigation.navigation.graphs.AuthScreen
import com.example.composenavigation.navigation.graphs.Graph
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {

    val scale = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.7f,
            animationSpec = tween(
                durationMillis = 800,
                easing = { OvershootInterpolator(4f).getInterpolation(it) })
        )
        delay(2000L)

        val destination =
            if (FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()) Graph.AUTHENTICATION else Graph.HOME
        navController.navigate(destination) {
            popUpTo(AuthScreen.Splash.route) {
                inclusive = true
            }
            launchSingleTop = true
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Cyan),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.scale(scale.value),
            text = "Splash Screen",
            color = Color.DarkGray,
            style = MaterialTheme.typography.subtitle1,
            fontSize = 32.sp
        )
    }
}
