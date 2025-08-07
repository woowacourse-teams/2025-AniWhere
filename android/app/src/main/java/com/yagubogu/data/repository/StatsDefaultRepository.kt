package com.yagubogu.data.repository

import com.yagubogu.data.datasource.StatsDataSource
import com.yagubogu.data.dto.response.StatsCountsResponse
import com.yagubogu.data.dto.response.StatsLuckyStadiumsResponse
import com.yagubogu.data.dto.response.StatsWinRateResponse
import com.yagubogu.domain.model.StatsCounts
import com.yagubogu.domain.repository.StatsRepository

class StatsDefaultRepository(
    private val statsDataSource: StatsDataSource,
) : StatsRepository {
    override suspend fun getStatsWinRate(
        memberId: Long,
        year: Int,
    ): Result<Double> =
        statsDataSource
            .getStatsWinRate(memberId, year)
            .map { statsWinRateResponse: StatsWinRateResponse ->
                statsWinRateResponse.winPercent
            }

    override suspend fun getStatsCounts(
        memberId: Long,
        year: Int,
    ): Result<StatsCounts> =
        statsDataSource
            .getStatsCounts(memberId, year)
            .map { statsCountsResponse: StatsCountsResponse ->
                statsCountsResponse.toDomain()
            }

    override suspend fun getLuckyStadiums(
        memberId: Long,
        year: Int,
    ): Result<String?> =
        statsDataSource
            .getLuckyStadiums(memberId, year)
            .map { statsLuckyStadiumsResponse: StatsLuckyStadiumsResponse ->
                statsLuckyStadiumsResponse.shortName
            }
}
