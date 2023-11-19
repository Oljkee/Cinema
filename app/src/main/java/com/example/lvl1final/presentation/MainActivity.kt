package com.example.lvl1final.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.lvl1final.R
import com.example.lvl1final.data.NetworkConnectionManager
import com.example.lvl1final.data.entity.FilterParameters
import com.example.lvl1final.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val dataStoreViewModel: DataStoreViewModel by viewModels()

    @Inject
    lateinit var networkConnectionManager: NetworkConnectionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        networkConnectionManager.startListenNetworkState()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_container) as NavHostFragment
        val navController = navHostFragment.navController

        val filterParameters = FilterParameters(
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
        dataStoreViewModel.updateSearchFilterParameters(filterParameters)
        dataStoreViewModel.setAllTempValues(filterParameters = filterParameters)

        binding.bottomNav.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_home -> {
                    navigateToGraph(R.id.home_page_nested_graph, navController)
                    true
                }

                R.id.menu_search -> {
                    navigateToGraph(R.id.search_nested_graph, navController)
                    true
                }

                R.id.menu_profile -> {
                    navigateToGraph(R.id.profile_nested_graph, navController)
                    true
                }

                else -> false
            }
        }


        lifecycleScope.launch {
            dataStoreViewModel.isOnBoardingCompletedFlow
                .distinctUntilChanged()
                .collect { onBoardingCompleted ->
                    if (onBoardingCompleted) {
                        networkConnectionManager.isNetworkConnectedFlow
                            .onEach { isNetworkConnected ->
                                if (isNetworkConnected) {
                                    binding.bottomNav.visibility = View.VISIBLE
                                    navController.setGraph(R.navigation.root_nav_graph)
                                } else {
                                    binding.bottomNav.visibility = View.GONE
                                    navController.setGraph(R.navigation.no_internet_connection_nav_graph)
                                }
                            }
                            .launchIn(lifecycleScope)

                    } else {
                        binding.bottomNav.visibility = View.GONE
                        navController.setGraph(R.navigation.onboarding_nav_graph)
                    }
                }
        }
    }

    private fun navigateToGraph(graphId: Int, navController: NavController) {
        val currentDestination = navController.currentDestination

        if (currentDestination?.id != graphId) {
            navController.navigate(graphId)
        } else {
            val startDestinationId = navController.graph.startDestinationId
            navController.popBackStack(startDestinationId, false)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        networkConnectionManager.stopListenNetworkState()
    }
}
