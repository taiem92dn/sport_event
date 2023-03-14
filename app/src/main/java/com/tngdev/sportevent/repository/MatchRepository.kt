package com.tngdev.sportevent.repository

import com.tngdev.sportevent.data.LocalMatchDataSource
import com.tngdev.sportevent.data.MatchDataSource
import com.tngdev.sportevent.model.MatchItem
import com.tngdev.sportevent.network.ApiResource
import com.tngdev.sportevent.network.INetworkCheckService
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class MatchRepository @Inject constructor(
    private val  remoteMatchDataSource: MatchDataSource,
    private val  localMatchDataSource: LocalMatchDataSource,
    private var networkCheckService: INetworkCheckService
) {
    private val TAG = MatchRepository::class.java.simpleName

    suspend fun getMatches(): ApiResource<List<MatchItem>> {
        if (!networkCheckService.hasInternet()) {
            return ApiResource.NoInternet(null)
        }

        return try {
            ApiResource.Success(remoteMatchDataSource.getMatches())
        } catch (e: Throwable) {
            e.printStackTrace()
            ApiResource.Error(e.message)
        }
    }

    fun saveMatchWorker(matchDateTime: String, workerId: String) {
        localMatchDataSource.saveMatchWorker(matchDateTime, workerId)
    }

    fun getMatchWorker(matchDateTime: String): String? {
        return localMatchDataSource.getMatchWorker(matchDateTime)
    }

    fun deleteMatchWorker(matchDateTime: String) {
        return localMatchDataSource.deleteMatchWorker(matchDateTime)
    }
}