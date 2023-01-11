package com.example.composenavigation.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

//@Composable
//fun BottomHomeScreen() {
//    Surface(
//        modifier = Modifier
//            .fillMaxWidth()
//            .fillMaxHeight()
//    ) {
//
//        Row(modifier = Modifier.fillMaxWidth()) {
//            Text(text = "This is the movie name")
//        }
//    }
//}

@Composable
fun BottomHomeScreen(viewModel: BottomHomeScreenViewModel = hiltViewModel()) {
    val questions = viewModel.data.value.data?.toMutableList()

    val questionIndex = remember {
        mutableStateOf(0)
    }

    if (viewModel.data.value.loading == true) {
        CircularProgressIndicator()
    } else {

        if (questions != null) {
            Column(modifier = Modifier.padding(12.dp)) {
                LazyColumn {
                    items(items = questions) { question ->
                        MovieRow(question = question.question, answer = question.answer)
                    }
                }
            }
        }
    }

}

@Composable
fun MovieRow(question: String, answer: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth().fillMaxSize()
            .padding(4.dp)
            , elevation = 6.dp,
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "QUESTION: $question")
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "ANSWER: $answer")
        }
    }
}
