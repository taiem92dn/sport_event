package com.tngdev.sportevent.data

import com.tngdev.sportevent.model.TeamItem
import com.tngdev.sportevent.model.TeamListResponse

interface TeamDataSource {

    suspend fun getAllTeams(): List<TeamItem>
}