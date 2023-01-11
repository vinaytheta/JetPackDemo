package com.example.composenavigation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun AuthHeader(title: String, painterResource: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    topEnd = CornerSize(0.dp),
                    topStart = CornerSize(0.dp),
                    bottomStart = CornerSize(0.dp),
                    bottomEnd = CornerSize(90.dp)
                )
            )
            .background(MaterialTheme.colors.secondaryVariant)
    ) {
        Image(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth(),
            painter = androidx.compose.ui.res.painterResource(id = painterResource),
            contentDescription = null
        )
        Text(
            modifier = Modifier.padding(8.dp),
            text = title,
            color = Color.Black,
            style = MaterialTheme.typography.h4
        )
    }
}

@Composable
fun TextFieldWithError(
    label: String,
    value: String,
    keyboardType: KeyboardType,
    isError: Boolean = false,
    errorMessage: String? = "",
    onValueChange: (String) -> Unit,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    Column {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = label) },
            value = value,
            onValueChange = { onValueChange(it) },
            isError = isError,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            colors = TextFieldDefaults.textFieldColors(
                Color.DarkGray
            ),
            shape = MaterialTheme.shapes.small,
            singleLine = true,
            trailingIcon = trailingIcon,
            visualTransformation = visualTransformation,
        )
        if (isError)
            ErrorText(errorMessage)
    }
}

@Composable
fun ErrorText(errorMessage: String?) {
    Text(
        modifier = Modifier.padding(top = 4.dp, start = 16.dp),
        text = errorMessage ?: "",
        color = Color.Red,
        style = MaterialTheme.typography.body1.copy(color = Color.Red)
    )
}
