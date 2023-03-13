package com.tngdev.sportevent.data

import com.tngdev.sportevent.model.MatchItem
import com.tngdev.sportevent.network.MatchService

class RemoteMatchDataSource constructor(val matchService: MatchService) : MatchDataSource {
    override suspend fun getMatches(): List<MatchItem> {
        val matchesList = matchService.getMatches()
        return mutableListOf<MatchItem>().apply {
            addAll(matchesList.matches.upcoming.sortedByDescending { it.date })
            addAll(matchesList.matches.previous.sortedByDescending { it.date })
        }
    }
}