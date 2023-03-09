package com.tngdev.sportevent.data

import com.tngdev.sportevent.model.MatchItem
import com.tngdev.sportevent.model.MatchesList

interface MatchDataSource {

    suspend fun getMatches(): List<MatchItem>
}