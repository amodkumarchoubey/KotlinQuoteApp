package com.amod.kotlinquotes.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface QuoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(quote : Quote)

    @Update
    suspend fun update(quote: Quote)

    @Delete
    suspend fun delete(quote: Quote)

    @Query("Select * from quoteTable order by id ASC")
    fun getAllQuotes(): LiveData<List<Quote>>


}