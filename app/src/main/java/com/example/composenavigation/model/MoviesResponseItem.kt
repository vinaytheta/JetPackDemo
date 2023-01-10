package com.example.composenavigation.model

data class MoviesResponseItem(
    val answer: String,
    val category: String,
    val choices: List<String>,
    val question: String
)