package com.example.lvl1final.presentation.onboarding

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.lvl1final.data.entity.FilterParameters
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class UserPreferencesRepository @Inject constructor(private val context: Context) {

    private object PreferencesKeys {
        val ONBOARDING_COMPLETED_KEY = booleanPreferencesKey("onboarding_completed")

        val COUNTRY_ID_SEARCH_FILTER = intPreferencesKey("selected_id_country")
        val GENRE_ID_SEARCH_FILTER = intPreferencesKey("selected_id_genre")

        val COUNTRY_SEARCH_FILTER = stringPreferencesKey("selected_country")
        val GENRE_SEARCH_FILTER = stringPreferencesKey("selected_genre")
        val ORDER_SEARCH_FILTER = stringPreferencesKey("order")
        val TYPE_SEARCH_FILTER = stringPreferencesKey("type")
        val RATING_FROM_SEARCH_FILTER = stringPreferencesKey("rating_from")
        val RATING_TO_SEARCH_FILTER = stringPreferencesKey("rating_to")
        val YEAR_FROM_SEARCH_FILTER = stringPreferencesKey("year_from")
        val YEAR_TO_SEARCH_FILTER = stringPreferencesKey("year_to")

        val MOVIE_WATCHED_STATE = booleanPreferencesKey("movie_watched_state")
    }

    suspend fun updateOnBoardingCompleted(isCompleted: Boolean) {
        context.dataStore.edit { pref ->
            pref[PreferencesKeys.ONBOARDING_COMPLETED_KEY] = isCompleted
        }
    }

    val isOnBoardingCompletedFlow: Flow<Boolean> = context.dataStore.data
        .map { pref ->
            pref[PreferencesKeys.ONBOARDING_COMPLETED_KEY] ?: false
        }

    suspend fun updateSearchFilterParameters(filterParameters: FilterParameters) {
        context.dataStore.edit { pref ->
            pref[PreferencesKeys.COUNTRY_ID_SEARCH_FILTER] = filterParameters.countryId
            pref[PreferencesKeys.GENRE_ID_SEARCH_FILTER] = filterParameters.genreId
            pref[PreferencesKeys.COUNTRY_SEARCH_FILTER] = filterParameters.country
            pref[PreferencesKeys.GENRE_SEARCH_FILTER] = filterParameters.genre
            pref[PreferencesKeys.ORDER_SEARCH_FILTER] = filterParameters.sorting
            pref[PreferencesKeys.TYPE_SEARCH_FILTER] = filterParameters.type
            pref[PreferencesKeys.RATING_FROM_SEARCH_FILTER] = filterParameters.ratingFrom
            pref[PreferencesKeys.RATING_TO_SEARCH_FILTER] = filterParameters.ratingTo
            pref[PreferencesKeys.YEAR_FROM_SEARCH_FILTER] = filterParameters.yearFrom
            pref[PreferencesKeys.YEAR_TO_SEARCH_FILTER] = filterParameters.yearTo
            pref[PreferencesKeys.MOVIE_WATCHED_STATE] = filterParameters.hideWatchedMovies
        }
    }

    val searchFilterParameters: Flow<FilterParameters> = context.dataStore.data
        .map { pref ->
            FilterParameters(
                countryId = pref[PreferencesKeys.COUNTRY_ID_SEARCH_FILTER]!!,
                genreId = pref[PreferencesKeys.GENRE_ID_SEARCH_FILTER]!!,
                country = pref[PreferencesKeys.COUNTRY_SEARCH_FILTER]!!,
                genre = pref[PreferencesKeys.GENRE_SEARCH_FILTER]!!,
                sorting = pref[PreferencesKeys.ORDER_SEARCH_FILTER]!!,
                type = pref[PreferencesKeys.TYPE_SEARCH_FILTER]!!,
                ratingFrom = pref[PreferencesKeys.RATING_FROM_SEARCH_FILTER]!!,
                ratingTo = pref[PreferencesKeys.RATING_TO_SEARCH_FILTER]!!,
                yearFrom = pref[PreferencesKeys.YEAR_FROM_SEARCH_FILTER]!!,
                yearTo = pref[PreferencesKeys.YEAR_TO_SEARCH_FILTER]!!,
                hideWatchedMovies = pref[PreferencesKeys.MOVIE_WATCHED_STATE]!!
            )
        }

}

