package com.tngdev.sportevent.repository

import com.tngdev.sportevent.data.MatchDataSource
import com.tngdev.sportevent.data.TeamDataSource
import com.tngdev.sportevent.model.MatchItem
import com.tngdev.sportevent.model.TeamItem
import com.tngdev.sportevent.network.ApiResource
import com.tngdev.sportevent.network.INetworkCheckService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TeamRepository @Inject constructor(
    private val teamDataSource: TeamDataSource,
    private var networkCheckService: INetworkCheckService
) {
    private val TAG = TeamDataSource::class.java.simpleName

    suspend fun getAllTeams(): ApiResource<List<TeamItem>> {
        if (!networkCheckService.hasInternet()) {
            return ApiResource.NoInternet(null)
        }

        return try {
            ApiResource.Success(teamDataSource.getAllTeams())
        } catch (e: Throwable) {
            e.printStackTrace()
            ApiResource.Error(e.message)
        }
    }
}
