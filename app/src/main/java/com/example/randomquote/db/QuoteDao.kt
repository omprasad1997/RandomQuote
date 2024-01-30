package com.example.randomquote.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.randomquote.models.Result

@Dao
interface QuoteDao {

    @Insert
    suspend fun addQuotes(quoteList:List<Result>)

    @Query("SELECT * FROM quote")
    suspend fun getQuotes() : List<Result>
}