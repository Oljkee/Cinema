package com.example.lvl1final.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.lvl1final.R
import com.example.lvl1final.data.api.*
import com.example.lvl1final.domain.CreateNewCollectionUseCase
import com.example.lvl1final.data.entity.CollectionMovie
import com.example.lvl1final.data.entity.CollectionWithMovies
import com.example.lvl1final.data.entity.FilterParameters
import com.example.lvl1final.data.entity.InterestingMovie
import com.example.lvl1final.data.entity.KinopoiskMovie
import com.example.lvl1final.data.entity.MoviesCollection
import com.example.lvl1final.data.entity.WatchedMovie
import com.example.lvl1final.domain.*
import com.example.lvl1final.presentation.common.MoviePagingSource
import com.example.lvl1final.presentation.search.MovieSearchPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val createNewCollectionUseCase: CreateNewCollectionUseCase,
    private val deleteCollectionUseCase: DeleteCollectionUseCase,
    private val getCollectionListWithThisMovieUseCase: GetCollectionListWithThisMovieUseCase,
    private val insertMovieToCollectionUseCase: InsertMovieToCollectionUseCase,
    private val deleteMovieFromCollectionUseCase: DeleteMovieFromCollectionUseCase,
    private val getCollectionListUseCase: GetCollectionListUseCase,
    private val getCollectionIdUseCase: GetCollectionIdUseCase,
    private val getCollectionWithMoviesUseCase: GetCollectionWithMoviesUseCase,
    private val checkMovieInWatchedCollectionUseCase: CheckMovieInWatchedCollectionUseCase,
    private val searchMovieUseCase: SearchMovieUseCase,
    private val getActorInfoUseCase: GetActorInfoUseCase,
    private val getSeasonsAndEpisodesUseCase: GetSeasonsAndEpisodesUseCase,
    private val getSimilarMoviesUseCase: GetSimilarMoviesUseCase,
    private val getMovieImagesUseCase: GetMovieImagesUseCase,
    private val getMovieStaffInfoUseCase: GetMovieStaffInfoUseCase,
    private val getMovieInfoUseCase: GetMovieInfoUseCase,
    private val getSeriesUseCase: GetSeriesUseCase,
    private val getTopMoviesUseCase: GetTopMoviesUseCase,
    private val getSelectionMoviesUseCase: GetSelectionMoviesUseCase,
    private val getMovieSelectionFiltersUseCase: GetMovieSelectionFiltersUseCase,
    private val getPremieresUseCase: GetPremieresUseCase
) : ViewModel() {

    private val _firstSelectionCountry = MutableStateFlow<CountryDto?>(null)
    val firstSelectionCountry = _firstSelectionCountry.asStateFlow()

    private val _firstSelectionGenre = MutableStateFlow<GenreDto?>(null)
    val firstSelectionGenre = _firstSelectionGenre.asStateFlow()

    private val _secondSelectionCountry = MutableStateFlow<CountryDto?>(null)
    val secondSelectionCountry = _secondSelectionCountry.asStateFlow()

    private val _secondSelectionGenre = MutableStateFlow<GenreDto?>(null)
    val secondSelectionGenre = _secondSelectionGenre.asStateFlow()

    private val _movieStillImages = MutableStateFlow<List<ImageDto>>(emptyList())
    val movieStillImages = _movieStillImages.asStateFlow()

    private val _movieShootingImages = MutableStateFlow<List<ImageDto>>(emptyList())
    val movieShootingImages = _movieShootingImages.asStateFlow()

    private val _moviePosterImages = MutableStateFlow<List<ImageDto>>(emptyList())
    val moviePosterImages = _moviePosterImages.asStateFlow()

    private val _movieFanArtImages = MutableStateFlow<List<ImageDto>>(emptyList())
    val movieFanArtImages = _movieFanArtImages.asStateFlow()

    private val _moviePromoImages = MutableStateFlow<List<ImageDto>>(emptyList())
    val moviePromoImages = _moviePromoImages.asStateFlow()

    private val _movieConceptImages = MutableStateFlow<List<ImageDto>>(emptyList())
    val movieConceptImages = _movieConceptImages.asStateFlow()

    private val _movieWallpaperImages = MutableStateFlow<List<ImageDto>>(emptyList())
    val movieWallpaperImages = _movieWallpaperImages.asStateFlow()

    private val _movieCoverImages = MutableStateFlow<List<ImageDto>>(emptyList())
    val movieCoverImages = _movieCoverImages.asStateFlow()

    private val _movieScreenshotImages = MutableStateFlow<List<ImageDto>>(emptyList())
    val movieScreenshotImages = _movieScreenshotImages.asStateFlow()

    private val _movieImagesTypeIsNotNull = MutableStateFlow<List<ImageDto>?>(null)
    val movieImagesTypeIsNotNull = _movieImagesTypeIsNotNull.asStateFlow()

    private val _firstSelectionMoviesFiltersLoaded = MutableStateFlow(false)
    val firstSelectionMoviesFiltersLoaded = _firstSelectionMoviesFiltersLoaded.asStateFlow()

    private val _secondSelectionMoviesFiltersLoaded = MutableStateFlow(false)
    val secondSelectionMoviesFiltersLoaded = _secondSelectionMoviesFiltersLoaded.asStateFlow()

    private val _premieres = MutableStateFlow<List<MovieDto>>(emptyList())
    val premieres = _premieres.asStateFlow()

    private val _movieInfo = MutableStateFlow<KinopoiskMovieInfoDto?>(null)
    val movieInfo = _movieInfo.asStateFlow()

    private val _movieStaffInfo = MutableStateFlow<List<MovieStaffDto>?>(null)
    val movieStaffInfo = _movieStaffInfo.asStateFlow()

    private val _similarMovies = MutableStateFlow<List<SimilarMoviesItemDto>>(emptyList())
    val similarMovies = _similarMovies.asStateFlow()

    private val _seasonsAndEpisodes = MutableStateFlow<SeasonsAndEpisodesDto?>(null)
    val seasonsAndEpisodes = _seasonsAndEpisodes.asStateFlow()

    private val _actorInfo = MutableStateFlow<ActorInfoDto?>(null)
    val actorInfo = _actorInfo.asStateFlow()

    private val _movieCrewList = MutableStateFlow<List<MovieStaffDto>>(emptyList())
    val movieCrewList = _movieCrewList.asStateFlow()

    fun setMovieCrewList(list: List<MovieStaffDto>) {
        _movieCrewList.value = list
    }

    val previewPagedPopularMovies: Flow<PagingData<MovieDto>> = Pager(
        config = PagingConfig(pageSize = 5, enablePlaceholders = false, initialLoadSize = 20),
        pagingSourceFactory = {
            MoviePagingSource(
                getSeriesUseCase = getSeriesUseCase,
                getTopMoviesUseCase = getTopMoviesUseCase,
                getSelectionMoviesUseCase = getSelectionMoviesUseCase,
                listType = Arguments.TOP_POPULAR,
                listPreview = true
            )
        }
    ).flow.cachedIn(viewModelScope)

    val pagedPopularMovies: Flow<PagingData<MovieDto>> = Pager(
        config = PagingConfig(pageSize = 5, enablePlaceholders = true, initialLoadSize = 20),
        pagingSourceFactory = {
            MoviePagingSource(
                getSeriesUseCase = getSeriesUseCase,
                getTopMoviesUseCase = getTopMoviesUseCase,
                getSelectionMoviesUseCase = getSelectionMoviesUseCase,
                listType = Arguments.TOP_POPULAR
            )
        }
    ).flow.cachedIn(viewModelScope)


    val previewPagedTop250Movies: Flow<PagingData<MovieDto>> = Pager(
        config = PagingConfig(pageSize = 5, enablePlaceholders = true, initialLoadSize = 20),
        pagingSourceFactory = {
            MoviePagingSource(
                getSeriesUseCase = getSeriesUseCase,
                getTopMoviesUseCase = getTopMoviesUseCase,
                getSelectionMoviesUseCase = getSelectionMoviesUseCase,
                listType = Arguments.TOP_250,
                listPreview = true
            )
        }
    ).flow.cachedIn(viewModelScope)

    val pagedTop250Movies: Flow<PagingData<MovieDto>> = Pager(
        config = PagingConfig(pageSize = 5, enablePlaceholders = true, initialLoadSize = 20),
        pagingSourceFactory = {
            MoviePagingSource(
                getSeriesUseCase = getSeriesUseCase,
                getTopMoviesUseCase = getTopMoviesUseCase,
                getSelectionMoviesUseCase = getSelectionMoviesUseCase,
                listType = Arguments.TOP_250
            )
        }
    ).flow.cachedIn(viewModelScope)

    private fun getPremieres() {
        viewModelScope.launch {
            _premieres.value = getPremieresUseCase()
        }
    }

    private fun getSelectionFilters() {
        viewModelScope.launch {
            val countriesAndGenres = getMovieSelectionFiltersUseCase()
            val countryFullList = countriesAndGenres.countries.shuffled()
            val genreFullList = countriesAndGenres.genres.shuffled()

            viewModelScope.launch {
                val countries = countryFullList.take(countryFullList.size / 2)
                val genres = genreFullList.take(genreFullList.size / 2)
                var kinopoiskSelectionMoviesDto: KinopoiskSelectionMoviesDto
                var index = 0
                do {
                    val country = countries[index]
                    val genre = genres[index]
                    kinopoiskSelectionMoviesDto = getSelectionMoviesUseCase(
                        countries = country.id!!,
                        genres = genre.id!!,
                        page = 1
                    )

                    if (kinopoiskSelectionMoviesDto.total > 0) {
                        _firstSelectionCountry.value = countries[index]
                        _firstSelectionGenre.value = genres[index]
                        break
                    }

                    index++
                } while (true)
                _firstSelectionMoviesFiltersLoaded.value = true
            }

            viewModelScope.launch {
                val countries = countryFullList.takeLast(countryFullList.size / 2)
                val genres = genreFullList.takeLast(genreFullList.size / 2)
                var kinopoiskSelectionMoviesDto: KinopoiskSelectionMoviesDto
                var index = 0
                do {
                    val country = countries[index]
                    val genre = genres[index]

                    kinopoiskSelectionMoviesDto = getSelectionMoviesUseCase(
                        countries = country.id!!,
                        genres = genre.id!!,
                        page = 1
                    )

                    if (kinopoiskSelectionMoviesDto.total > 0) {
                        _secondSelectionCountry.value = countries[index]
                        _secondSelectionGenre.value = genres[index]
                        break
                    }

                    index++
                } while (true)
                _secondSelectionMoviesFiltersLoaded.value = true
            }
        }
    }

    val previewFirstSelectionMovies: Flow<PagingData<MovieDto>> = Pager(
        config = PagingConfig(pageSize = 5, enablePlaceholders = true, initialLoadSize = 20),
        pagingSourceFactory = {
            MoviePagingSource(
                getSeriesUseCase = getSeriesUseCase,
                getTopMoviesUseCase = getTopMoviesUseCase,
                getSelectionMoviesUseCase = getSelectionMoviesUseCase,
                countries = firstSelectionCountry.value,
                genres = firstSelectionGenre.value,
                listType = Arguments.ARG_FILM_TYPE,
                listPreview = true
            )
        }
    ).flow.cachedIn(viewModelScope)

    val firstSelectionMovies: Flow<PagingData<MovieDto>> = Pager(
        config = PagingConfig(pageSize = 5, enablePlaceholders = true, initialLoadSize = 20),
        pagingSourceFactory = {
            MoviePagingSource(
                getSeriesUseCase = getSeriesUseCase,
                getTopMoviesUseCase = getTopMoviesUseCase,
                getSelectionMoviesUseCase = getSelectionMoviesUseCase,
                countries = firstSelectionCountry.value,
                genres = firstSelectionGenre.value,
                listType = Arguments.ARG_FILM_TYPE
            )
        }
    ).flow.cachedIn(viewModelScope)

    val previewSecondSelectionMovies: Flow<PagingData<MovieDto>> = Pager(
        config = PagingConfig(pageSize = 5, enablePlaceholders = true, initialLoadSize = 20),
        pagingSourceFactory = {
            MoviePagingSource(
                getSeriesUseCase = getSeriesUseCase,
                getTopMoviesUseCase = getTopMoviesUseCase,
                getSelectionMoviesUseCase = getSelectionMoviesUseCase,
                countries = secondSelectionCountry.value,
                genres = secondSelectionGenre.value,
                listType = Arguments.ARG_FILM_TYPE,
                listPreview = true
            )
        }
    ).flow.cachedIn(viewModelScope)

    val secondSelectionMovies: Flow<PagingData<MovieDto>> = Pager(
        config = PagingConfig(pageSize = 5, enablePlaceholders = true, initialLoadSize = 20),
        pagingSourceFactory = {
            MoviePagingSource(
                getSeriesUseCase = getSeriesUseCase,
                getTopMoviesUseCase = getTopMoviesUseCase,
                getSelectionMoviesUseCase = getSelectionMoviesUseCase,
                countries = secondSelectionCountry.value,
                genres = secondSelectionGenre.value,
                listType = Arguments.ARG_FILM_TYPE
            )
        }
    ).flow.cachedIn(viewModelScope)

    val previewPagedTvSeries: Flow<PagingData<MovieDto>> = Pager(
        config = PagingConfig(pageSize = 5, enablePlaceholders = true, initialLoadSize = 20),
        pagingSourceFactory = {
            MoviePagingSource(
                getSeriesUseCase = getSeriesUseCase,
                getTopMoviesUseCase = getTopMoviesUseCase,
                getSelectionMoviesUseCase = getSelectionMoviesUseCase,
                listType = Arguments.ARG_SERIES_TYPE,
                listPreview = true
            )
        }
    ).flow.cachedIn(viewModelScope)

    val pagedTvSeries: Flow<PagingData<MovieDto>> = Pager(
        config = PagingConfig(pageSize = 5, enablePlaceholders = true, initialLoadSize = 20),
        pagingSourceFactory = {
            MoviePagingSource(
                getSeriesUseCase = getSeriesUseCase,
                getTopMoviesUseCase = getTopMoviesUseCase,
                getSelectionMoviesUseCase = getSelectionMoviesUseCase,
                listType = Arguments.ARG_SERIES_TYPE
            )
        }
    ).flow.cachedIn(viewModelScope)

    private fun getMovieInfo(kinopoiskId: Int) {
        viewModelScope.launch {
            _movieInfo.value = getMovieInfoUseCase(kinopoiskId)
        }
    }

    private fun getMovieStaffInfo(filmId: Int) {
        viewModelScope.launch {
            _movieStaffInfo.value = getMovieStaffInfoUseCase(filmId)
        }
    }

    private fun getSimilarMovies(id: Int) {
        viewModelScope.launch {
            _similarMovies.value = getSimilarMoviesUseCase(id = id)
        }
    }

    private fun getMovieStillImages(id: Int) =
        setValue(_movieStillImages, id, Arguments.MOVIE_IMAGES_TYPE_STILL)

    private fun getMovieShootingImages(id: Int) =
        setValue(_movieShootingImages, id, Arguments.MOVIE_IMAGES_TYPE_SHOOTING)

    private fun getMoviePosterImages(id: Int) =
        setValue(_moviePosterImages, id, Arguments.MOVIE_IMAGES_TYPE_POSTER)

    private fun getMovieFanArtImages(id: Int) =
        setValue(_movieFanArtImages, id, Arguments.MOVIE_IMAGES_TYPE_FAN_ART)

    private fun getMoviePromoImages(id: Int) =
        setValue(_moviePromoImages, id, Arguments.MOVIE_IMAGES_TYPE_PROMO)

    private fun getMovieConceptImages(id: Int) =
        setValue(_movieConceptImages, id, Arguments.MOVIE_IMAGES_TYPE_CONCEPT)

    private fun getMovieWallpaperImages(id: Int) =
        setValue(_movieWallpaperImages, id, Arguments.MOVIE_IMAGES_TYPE_WALLPAPER)

    private fun getMovieCoverImages(id: Int) =
        setValue(_movieCoverImages, id, Arguments.MOVIE_IMAGES_TYPE_COVER)

    private fun getMovieScreenshotImages(id: Int) =
        setValue(_movieScreenshotImages, id, Arguments.MOVIE_IMAGES_TYPE_SCREENSHOT)

    private val imageFunctions = listOf(
        ::getMovieStillImages,
        ::getMovieShootingImages,
        ::getMoviePosterImages,
        ::getMovieFanArtImages,
        ::getMoviePromoImages,
        ::getMovieConceptImages,
        ::getMovieWallpaperImages,
        ::getMovieCoverImages,
        ::getMovieScreenshotImages
    )

    fun getAllImages(id: Int) {
        for (imageFunction in imageFunctions) {
            imageFunction(id)
        }
    }

    private fun getFirstNonEmptyImages(id: Int) {
        _movieImagesTypeIsNotNull.value = null
        for (imageFunction in imageFunctions) {
            if (movieImagesTypeIsNotNull.value != null) {
                break
            } else {
                imageFunction(id)
            }
        }
    }

    fun getMovieData(id: Int) {
        getMovieInfo(id)
        getMovieStaffInfo(id)
        getSimilarMovies(id)
        getFirstNonEmptyImages(id)
        getSeasonsAndEpisodes(id)
    }

    private fun getSeasonsAndEpisodes(id: Int) {
        viewModelScope.launch {
            _seasonsAndEpisodes.value = getSeasonsAndEpisodesUseCase(id = id)
        }
    }

    fun getActorInfo(id: Int) {
        viewModelScope.launch {
            _actorInfo.value = getActorInfoUseCase(id = id)
        }
    }

    private fun setValue(
        mutableStateFlow: MutableStateFlow<List<ImageDto>>,
        id: Int,
        type: String
    ) {
        viewModelScope.launch {
            val list = getMovieImagesUseCase(id, type)
            if (_movieImagesTypeIsNotNull.value == null) {
                if (list.isNotEmpty()) {
                    _movieImagesTypeIsNotNull.value = list
                }
            }
            mutableStateFlow.value = list
        }
    }


    // database
    private val _movieInInterestingCollection = MutableStateFlow(false)
    val movieInInterestingCollection = _movieInInterestingCollection.asStateFlow()

    private val _movieInWatchedCollection = MutableStateFlow(false)
    val movieInWatchedCollection = _movieInWatchedCollection.asStateFlow()

    private val _movieInFavoriteCollection = MutableStateFlow(false)
    val movieInFavoriteCollection = _movieInFavoriteCollection.asStateFlow()

    private val _movieInDelayedCollection = MutableStateFlow(false)
    val movieInDelayedCollection = _movieInDelayedCollection.asStateFlow()

    private val _favoriteCollectionMovieList =
        MutableStateFlow<CollectionWithMovies?>(null)
    val favoriteCollectionMovieList = _favoriteCollectionMovieList.asStateFlow()

    private val _delayedCollectionMovieList =
        MutableStateFlow<CollectionWithMovies?>(null)
    val delayedCollectionMovieList = _delayedCollectionMovieList.asStateFlow()

    private val _userCollectionMovieList =
        MutableStateFlow<CollectionWithMovies?>(null)
    val userCollectionMovieList = _userCollectionMovieList.asStateFlow()

    private val _collectionIdListWithMovie = MutableStateFlow<List<Int>?>(null)
    val collectionIdListWithMovie = _collectionIdListWithMovie.asStateFlow()

    private val _collectionsUpdateState =
        MutableStateFlow(false)
    val collectionsUpdateState = _collectionsUpdateState.asStateFlow()

    private val _createdCollectionId = MutableStateFlow<Int?>(null)
    val createdCollectionId = _createdCollectionId.asStateFlow()

    private val _countriesAndGenres = MutableStateFlow<KinopoiskMovieSelectionFiltersDto?>(null)
    val countriesAndGenres = _countriesAndGenres.asStateFlow()

    private val _isEmptyList = MutableStateFlow(false)
    val isEmptyList = _isEmptyList.asStateFlow()

    private val currentFilterParameters = MutableStateFlow<FilterParameters?>(null)

    init {

    }

    init {
        getPremieres()
        getSelectionFilters()
        currentFilterParameters.value = FilterParameters(
            countryId = 54,
            genreId = 25,
            country = Arguments.ALL_COUNTRY,
            genre = Arguments.ALL_GENRE,
            sorting = Arguments.SORT_BY_RATING_FILTER,
            type = Arguments.ARG_ALL_TYPE,
            ratingFrom = "6",
            ratingTo = "10",
            yearFrom = "1999",
            yearTo = "2023",
            hideWatchedMovies = true
        )
    }

    private val currentKeyword = MutableStateFlow("")

    var movieSearchFlow: Flow<PagingData<MovieDto>> = searchMovie(
        filterParametersProvider = { currentFilterParameters.value!! },
        keywordProvider = { currentKeyword.value }
    )

    private var moviePagingSource: MovieSearchPagingSource? = null

    fun getCollectionsWithMovie(kinopoiskId: Int) {
        viewModelScope.launch {
            _movieInFavoriteCollection.value = false
            _movieInDelayedCollection.value = false

            getCollectionListWithThisMovieUseCase.getCollectionIdListWithThisMovie(kinopoiskId)
                ?.forEach { id ->
                    when (id) {
                        Arguments.FAVORITE_COLLECTION_ID -> _movieInFavoriteCollection.value = true
                        Arguments.DELAYED_COLLECTION_ID -> _movieInDelayedCollection.value = true
                    }
                }
        }
    }

    fun clearCollectionIdListWithMovie() {
        _collectionIdListWithMovie.value = null
    }

    fun getCollectionIdListWithMovie(kinopoiskId: Int) {
        viewModelScope.launch {
            _collectionIdListWithMovie.value =
                getCollectionListWithThisMovieUseCase.getCollectionIdListWithThisMovie(kinopoiskId)
        }
    }

    //Получить список всех коллекций
    val collectionList = getCollectionListUseCase.getCollectionList()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Создать новую коллекцию
    fun createNewCollection(newCollectionName: String) {
        viewModelScope.launch {
            val newCollection = MoviesCollection(
                collectionName = newCollectionName,
                collectionIcon = R.drawable.ic_user,
                numberOfMovies = 0
            )
            createNewCollectionUseCase.createNewCollection(newCollection)
            _createdCollectionId.value = getCollectionIdUseCase(newCollectionName)
        }
    }

    fun deleteCollection(collection: MoviesCollection) {
        viewModelScope.launch {
            deleteCollectionUseCase.deleteCollection(collection)
        }
    }

    //Получить список фильмов конкретной коллекции
    fun getTheCollectionMovies(collectionId: Int) {
        viewModelScope.launch {
            val collectionWithMovies = getCollectionWithMoviesUseCase(collectionId)
            when (collectionId) {
                Arguments.FAVORITE_COLLECTION_ID -> _favoriteCollectionMovieList.value =
                    collectionWithMovies

                Arguments.DELAYED_COLLECTION_ID -> _delayedCollectionMovieList.value =
                    collectionWithMovies

                else -> _userCollectionMovieList.value = collectionWithMovies
            }
        }
    }

    fun insertMovieToCollection(
        kinopoiskMovie: KinopoiskMovie,
        collectionMovie: CollectionMovie
    ) {
        viewModelScope.launch {
            insertMovieToCollectionUseCase.insertMovieToCollection(kinopoiskMovie, collectionMovie)
            _collectionsUpdateState.value = !_collectionsUpdateState.value
            setStatusOfTheMovieInTheCollection(collectionMovie.collectionId, true)
        }
    }

    fun deleteMovieFromCollection(collectionMovie: CollectionMovie) {
        viewModelScope.launch {
            deleteMovieFromCollectionUseCase.deleteMovieFromCollection(collectionMovie)
            _collectionsUpdateState.value = !_collectionsUpdateState.value
            setStatusOfTheMovieInTheCollection(collectionMovie.collectionId, false)
        }
    }

    private fun setStatusOfTheMovieInTheCollection(
        collectionId: Int,
        isMovieInCollection: Boolean
    ) {
        when (collectionId) {
            Arguments.FAVORITE_COLLECTION_ID -> _movieInFavoriteCollection.value =
                isMovieInCollection

            Arguments.DELAYED_COLLECTION_ID -> _movieInDelayedCollection.value = isMovieInCollection
        }
    }

    fun clearCreatedCollectionId() {
        _createdCollectionId.value = null
    }

    /* Просмотренные */
    //Проверка фильма в списке "Просмотренные"
    suspend fun isMovieInWatchedCollection(kinopoiskId: Int) {
        _movieInWatchedCollection.value =
            checkMovieInWatchedCollectionUseCase.isMovieInWatchedCollection(kinopoiskId) != null
    }

    // Получение всего списка "Просмотренные"
    val watchedCollectionMovieList = getCollectionListUseCase.getWatchedMovieList()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Добавление фильма в "Просмотренные"
    suspend fun insertMovieToWatchedCollection(
        kinopoiskMovie: KinopoiskMovie,
        watchedMovie: WatchedMovie
    ) {
        insertMovieToCollectionUseCase.insertMovieToWatchedCollection(kinopoiskMovie, watchedMovie)
        _movieInWatchedCollection.value = true
    }

    //Удаление фильма из "Просмотренные"
    suspend fun deleteMovieFromWatchedCollection(kinopoiskId: Int) {
        val watchedMovie = checkMovieInWatchedCollectionUseCase.isMovieInWatchedCollection(kinopoiskId)
        if (watchedMovie != null) {
            deleteMovieFromCollectionUseCase.deleteMovieFromWatchedCollection(watchedMovie)
            _movieInWatchedCollection.value = false
        }
    }

    /* Интересующие */
    // Получение всего списка "Интересующие"
    val interestingCollectionMovieList = getCollectionListUseCase.getInterestingMovieList()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Добавление фильма в "Интересующие"
    suspend fun insertMovieToInterestingCollection(
        kinopoiskMovie: KinopoiskMovie,
        interestingMovie: InterestingMovie
    ) {
        insertMovieToCollectionUseCase.insertMovieToInterestingCollection(
            kinopoiskMovie,
            interestingMovie
        )
    }

    fun getCountriesAndGenres() {
        viewModelScope.launch {
            _countriesAndGenres.value = getMovieSelectionFiltersUseCase()
        }
    }

    private fun isEmptyList(isEmptyList: Boolean) {
        _isEmptyList.value = isEmptyList
    }

    private fun searchMovie(
        filterParametersProvider: () -> FilterParameters,
        keywordProvider: () -> String
    ): Flow<PagingData<MovieDto>> {
        val pagingSourceFactory = {
            moviePagingSource = MovieSearchPagingSource(
                searchMovieUseCase = searchMovieUseCase,
                filterParameters = filterParametersProvider(),
                keyword = keywordProvider(),
                isEmptyList = { isEmpty -> isEmptyList(isEmpty) },
                watchedMovieList = watchedCollectionMovieList.value
            )
            moviePagingSource!!
        }

        return Pager(
            config = PagingConfig(5),
            pagingSourceFactory = pagingSourceFactory
        ).flow.cachedIn(viewModelScope)
    }

    fun updateSearchFilterParameters(filterParameters: FilterParameters) {
        currentFilterParameters.value = filterParameters
    }

    fun updateSearchKeyword(keyword: String) {
        currentKeyword.value = keyword
        moviePagingSource?.invalidate()
    }

}
