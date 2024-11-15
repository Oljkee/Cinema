package com.example.lvl1final

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.lvl1final.data.storage.KinopoiskDao
import com.example.lvl1final.data.storage.KinopoiskDatabase
import com.example.lvl1final.data.storage.entity.MoviesCollectionEntity
import com.example.lvl1final.data.network.NetworkConnectionManager
import com.example.lvl1final.data.repositories.UserPreferencesRepositoryImpl
import com.example.lvl1final.data.network.NetworkConnectionManagerImpl
import com.example.lvl1final.data.repositories.InterestingMovieRepositoryImpl
import com.example.lvl1final.data.repositories.MovieRepositoryImpl
import com.example.lvl1final.domain.repository.MovieRepository
import com.example.lvl1final.data.repositories.MovieCollectionRepositoryImpl
import com.example.lvl1final.data.repositories.WatchedMoviesRepositoryImpl
import com.example.lvl1final.domain.repository.InterestingMovieRepository
import com.example.lvl1final.domain.repository.MovieCollectionRepository
import com.example.lvl1final.domain.repository.UserPreferencesRepository
import com.example.lvl1final.domain.repository.WatchedMoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.*
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ApplicationModule {
    @Provides
    fun provideKinopoiskDao(kinopoiskDatabase: KinopoiskDatabase): KinopoiskDao {
        return kinopoiskDatabase.kinopoiskDao()
    }

    @Provides
    fun provideWatchedMoviesRepository(dao: KinopoiskDao): WatchedMoviesRepository {
        return WatchedMoviesRepositoryImpl(dao)
    }
    @Provides
    fun provideInterestingMovieRepository(dao: KinopoiskDao): InterestingMovieRepository {
        return InterestingMovieRepositoryImpl(dao)
    }
    @Provides
    fun provideMovieCollectionRepository(dao: KinopoiskDao): MovieCollectionRepository {
        return MovieCollectionRepositoryImpl(dao)
    }

    @Provides
    fun provideMovieRepository(): MovieRepository {
        return MovieRepositoryImpl()
    }

    @Provides
    @Singleton
    fun ProvideKinopoiskDatabase(@ApplicationContext appContext: Context): KinopoiskDatabase {
        return getInstance(appContext)
    }

    @Provides
    @Singleton
    fun provideNetworkConnectionManager(@ApplicationContext context: Context): NetworkConnectionManager {
        return NetworkConnectionManagerImpl(context, CoroutineScope(Dispatchers.Default + Job()))
    }

    @Provides
    @Singleton
    fun provideUserPreferencesRepository(@ApplicationContext context: Context) : UserPreferencesRepository {
        return UserPreferencesRepositoryImpl(context)
    }


    private suspend fun insertDefaultCollections(dao: KinopoiskDao) {
        withContext(Dispatchers.IO) {
            val favoriteCollection = MoviesCollectionEntity(
                collectionName = "Любимое",
                collectionIcon = R.drawable.ic_heart,
                numberOfMovies = 0,
                deletable = false
            )
            val delayedCollection = MoviesCollectionEntity(
                collectionName = "Хочу посмотреть",
                collectionIcon = R.drawable.ic_bookmark,
                numberOfMovies = 0,
                deletable = false

            )
            dao.insertCollection(favoriteCollection)
            dao.insertCollection(delayedCollection)
        }
    }


    fun getInstance(@ApplicationContext context: Context): KinopoiskDatabase {
        return runBlocking {
            buildDatabase(context)
        }
    }

    private suspend fun buildDatabase(context: Context): KinopoiskDatabase {
        return withContext(Dispatchers.IO) {
            Room.databaseBuilder(
                context,
                KinopoiskDatabase::class.java,
                "app-database.db"
            ).addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    CoroutineScope(Dispatchers.Default + Job()).launch {
                        val dao = getInstance(context).kinopoiskDao()
                        insertDefaultCollections(dao)
                    }
                }
            })
                .build()
        }
    }
}


