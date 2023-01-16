package com.example.composenavigation.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties

@Composable
fun PhotosOrGalleryDialog() {

    var dialogOpen by remember {
        mutableStateOf(true)
    }

    if (dialogOpen) {
        AlertDialog(
            onDismissRequest = {
                // Dismiss the dialog when the user clicks outside the dialog or on the back
                // button. If you want to disable that functionality,
                // simply leave this block empty.
                dialogOpen = false
            },
            buttons = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Cyan),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    Button(colors = ButtonDefaults.buttonColors(Color.DarkGray),
                        onClick = {
                            dialogOpen = false
                        }) {
                        Text(text = "Camera", color = Color.White)
                    }

                    Button(colors = ButtonDefaults.buttonColors(Color.DarkGray),
                        onClick = {
                        dialogOpen = false
                    }) {
                        Text(text = "Gallery", color = Color.White)
                    }

                }
            },
            title = {
                Text(text = "Choose an option", modifier = Modifier.padding(vertical = 20.dp))
            },
            modifier = Modifier // Set the width and padding
                .fillMaxWidth()
                .padding(32.dp),
            shape = RoundedCornerShape(5.dp),
            backgroundColor = Color.Cyan,
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        )
    }
}

@Preview
@Composable
fun PhotosOrGalleryDialogPreview() {
    PhotosOrGalleryDialog()
}
