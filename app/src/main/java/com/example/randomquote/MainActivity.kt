package com.example.randomquote

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.randomquote.repository.Response
import com.example.randomquote.viewmodels.MainViewModel
import com.example.randomquote.viewmodels.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = (application as QuoteApplication).quoteRepository

        mainViewModel = ViewModelProvider(this,MainViewModelFactory(repository)).get(MainViewModel::class.java)

        mainViewModel.quotes.observe(this, Observer {
            when(it){
                is Response.Loading -> {}
                is Response.Success -> {
                    it.data?.let {
                        Toast.makeText(this, it.results.size.toString(),Toast.LENGTH_SHORT).show()
                    }
                }
                is Response.Error -> {
                    Toast.makeText(this, "Something went wrong",Toast.LENGTH_SHORT).show()
                }
            }

        })
    }
}