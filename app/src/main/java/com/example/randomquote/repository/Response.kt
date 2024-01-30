package com.example.randomquote.repository

import com.example.randomquote.models.QuoteList

/*
sealed class Response(val data:QuoteList? = null, val errorMessage:String? = null) {

    class Loading : Response()
    class Success(quoteList: QuoteList) : Response(data = quoteList)
    class Error(errorMessage:String)   : Response(errorMessage = errorMessage)
}
 */

//Generics class
sealed class Response<T>(val data:T? = null, val errorMessage:String? = null) {

    class Loading<T> : Response<T>()
    class Success<T>(quoteList: T?) : Response<T>(data = quoteList)
    class Error<T>(errorMessage:String)   : Response<T>(errorMessage = errorMessage)
}