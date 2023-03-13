package com.tngdev.sportevent.data

import com.tngdev.sportevent.model.MatchItem
import com.tngdev.sportevent.model.TeamItem
import com.tngdev.sportevent.model.TeamListResponse
import com.tngdev.sportevent.network.TeamService

class RemoteTeamDataSource(private val teamService: TeamService): TeamDataSource {
    override suspend fun getAllTeams(): List<TeamItem> {
        return teamService.getAllTeams().teams
    }

    override suspend fun getTeamMatches(teamId: String): List<MatchItem> {
        val matchesList = teamService.getTeamMatches(teamId)

        return mutableListOf<MatchItem>().apply {
            addAll(matchesList.matches.upcoming.sortedByDescending { it.date })
            addAll(matchesList.matches.previous.sortedByDescending { it.date })
        }
    }
}