package com.yagubogu.presentation.stats.stadium

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.yagubogu.YaguBoguApplication
import com.yagubogu.databinding.FragmentStadiumStatsBinding
import com.yagubogu.presentation.stats.stadium.model.StadiumStatsUiModel
import com.yagubogu.presentation.stats.stadium.model.TeamOccupancyStatus

@Suppress("ktlint:standard:backing-property-naming")
class StadiumStatsFragment : Fragment() {
    private var _binding: FragmentStadiumStatsBinding? = null
    private val binding: FragmentStadiumStatsBinding get() = _binding!!

    private val viewModel: StadiumStatsViewModel by viewModels {
        val app = requireActivity().application as YaguBoguApplication
        StadiumStatsViewModelFactory(app.statsRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentStadiumStatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
//        setupChart()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.stadiumStatsUiModel.observe(viewLifecycleOwner) { stadiumStatsUiModel: StadiumStatsUiModel ->
            binding.stadiumStatsUiModel = stadiumStatsUiModel
            loadChartData(stadiumStatsUiModel)
        }
    }

    private fun loadChartData(stadiumStatsUiModel: StadiumStatsUiModel) {
        val pieEntries = ArrayList<PieEntry>()

        stadiumStatsUiModel.teamOccupancyStatuses.forEach { teamOccupancyStatus: TeamOccupancyStatus ->
            pieEntries.add(
                PieEntry(
                    teamOccupancyStatus.percentage.toFloat(),
                    teamOccupancyStatus.teamName,
                ),
            )
        }

        val stadiumStatsChartDataSet = PieDataSet(pieEntries, STADIUM_CHART_DESCRIPTION)

        stadiumStatsChartDataSet.colors =
            stadiumStatsUiModel.teamOccupancyStatuses.map { requireContext().getColor(it.teamColor) }
        val pieData = PieData(stadiumStatsChartDataSet)
        pieData.setDrawValues(false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val STADIUM_CHART_DESCRIPTION = "오늘의 구장 현황"
    }
}
