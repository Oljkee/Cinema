package com.example.lvl1final.presentation.common

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.lvl1final.R
import com.example.lvl1final.databinding.FragmentFilmPageBinding
import com.example.lvl1final.domain.models.collection.CollectionMovie
import com.example.lvl1final.domain.models.collection.InterestingMovie
import com.example.lvl1final.domain.models.collection.KinopoiskMovie
import com.example.lvl1final.domain.models.collection.WatchedMovie
import com.example.lvl1final.domain.models.collection.WatchedMovieWithKinopoiskMovie
import com.example.lvl1final.domain.models.movieimpl.ImageImpl
import com.example.lvl1final.domain.models.movie.KinopoiskMovieInfo
import com.example.lvl1final.domain.models.movieimpl.MovieStaffImpl
import com.example.lvl1final.domain.models.movie.SimilarMoviesItem
import com.example.lvl1final.presentation.Arguments
import com.example.lvl1final.presentation.MainViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FilmPageFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentFilmPageBinding? = null
    private val binding get() = _binding!!
    private var watchedMovieList = emptyList<WatchedMovieWithKinopoiskMovie?>()
    private val movieImagesPagedListAdapter =
        MovieImagesPagedListAdapter { image -> onGalleryItemClick(image) }
    private val similarMovieListAdapter = SimilarMovieListAdapter(
        onMovieItemClick = { movie -> onMovieItemClick(movie) },
        isWatchedMovie = { id -> isWatchedMovie(id) }
    )
    private val movieActorListAdapter = MovieStaffListAdapter { staff -> onActorItemClick(staff) }
    private val movieCrewListAdapter = MovieStaffListAdapter { staff -> onActorItemClick(staff) }
    private val bundle = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmPageBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var id = arguments?.getInt(Arguments.ARG_KINOPOISK_ID)

        binding.apply {
            imgBackButton.setOnClickListener {
                findNavController().popBackStack()
            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.watchedCollectionMovieList.collect { list ->
                    if (list.isNotEmpty()) {
                        watchedMovieList = list
                    }
                }
            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.movieInFavoriteCollection.collect { isMovieInCollection ->
                    if (isMovieInCollection) imgLikeButton.setImageResource(R.drawable.ic_heart_off)
                    else imgLikeButton.setImageResource(R.drawable.ic_heart)
                }
            }
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.movieInWatchedCollection.collect { isMovieInCollection ->
                    if (isMovieInCollection) imgEyeButton.setImageResource(R.drawable.ic_eye_closed)
                    else imgEyeButton.setImageResource(R.drawable.ic_eye)
                }
            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.movieInDelayedCollection.collect { isMovieInCollection ->
                    if (isMovieInCollection) imgBookmarkButton.setImageResource(R.drawable.ic_bookmark_selected)
                    else imgBookmarkButton.setImageResource(R.drawable.ic_bookmark)
                }
            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.movieData.collect { fullMovieData ->
                    fullMovieData?.movieInfo?.apply {
                        id = this.kinopoiskId
                        viewModel.isMovieInWatchedCollection(this.kinopoiskId)
                        addToCollection(
                            viewModel.movieInInterestingCollection,
                            this,
                            Arguments.INTERESTING_COLLECTION
                        )


                        imgLikeButton.setOnClickListener {
                            isMovieInCollectionAfterClick(
                                viewModel.movieInFavoriteCollection,
                                this,
                                Arguments.FAVORITE_COLLECTION_ID
                            )
                        }

                        imgEyeButton.setOnClickListener {
                            addToCollection(
                                viewModel.movieInWatchedCollection,
                                this,
                                Arguments.WATCHED_COLLECTION
                            )
                        }

                        imgShareButton.setOnClickListener {
                            val id = imdbId
                            if (!id.isNullOrEmpty()) {
                                shareMovie(id)
                            }
                        }

                        imgBookmarkButton.setOnClickListener {
                            isMovieInCollectionAfterClick(
                                viewModel.movieInDelayedCollection,
                                this,
                                Arguments.DELAYED_COLLECTION_ID
                            )
                        }

                        imgExtraMenuButton.setOnClickListener {
                            findNavController().navigate(
                                R.id.action_filmPageFragment_to_extraMenuBottomSheetFragment
                            )
                        }

                        viewModel.getCollectionsWithMovie(kinopoiskId)

                        Glide.with(root.context)
                            .load(this.logoUrl)
                            .into(imgFilmLogo)

                        Glide.with(root.context)
                            .load(this.posterUrl)
                            .into(imgFilmPoster)

                        if (ratingKinopoisk != null) {
                            textViewRaitingKinopoisk.text = ratingKinopoisk.toString()
                        } else {
                            textViewRaitingKinopoisk.visibility = View.GONE
                        }

                        textViewFilmName.text = nameRu ?: (nameOriginal ?: nameEn)

                        val genre: String = genres.joinToString(separator = ", ") { genre ->
                            genre.genre
                        }
                        textViewYearGenres.text =
                            if (year != null) "$year, $genre"
                            else genre

                        var countryLengthAgeResult = ""

                        val country: String =
                            countries.joinToString(separator = ", ") { country ->
                                country.country
                            }
                        countryLengthAgeResult += country

                        filmLength?.let { length ->
                            if (countryLengthAgeResult.isNotEmpty()) {
                                countryLengthAgeResult += ", "
                            }
                            countryLengthAgeResult += formatMinutesToHoursAndMinutes(length)

                        }
                        val limits = ratingAgeLimits
                        if (!limits.isNullOrEmpty()) {
                            val regex = Regex("\\d+")
                            val ageLimit = "${regex.find(limits)!!.value}+"
                            if (countryLengthAgeResult.isNotEmpty()) {
                                countryLengthAgeResult += ", "
                            }
                            countryLengthAgeResult += ageLimit
                        }

                        textViewCountryLengthAge.text = countryLengthAgeResult

                        if (shortDescription == null) textViewShortDescription.visibility =
                            View.GONE
                        else {
                            textViewShortDescription.visibility = View.VISIBLE
                            textViewShortDescription.text = shortDescription
                            textViewShortDescription.setOnClickListener {
                                if (textViewShortDescription.maxLines == Int.MAX_VALUE)
                                    textViewShortDescription.maxLines = 4
                                else textViewShortDescription.maxLines = Int.MAX_VALUE
                            }

                        }

                        if (description == null) textViewDescription.visibility = View.GONE
                        else {
                            textViewDescription.visibility = View.VISIBLE
                            textViewDescription.text = description
                            textViewDescription.setOnClickListener {
                                if (textViewDescription.maxLines == Int.MAX_VALUE)
                                    textViewDescription.maxLines = 5
                                else textViewDescription.maxLines = Int.MAX_VALUE
                            }
                        }
                    }

                    // Сезоны и серии
                    fullMovieData?.seasonsAndEpisodes.apply {
                        if (this == null || this.items.isEmpty()) {
                            layoutSeasonsEpisodes.visibility = View.GONE
                            textViewNumberOfSeasonsEpisodes.visibility = View.GONE
                        } else {
                            val seasonsCount = this.total
                            var episodesCount = 0
                            this.items.forEach {
                                episodesCount += it.episodes.size
                            }
                            layoutSeasonsEpisodes.visibility = View.VISIBLE
                            textViewNumberOfSeasonsEpisodes.visibility = View.VISIBLE
                            val numberOfEpisodesName = getNumberOfEpisodesName(episodesCount)
                            val numberOfSeasonsName = getNumberOfSeasonsName(seasonsCount)

                            textViewNumberOfSeasonsEpisodes.text = resources.getString(
                                R.string.number_of_seasons_and_episodes,
                                seasonsCount,
                                numberOfSeasonsName,
                                episodesCount,
                                numberOfEpisodesName
                            )
                            textViewAllSeasonsEpisodes.setOnClickListener {
                                findNavController().navigate(R.id.action_filmPageFragment_to_seasonsAndEpisodesFragment)
                            }
                        }
                    }

                    // В фильме снимались
                    // Над фильмом работали
                    recyclerViewActors.adapter = movieActorListAdapter
                    recyclerViewMovieCrew.adapter = movieCrewListAdapter
                    fullMovieData?.staffInfo.apply {
                        if (!this.isNullOrEmpty()) {
                            val actors = mutableListOf<MovieStaffImpl>()
                            val movieCrew = mutableListOf<MovieStaffImpl>()

                            this.forEach { staffMember ->
                                if (staffMember.professionKey == Arguments.MOVIE_STAFF_PROFESSION_ACTOR) {
                                    actors.add(staffMember)
                                } else {
                                    movieCrew.add(staffMember)
                                }
                            }
                            val actorResultList = actors.toList()

                            if (actorResultList.isEmpty()) {
                                layoutActors.visibility = View.GONE
                                recyclerViewActors.visibility = View.GONE
                            } else {
                                layoutActors.visibility = View.VISIBLE
                                recyclerViewActors.visibility = View.VISIBLE
                                val actorListDisplaySize = if (actorResultList.size > 20) {
                                    allActors.text = actorResultList.size.toString()
                                    allActors.setOnClickListener {
                                        viewModel.setMovieCrewList(actorResultList)
                                        bundle.putString(
                                            Arguments.MOVIE_CREW_TYPE_NAME,
                                            getString(R.string.actors)
                                        )
                                        findNavController().navigate(
                                            R.id.action_filmPageFragment_to_movieCrewListFragment,
                                            bundle
                                        )
                                    }
                                    20
                                } else {
                                    allActors.visibility = View.GONE
                                    actorResultList.size
                                }
                                movieActorListAdapter.submitList(
                                    actorResultList.subList(
                                        0,
                                        actorListDisplaySize
                                    )
                                )
                            }


                            val movieCrewResultList = movieCrew.toList()
                            if (movieCrewResultList.isEmpty()) {
                                layoutFilmCrew.visibility = View.GONE
                                recyclerViewMovieCrew.visibility = View.GONE
                            } else {
                                layoutFilmCrew.visibility = View.VISIBLE
                                recyclerViewMovieCrew.visibility = View.VISIBLE
                                val movieCrewListDisplaySize = if (movieCrewResultList.size > 6) {
                                    allFilmCrew.text = movieCrewResultList.size.toString()
                                    allFilmCrew.setOnClickListener {
                                        viewModel.setMovieCrewList(movieCrewResultList)
                                        bundle.putString(
                                            Arguments.MOVIE_CREW_TYPE_NAME,
                                            getString(R.string.film_crew)
                                        )
                                        findNavController().navigate(
                                            R.id.action_filmPageFragment_to_movieCrewListFragment,
                                            bundle
                                        )
                                    }
                                    6
                                } else {
                                    allFilmCrew.visibility = View.GONE
                                    movieCrewResultList.size
                                }
                                movieCrewListAdapter.submitList(
                                    movieCrewResultList.subList(
                                        0,
                                        movieCrewListDisplaySize
                                    )
                                )
                            }
                        }
                    }

                    // Галерея
                    recyclerViewGallery.adapter = movieImagesPagedListAdapter
                    fullMovieData?.movieImages.apply {
                        movieImagesPagedListAdapter.submitList(this)
                    }
                    allGalleryItems.setOnClickListener {
                        viewModel.getAllImages(id!!)
                        findNavController().navigate(R.id.action_filmPageFragment_to_galleryFragment)
                    }

                    // Похожие фильмы
                    recyclerViewSimilarFilms.adapter = similarMovieListAdapter
                    fullMovieData?.similarMovies?.apply {
                        if (this.isEmpty()) {
                            recyclerViewSimilarFilms.visibility = View.GONE
                            linearLayoutSimilarFilms.visibility = View.GONE
                        } else {
                            recyclerViewSimilarFilms.visibility = View.VISIBLE
                            linearLayoutSimilarFilms.visibility = View.VISIBLE
                        }
                        similarMovieListAdapter.submitList(this)
                    }
                    allSimilarFilms.setOnClickListener {
                        bundle.putString(Arguments.TYPE, Arguments.ARG_SIMILAR_MOVIES)
                        bundle.putString(
                            Arguments.COLLECTION_NAME,
                            getString(R.string.similar_films)
                        )
                        findNavController().navigate(
                            R.id.action_filmPageFragment_to_listPageFragment,
                            bundle
                        )
                    }

                    if(fullMovieData != null) {
                        loadingProgressBar.visibility = View.GONE
                        appbar.visibility = View.VISIBLE
                        nestedScrollView.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun onMovieItemClick(movie: SimilarMoviesItem) {
        val id = movie.filmId
        viewModel.getMovieData(id)
        bundle.putInt(Arguments.ARG_KINOPOISK_ID, id)
        findNavController().navigate(R.id.action_filmPageFragment_self, bundle)
    }

    private fun onActorItemClick(staff: MovieStaffImpl) {
        val id = staff.staffId
        viewModel.getActorInfo(id)
        findNavController().navigate(R.id.action_filmPageFragment_to_actorPageFragment)
    }

    private fun onGalleryItemClick(image: ImageImpl) {
        bundle.putString(Arguments.ARG_GALLERY_IMAGE_URL, image.imageUrl)
        findNavController().navigate(R.id.action_filmPageFragment_to_imageDialogFragment, bundle)
    }

    private fun getNumberOfEpisodesName(size: Int): String {
        val episodeArray = resources.getStringArray(R.array.number_of_episodes)
        val numberOfEpisodes = size % 10
        return if ((size in 11..20) || (numberOfEpisodes == 0) || (numberOfEpisodes in 5..9)) {
            episodeArray[2]
        } else if (numberOfEpisodes == 1) {
            episodeArray[0]
        } else episodeArray[1]
    }

    private fun getNumberOfSeasonsName(size: Int): String {
        val seasonArray = resources.getStringArray(R.array.number_of_seasons)
        val numberOfSeasons = size % 10
        return if ((size in 11..20) || (numberOfSeasons == 0) || (numberOfSeasons in 5..9)) {
            seasonArray[2]
        } else if (numberOfSeasons == 1) {
            seasonArray[0]
        } else seasonArray[1]
    }

    private fun addToCollection(
        movieStateFlow: StateFlow<Boolean>,
        movieInfo: KinopoiskMovieInfo,
        collectionType: String
    ) {
        val kinopoiskMovie: KinopoiskMovie
        val id = movieInfo.kinopoiskId
        movieInfo.apply {
            val name = nameRu ?: (nameOriginal ?: nameEn)
            val genre: String = genres.joinToString(separator = ", ") { genre ->
                genre.genre
            }
            kinopoiskMovie = KinopoiskMovie(
                kinopoiskId,
                posterUrlPreview,
                name,
                genre,
                year,
                ratingKinopoisk
            )
        }
        if (collectionType == Arguments.INTERESTING_COLLECTION) {
            val interestingMovie = InterestingMovie(kinopoiskId = id)

                if (!movieStateFlow.value) {
                    viewModel.insertMovieToInterestingCollection(kinopoiskMovie, interestingMovie)
                }
        } else {
            val watchedMovie = WatchedMovie(kinopoiskId = id)

                if (movieStateFlow.value) {
                    viewModel.deleteMovieFromWatchedCollection(kinopoiskId = id)
                } else {
                    viewModel.insertMovieToWatchedCollection(kinopoiskMovie, watchedMovie)
                }
        }
    }

    private fun isMovieInCollectionAfterClick(
        movieStateFlow: StateFlow<Boolean>,
        movieInfo: KinopoiskMovieInfo,
        collectionId: Int
    ) {
        val kinopoiskMovie: KinopoiskMovie
        val collectionMovie: CollectionMovie
        val id = movieInfo.kinopoiskId
        movieInfo.apply {
            val name = nameRu ?: (nameOriginal ?: nameEn)
            val genre: String = genres.joinToString(separator = ", ") { genre ->
                genre.genre
            }
            kinopoiskMovie = KinopoiskMovie(
                kinopoiskId,
                posterUrl,
                name,
                genre,
                year,
                ratingKinopoisk
            )
            collectionMovie =
                CollectionMovie(collectionId, id)
        }

        if (movieStateFlow.value) {
            viewModel.deleteMovieFromCollection(collectionMovie)
        } else {
            viewModel.insertMovieToCollection(kinopoiskMovie, collectionMovie)
        }
    }


    private fun isWatchedMovie(id: Int): Boolean {
        if (watchedMovieList.isNotEmpty()) {
            for (movie in watchedMovieList) {
                if (id == movie?.kinopoiskMovie?.kinopoiskId) return true
            }
        }
        return false
    }

    private fun formatMinutesToHoursAndMinutes(minutes: Int): String {
        val hours = minutes / 60
        val remainingMinutes = minutes % 60

        val hoursString = if (hours > 0) "$hours ч" else ""
        val minutesString = if (remainingMinutes > 0) "$remainingMinutes мин" else ""

        return if (hoursString.isNotEmpty() && minutesString.isNotEmpty()) {
            "$hoursString $minutesString"
        } else {
            hoursString + minutesString
        }
    }

    private fun shareMovie(imdbId: String) {
        val movieUrl = "https://www.imdb.com/title/$imdbId/"

        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, movieUrl)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}