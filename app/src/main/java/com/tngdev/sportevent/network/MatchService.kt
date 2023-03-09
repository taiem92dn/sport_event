package com.tngdev.sportevent.network

import com.tngdev.sportevent.model.MatchItem
import com.tngdev.sportevent.model.MatchesList
import retrofit2.http.GET

interface MatchService {

    @GET("/teams/matches")
    suspend fun getMatches(): MatchesList<MatchItem>
}