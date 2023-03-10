package com.tngdev.sportevent.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.tngdev.sportevent.model.MatchItem

@BindingAdapter("matchHome")
fun setMatchHome(textView: TextView, matchItem: MatchItem) {
    textView.text = matchItem.home
}

@BindingAdapter("matchAway")
fun setMatchAway(textView: TextView, matchItem: MatchItem) {
    textView.text = matchItem.away
}

@BindingAdapter("matchDate")
fun setMatchDate(textView: TextView, matchItem: MatchItem) {
    textView.text = formatDateStr(matchItem.date)
}

