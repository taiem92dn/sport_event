package com.tngdev.sportevent.network

import com.tngdev.sportevent.model.MatchItem
import com.tngdev.sportevent.model.MatchesList
import com.tngdev.sportevent.model.TeamListResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface TeamService {

    @GET("/teams")
    suspend fun getAllTeams(): TeamListResponse

    @GET("/teams/{teamId}/matches")
    suspend fun getTeamMatches(@Path("teamId") teamId: String): MatchesList<MatchItem>
}