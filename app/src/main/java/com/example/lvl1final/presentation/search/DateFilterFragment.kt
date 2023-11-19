package com.example.lvl1final.presentation.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.lvl1final.R
import com.example.lvl1final.databinding.FragmentDateFilterBinding
import com.example.lvl1final.presentation.DataStoreViewModel

class DateFilterFragment : Fragment() {
    private val dataStoreViewModel: DataStoreViewModel by activityViewModels()
    private var _binding: FragmentDateFilterBinding? = null
    private val binding get() = _binding!!
    private lateinit var startPeriodRangeOfYearsListAdapter: YearListAdapter
    private lateinit var endPeriodRangeOfYearsListAdapter: YearListAdapter
    private var selectedYearStartPeriod = ""
    private var selectedYearEndPeriod = ""
    private var currentStartPeriodRangeOfYears = (1950..1961).toList()
    private var currentEndPeriodRangeOfYears = (1950..1961).toList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDateFilterBinding.inflate(layoutInflater, container, false)
        selectedYearStartPeriod = dataStoreViewModel.yearFromFilterParameterTempValue.value
        selectedYearEndPeriod = dataStoreViewModel.yearToFilterParameterTempValue.value
        startPeriodRangeOfYearsListAdapter = YearListAdapter(
            setNewYear = { year -> setNewYearStartPeriod(year) },
            selectedYear = selectedYearStartPeriod.toInt()
        )
        endPeriodRangeOfYearsListAdapter = YearListAdapter(
            setNewYear = { year -> setNewYearEndPeriod(year) },
            selectedYear = selectedYearEndPeriod.toInt()
        )
        binding.startPeriod.recyclerViewYears.adapter = startPeriodRangeOfYearsListAdapter
        binding.endPeriod.recyclerViewYears.adapter = endPeriodRangeOfYearsListAdapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            currentStartPeriodRangeOfYears =
                getYearsForTheFirstStart(selectedYearStartPeriod.toInt())
            currentEndPeriodRangeOfYears = getYearsForTheFirstStart(selectedYearEndPeriod.toInt())

            setButtonsVisibility(
                currentStartPeriodRangeOfYears,
                startPeriod.imageViewPrev,
                startPeriod.imageViewNext
            )
            setButtonsVisibility(
                currentEndPeriodRangeOfYears,
                endPeriod.imageViewPrev,
                endPeriod.imageViewNext
            )

            startPeriodRangeOfYearsListAdapter.submitList(currentStartPeriodRangeOfYears)
            endPeriodRangeOfYearsListAdapter.submitList(currentEndPeriodRangeOfYears)

            startPeriod.textviewYears.text = getStartPeriodSelectedRangeOfYears()
            endPeriod.textviewYears.text = getEndPeriodSelectedRangeOfYears()

            startPeriod.imageViewPrev.setOnClickListener {
                currentStartPeriodRangeOfYears = loadYears(
                    false,
                    startPeriodRangeOfYearsListAdapter,
                    currentStartPeriodRangeOfYears
                )
                startPeriod.textviewYears.text = getStartPeriodSelectedRangeOfYears()
                setButtonsVisibility(
                    currentStartPeriodRangeOfYears,
                    startPeriod.imageViewPrev,
                    startPeriod.imageViewNext
                )
                setButtonsVisibility(
                    currentEndPeriodRangeOfYears,
                    endPeriod.imageViewPrev,
                    endPeriod.imageViewNext
                )
            }
            startPeriod.imageViewNext.setOnClickListener {
                currentStartPeriodRangeOfYears = loadYears(
                    true,
                    startPeriodRangeOfYearsListAdapter,
                    currentStartPeriodRangeOfYears
                )
                startPeriod.textviewYears.text = getStartPeriodSelectedRangeOfYears()
                setButtonsVisibility(
                    currentStartPeriodRangeOfYears,
                    startPeriod.imageViewPrev,
                    startPeriod.imageViewNext
                )
                setButtonsVisibility(
                    currentEndPeriodRangeOfYears,
                    endPeriod.imageViewPrev,
                    endPeriod.imageViewNext
                )
            }

