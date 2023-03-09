package com.tngdev.sportevent.repository

import com.tngdev.sportevent.data.MatchDataSource
import com.tngdev.sportevent.model.MatchItem
import com.tngdev.sportevent.model.MatchesList
import com.tngdev.sportevent.network.ApiResource
import com.tngdev.sportevent.network.INetworkCheckService
import com.tngdev.sportevent.network.MatchService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MatchRepository @Inject constructor(
    private val matchDataSource: MatchDataSource,
    private var networkCheckService: INetworkCheckService
) {
    private val TAG = MatchRepository::class.java.simpleName

    suspend fun getMatches(): ApiResource<List<MatchItem>> {
        if (!networkCheckService.hasInternet()) {
            return ApiResource.NoInternet(null)
        }

        return try {
            ApiResource.Success(matchDataSource.getMatches())
        } catch (e: Throwable) {
            e.printStackTrace()
            ApiResource.Error(e.message)
        }
    }
}