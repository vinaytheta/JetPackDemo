package com.example.composenavigation.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BottomHomeScreen() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {

        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = "This is the movie name")
        }
    }
}

@Preview
@Composable
fun BottomHomeScreenPreview() {
    MainContent()
}

@Composable
fun MainContent(movieList: List<String> = listOf("sfdg", "sdf", "dfgh")) {
    Column(modifier = Modifier.padding(12.dp)) {
        LazyColumn {
            items(items = movieList) { item ->
                MovieRow(question = item)
            }
        }
    }
}

@Composable
fun MovieRow(question: String) {
    androidx.compose.material.Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .height(130.dp), elevation = 6.dp,
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = question)
//            Text(text = answer)
        }
    }
}
