package com.tngdev.sportevent.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MatchItem(
    val date: String,
    val description: String,
    val home: String,
    val away: String,
): Parcelable {

    val winner: String? = null
    val highlights: String? = null

}
