package com.example.lvl1final.data.db

import androidx.room.*
import com.example.lvl1final.data.entity.CollectionMovie
import com.example.lvl1final.data.entity.CollectionWithMovies
import com.example.lvl1final.data.entity.InterestingMovie
import com.example.lvl1final.data.entity.InterestingMovieWithKinopoiskMovie
import com.example.lvl1final.data.entity.KinopoiskMovie
import com.example.lvl1final.data.entity.MoviesCollection
import com.example.lvl1final.data.entity.WatchedMovie
import com.example.lvl1final.data.entity.WatchedMovieWithKinopoiskMovie
import kotlinx.coroutines.flow.Flow

@Dao
interface KinopoiskDao {
    // Создание коллекции
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCollection(moviesCollection: MoviesCollection)

    // Удаление коллекции
    @Delete
    suspend fun deleteCollection(moviesCollection: MoviesCollection)

    @Query("UPDATE movies_collection SET number_of_movies = :numberOfMovies WHERE id = :collectionId")
    suspend fun setNumberOfMoviesIntoMoviesCollection(collectionId: Int, numberOfMovies: Int)

    // добавить фильм в подборку
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovieToCollection(collectionMovie: CollectionMovie)

    // удалить фильм из подборки
    @Delete
    suspend fun deleteMovieFromCollection(collectionMovie: CollectionMovie)


    // Увеличить количество фильмов в колонке number_of_movies, таблицы movies_collections
    @Query("UPDATE movies_collection SET number_of_movies = number_of_movies + 1 WHERE id = :collectionId")
    suspend fun incrementNumberOfMovies(collectionId: Int)

    // Уменьшить количество фильмов в колонке number_of_movies, таблицы movies_collections
    @Query("UPDATE movies_collection SET number_of_movies = number_of_movies - 1 WHERE id = :collectionId")
    suspend fun decrementNumberOfMovies(collectionId: Int)


    // получить список всех подборок
    @Query("SELECT * FROM movies_collection")
    fun getCollectionList(): Flow<List<MoviesCollection>>

    @Query("SELECT id FROM movies_collection WHERE collection_name = :collectionName")
    suspend fun getCollectionId(collectionName: String): Int

    //Добавление фильма в список фильмов, которые принадлежат находятся хотя-бы в одной подборке
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovie(movie: KinopoiskMovie)


    // получить все фильмы конкретной коллекции
    @Transaction
    @Query("SELECT * FROM movies_collection WHERE id = :collectionId")
    suspend fun getCollectionWithMovies(collectionId: Int): CollectionWithMovies?

    @Transaction
    @Query("SELECT * FROM movies_collection WHERE id = :collectionId")
    fun getCollectionWithMoviesAsFlow(collectionId: Int): Flow<CollectionWithMovies?>


    // удалить фильмы, которые не находятся ни в одной подборке
    @Query("DELETE FROM kinopoisk_movie WHERE kinopoisk_id NOT IN (SELECT movie_id FROM collection_movie)")
    fun deleteMoviesWithoutCollection()

    @Query("SELECT collection_id FROM collection_movie WHERE movie_id = :kinopoiskId")
    suspend fun getCollectionIdListWithThisMovie(kinopoiskId: Int): List<Int>?


    //добавить фильм в просмотренные
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovieToWatchedCollection(watchedMovie: WatchedMovie)

    //удалить фильм из просмотренных
    @Delete
    suspend fun deleteMovieFromWatchedCollection(watchedMovie: WatchedMovie)

    //получить список просмотренных фильмов
    @Transaction
    @Query("SELECT * FROM watched_movie")
    fun getWatchedMoviesWithKinopoiskMovies(): Flow<List<WatchedMovieWithKinopoiskMovie?>>

    //проверка фильма в списке просмотренных
    @Query("SELECT * FROM watched_movie WHERE kinopoisk_id = :kinopoiskId")
    suspend fun getIdInWatchedCollection(kinopoiskId: Int): WatchedMovie?


    //получить список интересующих фильмов
    @Transaction
    @Query("SELECT * FROM interesting_movie")
    fun getInterestingMoviesWithKinopoiskMovies(): Flow<List<InterestingMovieWithKinopoiskMovie?>>

    //проверка фильма в списке интересующих
    @Query("SELECT * FROM interesting_movie WHERE kinopoisk_id = :kinopoiskId")
    suspend fun getIdInInterestingCollection(kinopoiskId: Int): InterestingMovie?

    @Query("SELECT kinopoisk_id FROM interesting_movie WHERE kinopoisk_id = :kinopoiskId")
    suspend fun getInterestingMovieId(kinopoiskId: Int): Int?


    // получить размер коллекции
    @Query("SELECT COUNT(*) FROM interesting_movie")
    suspend fun getInterestingCollectionSize(): Int

    // получить первый элемент в списке
    @Query("SELECT kinopoisk_id FROM interesting_movie LIMIT 1")
    suspend fun getOldestInterestingMovieId(): Int?

    // удалить элемент
    @Query("DELETE FROM interesting_movie WHERE kinopoisk_id = :kinopoiskId")
    suspend fun deleteInterestingMovieById(kinopoiskId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateInterestingMovie(interestingMovie: InterestingMovie)


    // проверка фильма в конкретной коллекции
    @Transaction
    @Query("SELECT * FROM collection_movie WHERE movie_id = :movieId")
    suspend fun getMovieCollections(movieId: Int): List<CollectionMovie?>


}
