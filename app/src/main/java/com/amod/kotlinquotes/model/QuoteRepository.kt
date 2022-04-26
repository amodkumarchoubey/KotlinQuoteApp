package com.amod.kotlinquotes.model

import androidx.lifecycle.LiveData

class QuoteRepository(private val quoteDao: QuoteDao) {


    val allNotes: LiveData<List<Quote>> = quoteDao.getAllQuotes()

    suspend fun insert(quote: Quote) {
        quoteDao.insert(quote)
    }
    suspend fun delete(quote: Quote){
        quoteDao.delete(quote)
    }

    suspend fun update(quote: Quote){
        quoteDao.update(quote)
    }
}