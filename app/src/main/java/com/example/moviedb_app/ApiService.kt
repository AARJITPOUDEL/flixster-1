package com.example.moviedb_app

import Movie
import MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query



interface ApiService {


    @GET("movie/now_playing")
    fun getNowPlayingMovies(@Query("api_key") apiKey: String): Call<MovieResponse>
}
