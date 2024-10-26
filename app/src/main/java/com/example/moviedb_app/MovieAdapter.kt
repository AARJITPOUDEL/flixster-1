package com.example.moviedb_app

// Import necessary packages
import Movie
import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieAdapter(private val movies: List<Movie>) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount() = movies.size

    class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val title: TextView = view.findViewById(R.id.title)
        private val poster: ImageView = view.findViewById(R.id.poster)
        private val releaseDate: TextView = view.findViewById(R.id.release_date)
        private val ratingBar: RatingBar = view.findViewById(R.id.rating_bar)
        private val cardView: CardView = view.findViewById(R.id.card_view)
//        private val averageColorTextView: TextView = view.findViewById(R.id.average_color_text_view) // Add this line

        fun bind(movie: Movie) {
            title.text = movie.title
            releaseDate.text = " ${movie.release_date}     ${movie.original_language}"
            ratingBar.rating = (movie.vote_average / 2).toFloat()

            val posterUrl = "https://image.tmdb.org/t/p/w500${movie.poster_path}"
            Glide.with(itemView.context).asBitmap().load(posterUrl).into(poster)

            // Calculate average color in a coroutine
            GlobalScope.launch(Dispatchers.Main) {
                val bitmap = withContext(Dispatchers.IO) {
                    Glide.with(itemView.context)
                        .asBitmap()
                        .load(posterUrl)
                        .submit()
                        .get()
                }
                // Get average color and set it to TextView
                val averageColor = getAverageColor(bitmap)
                cardView.setCardBackgroundColor(averageColor)
                Log.i("Color","Avg Color: #${Integer.toHexString(averageColor).substring(2)}")
            }

        }

        private fun getAverageColor(bitmap: Bitmap): Int {
            val pixels = IntArray(bitmap.width * bitmap.height)
            bitmap.getPixels(pixels, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)

            var redSum = 0
            var greenSum = 0
            var blueSum = 0

            for (pixel in pixels) {
                redSum += android.graphics.Color.red(pixel)
                greenSum += android.graphics.Color.green(pixel)
                blueSum += android.graphics.Color.blue(pixel)
            }

            val pixelCount = pixels.size
            val averageRed = redSum / pixelCount
            val averageGreen = greenSum / pixelCount
            val averageBlue = blueSum / pixelCount

            return android.graphics.Color.rgb(averageRed, averageGreen, averageBlue)
        }
    }
}