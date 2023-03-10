package com.tngdev.sportevent.network

import com.tngdev.sportevent.model.TeamListResponse
import retrofit2.http.GET

interface TeamService {

    @GET("/teams")
    suspend fun getAllTeams(): TeamListResponse
}