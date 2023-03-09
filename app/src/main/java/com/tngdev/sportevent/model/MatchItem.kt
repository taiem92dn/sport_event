package com.tngdev.sportevent.model

data class MatchItem(
    val date: String,
    val description: String,
    val home: String,
    val away: String,
    val winner: String,
    val highlights: String,
)
