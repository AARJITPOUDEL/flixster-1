
data class MovieResponse(
    val dates: Dates,
    val page: Int,
    val results: List<Movie>
)

data class Dates(
    val maximum: String,
    val minimum: String
)

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val poster_path: String?,
    val release_date: String,
    val vote_average: Double,
    val vote_count: Int,
    val genre_ids: List<Int>,
    val popularity: Double,
    val original_language: String,
    val backdrop_path: String?
)
