package com.example.roomwordsample

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class WordViewModel(private val respository: WordRepository) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    // the UI when the data actually changes.
    // - Respository is completely separated from the UI through the ViewModel.
    val allWords: LiveData<List<Word>> = respository.allWords.asLiveData()

    /**
     * Lauching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(word: Word) = viewModelScope.launch {
        respository.insert(word)
    }
}

class WordViewModelFactory(private val respository: WordRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WordViewModel(respository) as T
        }
        throw IllegalAccessException("Unkown ViewModel class")
    }
}