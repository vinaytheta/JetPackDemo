package com.example.composenavigation.network

import com.example.composenavigation.model.Question
import retrofit2.http.GET
import javax.inject.Singleton

//https://raw.githubusercontent.com/itmmckernan/triviaJSON/master/movies.json
@Singleton
interface QuestionApi {
    @GET("movies.json")
    suspend fun fetchData(): Question
}