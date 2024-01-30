package com.example.randomquote.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.randomquote.api.QuoteService
import com.example.randomquote.db.QuoteDatabase
import com.example.randomquote.models.QuoteList
import com.example.randomquote.utils.NetworkUtils
import java.lang.Exception

class QuoteRepository(
    private val quoteService: QuoteService,
    private val quoteDatabase: QuoteDatabase,
    private val context: Context
) {
    private val quotesLiveData = MutableLiveData<Response<QuoteList>>()

    val quotes : LiveData<Response<QuoteList>>
        get() = quotesLiveData

    suspend fun getQuotes(page:Int){
        if(NetworkUtils.isNetworkAvailable(context)){
            try {
                val result = quoteService.getQuotes(page)
                if(result.body() != null){
                    quoteDatabase.quoteDao().addQuotes(result.body()!!.results)
                    quotesLiveData.postValue(Response.Success(result.body()))
                } else {
                    quotesLiveData.postValue(Response.Error("API Error"))
                }
            }catch (e:Exception){
                quotesLiveData.postValue(Response.Error(e.toString()))
            }
        } else {
            try {
                val quotes = quoteDatabase.quoteDao().getQuotes()
                val quoteList = QuoteList(1,1,1,quotes,1,1)
                quotesLiveData.postValue(Response.Success(quoteList))
            } catch (e : Exception){
                quotesLiveData.postValue(Response.Error(e.toString()))
            }

        }
    }

    suspend fun getQuotesBackground(){
        val randomNumber = (Math.random() * 10).toInt()
        val result = quoteService.getQuotes(randomNumber)
        if(result.body() != null){
            quoteDatabase.quoteDao().addQuotes(result.body()!!.results)
        }
    }
}