package com.example.lvl1final.data.network.api


import com.example.lvl1final.BuildConfig
import com.example.lvl1final.presentation.Arguments
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = BuildConfig.BASE_URL
private const val API_KEY = ""

interface KinopoiskApi {
    @Headers("X-API-KEY: $API_KEY")
    @GET("api/v2.2/films/premieres")
    suspend fun getPremieres(
        @Query("year") year: Int,
        @Query("month") month: String
    ): KinopoiskPremieresDto

    @Headers("X-API-KEY: $API_KEY")
    @GET("api/v2.2/films/top")
    suspend fun getTopMovies(
        @Query("type") type: String,
        @Query("page") page: Int
    ): KinopoiskTopDto

    @Headers("X-API-KEY: $API_KEY")
    @GET("api/v2.2/films/filters")
    suspend fun getMovieSelectionFilters(
    ): KinopoiskMovieSelectionFiltersDto

    @Headers("X-API-KEY: $API_KEY")
    @GET("api/v2.2/films")
    suspend fun getSelectionMovies(
        @Query("order") order: String = "RATING",
        @Query("ratingFrom") ratingFrom: Int = 6,
        @Query("ratingTo") ratingTo: Int = 10,
        @Query("yearFrom") yearFrom: Int = 1000,
        @Query("yearTo") yearTo: Int = 3000,
        @Query("keyword") keyword: String = "",
        @Query("countries") countries: Int? = null,
        @Query("genres") genres: Int? = null,
        @Query("type") type: String = Arguments.ARG_FILM_TYPE,
        @Query("page") page: Int
    ): KinopoiskSelectionMoviesDto

    @Headers("X-API-KEY: $API_KEY")
    @GET("api/v2.2/films/{id}")
    suspend fun getMovieInfo(
        @Path("id") kinopoiskId: Int,
    ): KinopoiskMovieInfoDto

    @Headers("X-API-KEY: $API_KEY")
    @GET("api/v1/staff")
    suspend fun getMovieStaffInfo(
        @Query("filmId") filmId: Int
    ): List<MovieStaffDto>

    @Headers("X-API-KEY: $API_KEY")
    @GET("api/v2.2/films/{id}/images")
    suspend fun getMovieImages(
        @Path("id") id: Int,
        @Query("type") type: String
    ): MovieImagesDto

    @Headers("X-API-KEY: $API_KEY")
    @GET("api/v2.2/films/{id}/similars")
    suspend fun getSimilarMovies(
        @Path("id") id: Int
    ): SimilarMoviesDto

    @Headers("X-API-KEY: $API_KEY")
    @GET("api/v2.2/films/{id}/seasons")
    suspend fun getSeasonsAndEpisodes(
        @Path("id") id: Int
    ): SeasonsAndEpisodesDto

    @Headers("X-API-KEY: $API_KEY")
    @GET("api/v1/staff/{id}")
    suspend fun getActorInfo(
        @Path("id") id: Int
    ): ActorDto
}

object RetrofitServices {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val getKinopoiskData: KinopoiskApi = retrofit.create(KinopoiskApi::class.java)

}


