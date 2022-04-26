package com.amod.kotlinquotes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.amod.kotlinquotes.model.Quote
import com.amod.kotlinquotes.model.QuoteDatabase
import com.amod.kotlinquotes.model.QuoteRepository
import kotlinx.coroutines.*

class QuoteViewModal(application: Application) : AndroidViewModel(application) {
    val allQuotes: LiveData<List<Quote>>
    val repository: QuoteRepository

    init {
        val dao = QuoteDatabase.getDatabase(application).getQuoteDao()
        repository = QuoteRepository(dao)
        allQuotes = repository.allNotes
    }

    fun deleteNote(quote: Quote) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(quote)
    }

    fun updateNote(quote: Quote) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(quote)
    }

    fun addNote(quote: Quote) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(quote)
    }
}