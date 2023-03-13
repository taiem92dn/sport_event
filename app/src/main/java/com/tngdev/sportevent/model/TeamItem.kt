package com.tngdev.sportevent.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TeamItem(
    val id: String,
    val name: String,
    val logo: String,
): Parcelable
