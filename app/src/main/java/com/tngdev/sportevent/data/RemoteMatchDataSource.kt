package com.tngdev.sportevent.data

import com.tngdev.sportevent.model.MatchItem
import com.tngdev.sportevent.network.MatchService

class RemoteMatchDataSource constructor(val matchService: MatchService) : MatchDataSource {
    override suspend fun getMatches(): List<MatchItem> {
        val matchesList = matchService.getMatches()
        return mutableListOf<MatchItem>().apply {
            addAll(matchesList.matches.previous)
            addAll(matchesList.matches.upcoming)
        }
    }
}