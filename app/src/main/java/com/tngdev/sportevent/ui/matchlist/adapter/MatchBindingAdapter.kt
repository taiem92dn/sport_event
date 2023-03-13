package com.tngdev.sportevent.ui.matchlist.adapter

import android.graphics.Color
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.tngdev.sportevent.model.MatchItem
import com.tngdev.sportevent.util.formatDateStr

@BindingAdapter("matchHome")
fun setMatchHome(textView: TextView, matchItem: MatchItem) {
    textView.text = matchItem.home
    textView.setTextColor(
        if (matchItem.winner == null) {
            Color.BLACK
        }
        else if (matchItem.home == matchItem.winner) {
            Color.BLACK
        } else {
            Color.GRAY
        }
    )
}

@BindingAdapter("matchAway")
fun setMatchAway(textView: TextView, matchItem: MatchItem) {
    textView.text = matchItem.away
    textView.setTextColor(
        if (matchItem.winner == null) {
            Color.BLACK
        }
        else if (matchItem.away == matchItem.winner) {
            Color.BLACK
        } else {
            Color.GRAY
        }
    )
}

@BindingAdapter("matchDate")
fun setMatchDate(textView: TextView, matchItem: MatchItem) {
    textView.text = formatDateStr(matchItem.date)
}

