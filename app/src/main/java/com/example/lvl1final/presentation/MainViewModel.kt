package com.example.lvl1final.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.lvl1final.R
import com.example.lvl1final.domain.models.collection.CollectionMovie
import com.example.lvl1final.domain.models.collection.CollectionWithMovies
import com.example.lvl1final.domain.models.collection.InterestingMovie
import com.example.lvl1final.domain.models.collection.KinopoiskMovie
import com.example.lvl1final.domain.models.movie.FilterParameters
import com.example.lvl1final.domain.models.collection.MoviesCollection
import com.example.lvl1final.domain.models.collection.WatchedMovie
import com.example.lvl1final.domain.models.movieimpl.ActorImpl
import com.example.lvl1final.domain.models.movieimpl.CountryImpl
import com.example.lvl1final.domain.models.movie.FullMovieData
import com.example.lvl1final.domain.models.movieimpl.GenreImpl
import com.example.lvl1final.domain.models.movieimpl.ImageImpl
import com.example.lvl1final.domain.models.movieimpl.KinopoiskMovieSelectionFiltersImpl
import com.example.lvl1final.domain.models.movieimpl.MovieImpl
import com.example.lvl1final.domain.models.movieimpl.MovieStaffImpl
import com.example.lvl1final.domain.usecase.CreateNewCollectionUseCase
import com.example.lvl1final.domain.usecase.CheckMovieInWatchedCollectionUseCase
import com.example.lvl1final.domain.usecase.DeleteCollectionUseCase
import com.example.lvl1final.domain.usecase.DeleteMovieFromCollectionUseCase
import com.example.lvl1final.domain.usecase.DeleteMovieFromWatchedCollectionUseCase
import com.example.lvl1final.domain.usecase.GetActorInfoUseCase
import com.example.lvl1final.domain.usecase.GetCollectionIdUseCase
import com.example.lvl1final.domain.usecase.GetCollectionListUseCase
import com.example.lvl1final.domain.usecase.GetCollectionListWithThisMovieUseCase
import com.example.lvl1final.domain.usecase.GetCollectionWithMoviesUseCase
import com.example.lvl1final.domain.usecase.GetCountriesAndGenresUseCase
import com.example.lvl1final.domain.usecase.GetInterestingCollectionListUseCase
import com.example.lvl1final.domain.usecase.GetAllMovieImagesUseCase
import com.example.lvl1final.domain.usecase.GetDefaultSearchFilterParametersUseCase
import com.example.lvl1final.domain.usecase.GetMovieDataUseCase
import com.example.lvl1final.domain.usecase.GetMovieSelectionFiltersUseCase
import com.example.lvl1final.domain.usecase.GetPremieresUseCase
import com.example.lvl1final.domain.usecase.GetSelectionMoviesUseCase
import com.example.lvl1final.domain.usecase.GetSeriesUseCase
import com.example.lvl1final.domain.usecase.GetTopMoviesUseCase
import com.example.lvl1final.domain.usecase.GetWatchedCollectionListUseCase
import com.example.lvl1final.domain.usecase.InsertMovieToCollectionUseCase
import com.example.lvl1final.domain.usecase.InsertMovieToInterestingCollectionUseCase
import com.example.lvl1final.domain.usecase.InsertMovieToWatchedCollectionUseCase
import com.example.lvl1final.domain.usecase.SearchMovieUseCase
import com.example.lvl1final.presentation.common.MoviePagingSource
import com.example.lvl1final.presentation.search.MovieSearchPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val createNewCollectionUseCase: CreateNewCollectionUseCase,
    private val deleteCollectionUseCase: DeleteCollectionUseCase,
    private val getCollectionListWithThisMovieUseCase: GetCollectionListWithThisMovieUseCase,
    private val insertMovieToCollectionUseCase: InsertMovieToCollectionUseCase,
    private val insertMovieToWatchedCollectionUseCase: InsertMovieToWatchedCollectionUseCase,
    private val insertMovieToInterestingCollectionUseCase: InsertMovieToInterestingCollectionUseCase,
    private val deleteMovieFromCollectionUseCase: DeleteMovieFromCollectionUseCase,
    private val deleteMovieFromWatchedCollectionUseCase: DeleteMovieFromWatchedCollectionUseCase,
    private val getCollectionListUseCase: GetCollectionListUseCase,
    private val getWatchedCollectionListUseCase: GetWatchedCollectionListUseCase,
    private val getInterestingCollectionListUseCase: GetInterestingCollectionListUseCase,
    private val getCollectionIdUseCase: GetCollectionIdUseCase,
    private val getCollectionWithMoviesUseCase: GetCollectionWithMoviesUseCase,
    private val checkMovieInWatchedCollectionUseCase: CheckMovieInWatchedCollectionUseCase,
    private val searchMovieUseCase: SearchMovieUseCase,
    private val getActorInfoUseCase: GetActorInfoUseCase,
    private val getAllMovieImagesUseCase: GetAllMovieImagesUseCase,
    private val getSeriesUseCase: GetSeriesUseCase,
    private val getTopMoviesUseCase: GetTopMoviesUseCase,
    private val getSelectionMoviesUseCase: GetSelectionMoviesUseCase,
    private val getCountriesAndGenresUseCase: GetCountriesAndGenresUseCase,
    private val getMovieSelectionFiltersUseCase: GetMovieSelectionFiltersUseCase,
    private val getPremieresUseCase: GetPremieresUseCase,
    private val getMovieDataUseCase: GetMovieDataUseCase,
    private val getDefaultSearchFilterParametersUseCase: GetDefaultSearchFilterParametersUseCase
) : ViewModel() {

    private val _firstSelectionCountry = MutableStateFlow<CountryImpl?>(null)
    val firstSelectionCountry = _firstSelectionCountry.asStateFlow()

    private val _firstSelectionGenre = MutableStateFlow<GenreImpl?>(null)
    val firstSelectionGenre = _firstSelectionGenre.asStateFlow()

    private val _secondSelectionCountry = MutableStateFlow<CountryImpl?>(null)
    val secondSelectionCountry = _secondSelectionCountry.asStateFlow()

    private val _secondSelectionGenre = MutableStateFlow<GenreImpl?>(null)
    val secondSelectionGenre = _secondSelectionGenre.asStateFlow()

    private val _allMovieImages = MutableStateFlow<Map<String, List<ImageImpl>>>(emptyMap())
    val allMovieImages = _allMovieImages.asStateFlow()

    private val _moviePageImages = MutableStateFlow<List<ImageImpl>?>(null)
    val moviePageImages = _moviePageImages.asStateFlow()

    private val _movieData = MutableStateFlow<FullMovieData?>(null)
    val movieData = _movieData.asStateFlow()

    private val _firstSelectionMoviesFiltersLoaded = MutableStateFlow(false)
    val firstSelectionMoviesFiltersLoaded = _firstSelectionMoviesFiltersLoaded.asStateFlow()

    private val _secondSelectionMoviesFiltersLoaded = MutableStateFlow(false)
    val secondSelectionMoviesFiltersLoaded = _secondSelectionMoviesFiltersLoaded.asStateFlow()

    private val _premieres = MutableStateFlow<List<MovieImpl>>(emptyList())
    val premieres = _premieres.asStateFlow()

    private val _actorInfo = MutableStateFlow<ActorImpl?>(null)
    val actorInfo = _actorInfo.asStateFlow()

    private val _movieCrewList = MutableStateFlow<List<MovieStaffImpl>>(emptyList())
    val movieCrewList = _movieCrewList.asStateFlow()

    fun setMovieCrewList(list: List<MovieStaffImpl>) {
        _movieCrewList.value = list
    }

    val previewPagedPopularMovies: Flow<PagingData<MovieImpl>> = Pager(
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

    val pagedPopularMovies: Flow<PagingData<MovieImpl>> = Pager(
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


    val previewPagedTop250Movies: Flow<PagingData<MovieImpl>> = Pager(
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

    val pagedTop250Movies: Flow<PagingData<MovieImpl>> = Pager(
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
            val countryGenrePairList = getMovieSelectionFiltersUseCase()
            val firstCountryGenrePairList = countryGenrePairList[0]
            val secondCountryGenrePairList = countryGenrePairList[1]

            _firstSelectionCountry.value = firstCountryGenrePairList.first
            _firstSelectionGenre.value = firstCountryGenrePairList.second
            _firstSelectionMoviesFiltersLoaded.value = true

            _secondSelectionCountry.value = secondCountryGenrePairList.first
            _secondSelectionGenre.value = secondCountryGenrePairList.second
            _secondSelectionMoviesFiltersLoaded.value = true
        }
    }

    val previewFirstSelectionMovies: Flow<PagingData<MovieImpl>> = Pager(
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

    val firstSelectionMovies: Flow<PagingData<MovieImpl>> = Pager(
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

    val previewSecondSelectionMovies: Flow<PagingData<MovieImpl>> = Pager(
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

    val secondSelectionMovies: Flow<PagingData<MovieImpl>> = Pager(
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

    val previewPagedTvSeries: Flow<PagingData<MovieImpl>> = Pager(
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

    val pagedTvSeries: Flow<PagingData<MovieImpl>> = Pager(
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

    fun getAllImages(id: Int) {
        _allMovieImages.value = emptyMap()
        viewModelScope.launch {
            val allImages = getAllMovieImagesUseCase.invoke(id, Arguments.movieImagesTypes)
            _allMovieImages.value = allImages
        }
    }

    fun getMovieData(id: Int) {
        _movieData.value = null
        viewModelScope.launch {
            _movieData.value = getMovieDataUseCase(id = id, types = Arguments.movieImagesTypes)
        }
    }

    fun getActorInfo(id: Int) {
        viewModelScope.launch {
            _actorInfo.value = getActorInfoUseCase(id = id)
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

    private val _collectionUpdateChannel =
        Channel<Unit>(Channel.BUFFERED)
    val collectionUpdateChannel = _collectionUpdateChannel.receiveAsFlow()

    private val _createdCollectionId = MutableStateFlow<Int?>(null)
    val createdCollectionId = _createdCollectionId.asStateFlow()

    private val _countriesAndGenres = MutableStateFlow<KinopoiskMovieSelectionFiltersImpl?>(null)
    val countriesAndGenres = _countriesAndGenres.asStateFlow()

    private val _isEmptyList = MutableStateFlow(false)
    val isEmptyList = _isEmptyList.asStateFlow()

    private val currentFilterParameters = MutableStateFlow<FilterParameters?>(null)

    init {
        getPremieres()
        getSelectionFilters()
        currentFilterParameters.value = getDefaultSearchFilterParametersUseCase()
    }

    private val currentKeyword = MutableStateFlow("")

    //TODO
    var movieSearchFlow: Flow<PagingData<MovieImpl>> = searchMovie(
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

    fun getCollectionIdListWithMovie(kinopoiskId: Int) {
        viewModelScope.launch {
            _collectionIdListWithMovie.value =
                getCollectionListWithThisMovieUseCase.getCollectionIdListWithThisMovie(kinopoiskId)
        }
    }

    //Получить список всех коллекций
    val collectionList = getCollectionListUseCase.getOtherCollectionList()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Создать новую коллекцию
    fun createNewCollection(newCollectionName: String) {
        viewModelScope.launch {
            val defaultIcon = R.drawable.ic_user
            createNewCollectionUseCase.createNewCollection(newCollectionName, defaultIcon)
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
            _collectionUpdateChannel.send(Unit)
            setStatusOfTheMovieInTheCollection(collectionMovie.collectionId, true)
        }
    }

    fun deleteMovieFromCollection(collectionMovie: CollectionMovie) {
        viewModelScope.launch {
            deleteMovieFromCollectionUseCase.deleteMovieFromCollection(collectionMovie)
            _collectionUpdateChannel.send(Unit)
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

    //удаление id, т.к. при переоткрытии ExtraMenuBottomSheetFragment - происходило повторное
    // добавление в последнюю созданную коллекцию
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
    val watchedCollectionMovieList = getWatchedCollectionListUseCase.getWatchedMovieList()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Добавление фильма в "Просмотренные"
    fun insertMovieToWatchedCollection(
        kinopoiskMovie: KinopoiskMovie,
        watchedMovie: WatchedMovie
    ) {
        viewModelScope.launch {
            insertMovieToWatchedCollectionUseCase.insertMovieToWatchedCollection(
                kinopoiskMovie,
                watchedMovie
            )
            _movieInWatchedCollection.value = true
        }
    }

    //Удаление фильма из "Просмотренные"
    fun deleteMovieFromWatchedCollection(kinopoiskId: Int) {
        viewModelScope.launch {
            deleteMovieFromWatchedCollectionUseCase(kinopoiskId)
            _movieInWatchedCollection.value = false
        }
    }

    /* Интересующие */
    // Получение всего списка "Интересующие"
    val interestingCollectionMovieList =
        getInterestingCollectionListUseCase.getInterestingMovieList()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    // Добавление фильма в "Интересующие"
    fun insertMovieToInterestingCollection(
        kinopoiskMovie: KinopoiskMovie,
        interestingMovie: InterestingMovie
    ) {
        viewModelScope.launch {
            insertMovieToInterestingCollectionUseCase.insertMovieToInterestingCollection(
                kinopoiskMovie,
                interestingMovie
            )
        }
    }

    fun getCountriesAndGenres() {
        viewModelScope.launch {
            _countriesAndGenres.value = getCountriesAndGenresUseCase()
        }
    }

    private fun isEmptyList(isEmptyList: Boolean) {
        _isEmptyList.value = isEmptyList
    }

    private fun searchMovie(
        filterParametersProvider: () -> FilterParameters,
        keywordProvider: () -> String
    ): Flow<PagingData<MovieImpl>> {
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
