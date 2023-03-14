package com.tngdev.sportevent.data

interface LocalMatchDataSource: MatchDataSource {

    fun saveMatchWorker(matchDateTime: String, workerId: String)

    fun getMatchWorker(matchDateTime: String): String?

    fun deleteMatchWorker(matchDateTime: String)
}