            endPeriod.imageViewPrev.setOnClickListener {
                currentEndPeriodRangeOfYears =
                    loadYears(false, endPeriodRangeOfYearsListAdapter, currentEndPeriodRangeOfYears)
                endPeriod.textviewYears.text = getEndPeriodSelectedRangeOfYears()
                setButtonsVisibility(
                    currentStartPeriodRangeOfYears,
                    startPeriod.imageViewPrev,
                    startPeriod.imageViewNext
                )
                setButtonsVisibility(
                    currentEndPeriodRangeOfYears,
                    endPeriod.imageViewPrev,
                    endPeriod.imageViewNext
                )
            }
            endPeriod.imageViewNext.setOnClickListener {
                currentEndPeriodRangeOfYears =
                    loadYears(true, endPeriodRangeOfYearsListAdapter, currentEndPeriodRangeOfYears)
                endPeriod.textviewYears.text = getEndPeriodSelectedRangeOfYears()
                setButtonsVisibility(
                    currentStartPeriodRangeOfYears,
                    startPeriod.imageViewPrev,
                    startPeriod.imageViewNext
                )
                setButtonsVisibility(
                    currentEndPeriodRangeOfYears,
                    endPeriod.imageViewPrev,
                    endPeriod.imageViewNext
                )
            }


            btnApply.setOnClickListener {
                if (selectedYearEndPeriod < selectedYearStartPeriod) dataStoreViewModel.setYearTempValues(
                    selectedYearEndPeriod,
                    selectedYearStartPeriod
                ) else dataStoreViewModel.setYearTempValues(
                    selectedYearStartPeriod,
                    selectedYearEndPeriod
                )
                findNavController().popBackStack()
            }

            imgBackButton.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setNewYearStartPeriod(year: String) {
        selectedYearStartPeriod = year
    }

    private fun setNewYearEndPeriod(year: String) {
        selectedYearEndPeriod = year
    }

    private fun setButtonsVisibility(
        rangeOfYears: List<Int>,
        btnPrev: ImageView,
        btnNext: ImageView
    ) {
        val btnPrevIsEnabled = rangeOfYears.first() != 1950
        val btnNextIsEnabled = rangeOfYears.last() != 2045

        if (btnPrevIsEnabled) btnPrev.visibility = View.VISIBLE
        else btnPrev.visibility = View.INVISIBLE

        if (btnNextIsEnabled) btnNext.visibility = View.VISIBLE
        else btnNext.visibility = View.INVISIBLE
    }

    private fun loadYears(
        next: Boolean,
        yearsAdapter: YearListAdapter,
        periodRangeOfYears: List<Int>
    ): List<Int> {
        val firstYear = periodRangeOfYears.first()
        val lastYear = periodRangeOfYears.last()
        val years = if (next) {
//            (firstYear + 12)..(firstYear + 23)
            (firstYear + 12)..(lastYear + 12)
        } else {
            (firstYear - 12)..(lastYear - 12)
        }.toList()

        yearsAdapter.submitList(years)
        return years
    }

    private fun getStartPeriodSelectedRangeOfYears(): String {
        return getString(
            R.string.selected_range_of_years,
            currentStartPeriodRangeOfYears.first().toString(),
            currentStartPeriodRangeOfYears.last().toString()
        )
    }

    private fun getEndPeriodSelectedRangeOfYears(): String {
        return getString(
            R.string.selected_range_of_years,
            currentEndPeriodRangeOfYears.first().toString(),
            currentEndPeriodRangeOfYears.last().toString()
        )
    }

    private fun getYearsForTheFirstStart(selectedYear: Int): List<Int> {
        var firstYear = 1950
        var lastYear = 1961

        while (true) {
            val range = firstYear..lastYear
            if (selectedYear in range) {
                return range.toList()
            } else {
                firstYear += 12
                lastYear += 12
            }
        }

    }
}