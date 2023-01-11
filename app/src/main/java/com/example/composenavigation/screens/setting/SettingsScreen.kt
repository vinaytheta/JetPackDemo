package com.example.composenavigation.screens.setting

import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.composenavigation.navigation.graphs.AuthScreen
import com.example.composenavigation.navigation.graphs.Graph
import com.google.firebase.auth.FirebaseAuth

@Composable
fun SettingsScreen(navController: NavController) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Setting Screen", fontSize = 22.sp)
            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(40.dp),
                colors = ButtonDefaults.buttonColors(contentColor = Color.Gray),
                onClick = {
                    FirebaseAuth.getInstance().signOut()
                    navController.navigate(AuthScreen.Login.route) {
                        popUpTo(Graph.HOME) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }) {
                Text(
                    text = "Sign Out",
                    color = Color.Black
                )
            }

        }
    }
}