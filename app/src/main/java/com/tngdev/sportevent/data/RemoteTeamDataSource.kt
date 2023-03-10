package com.tngdev.sportevent.data

import com.tngdev.sportevent.model.TeamItem
import com.tngdev.sportevent.model.TeamListResponse
import com.tngdev.sportevent.network.TeamService

class RemoteTeamDataSource(private val teamService: TeamService): TeamDataSource {
    override suspend fun getAllTeams(): List<TeamItem> {
        return teamService.getAllTeams().teams
    }
}