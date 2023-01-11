package com.example.composenavigation.screens.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composenavigation.data.DataOrException
import com.example.composenavigation.model.QuestionItem
import com.example.composenavigation.repository.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BottomHomeScreenViewModel @Inject constructor(
    private val questionRepository: QuestionRepository
) : ViewModel() {
    val data: MutableState<DataOrException<ArrayList<QuestionItem>, Boolean, Exception>> =
        mutableStateOf(
            DataOrException(null, true, Exception(""))
        )

    init {
        getAllQuestions()
    }

    private fun getAllQuestions() {
        viewModelScope.launch {
            data.value.loading = true
            data.value = questionRepository.getAllQuestions()
            if (data.value.data!!.isNotEmpty()) {
                data.value.loading = false
            }
        }
    }
}