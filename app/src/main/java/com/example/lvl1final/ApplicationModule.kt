package com.example.lvl1final

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.lvl1final.data.db.KinopoiskDao
import com.example.lvl1final.data.db.KinopoiskDatabase
import com.example.lvl1final.data.entity.MoviesCollection
import com.example.lvl1final.data.NetworkConnectionManager
import com.example.lvl1final.presentation.onboarding.UserPreferencesRepository
import com.example.lvl1final.data.NetworkConnectionManagerImpl
import com.example.lvl1final.data.repositories.KinopoiskNetworkRepository
import com.example.lvl1final.data.repositories.KinopoiskNetworkRepositoryImpl
import com.example.lvl1final.data.repositories.KinopoiskStorageRepository
import com.example.lvl1final.data.repositories.KinopoiskStorageRepositoryImpl
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
    fun provideKinopoiskStorageRepository(dao: KinopoiskDao): KinopoiskStorageRepository {
        return KinopoiskStorageRepositoryImpl(dao)
    }

    @Provides
    fun provideKinopoiskNetworkRepository(): KinopoiskNetworkRepository {
        return KinopoiskNetworkRepositoryImpl()
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
    fun provideDataStore(@ApplicationContext context: Context) = UserPreferencesRepository(context)


    private suspend fun insertDefaultCollections(dao: KinopoiskDao) {
        withContext(Dispatchers.IO) {
            val favoriteCollection = MoviesCollection(
                collectionName = "Любимое",
                collectionIcon = R.drawable.ic_heart,
                numberOfMovies = 0,
                deletable = false
            )
            val delayedCollection = MoviesCollection(
                collectionName = "Хочу посмотреть",
                collectionIcon = R.drawable.ic_bookmark,
                numberOfMovies = 0,
                deletable = false

            )
            dao.insertCollection(favoriteCollection)
            dao.insertCollection(delayedCollection)
        }
    }


    fun getInstance(context: Context): KinopoiskDatabase {
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


