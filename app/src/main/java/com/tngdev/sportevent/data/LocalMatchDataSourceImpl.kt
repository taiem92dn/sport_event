package com.tngdev.sportevent.data

import android.content.SharedPreferences
import com.tngdev.sportevent.model.MatchItem

class LocalMatchDataSourceImpl(private val sharedPreferences: SharedPreferences): LocalMatchDataSource {
    override suspend fun getMatches(): List<MatchItem> {
        TODO("Not yet implemented")
    }

    override fun saveMatchWorker(matchDateTime: String, workerId: String) {
        sharedPreferences
            .edit()
            .putString("date_${matchDateTime}", workerId)
            .apply()
    }

    override fun getMatchWorker(matchDateTime: String): String? {
        return sharedPreferences.getString("date_${matchDateTime}", null)
    }

    override fun deleteMatchWorker(matchDateTime: String) {
        sharedPreferences.edit().remove("date_${matchDateTime}").apply()
    }
}