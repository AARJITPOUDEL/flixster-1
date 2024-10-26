package com.example.moviedb_app

import MovieResponse
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val apiKey = "a07e22bc18f5cb106bfe4cc1f83ad8ed"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        // Set up GridLayoutManager with 2 columns
        recyclerView.layoutManager = GridLayoutManager(this, 2)



        fetchMovies()
    }



    private fun fetchMovies() {
        RetrofitClient.apiService.getNowPlayingMovies(apiKey).enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    val movies = response.body()?.results ?: emptyList()

                    // Set up the adapter with a click listener
                    recyclerView.adapter = MovieAdapter(movies)
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                // Handle error
            }
        })
    }
}