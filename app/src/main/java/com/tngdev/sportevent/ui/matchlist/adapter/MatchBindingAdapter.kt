package com.tngdev.sportevent.ui.matchlist.adapter

import android.graphics.Color
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.BindingAdapter
import com.tngdev.sportevent.R
import com.tngdev.sportevent.model.MatchItem
import com.tngdev.sportevent.util.formatDateStr
import com.tngdev.sportevent.util.formatTimeStr

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

@BindingAdapter("matchTime")
fun setMatchTime(textView: TextView, matchItem: MatchItem) {
    textView.text = formatTimeStr(matchItem.date)
}

@BindingAdapter("showHomeWinner")
fun showHomeWinner(textView: TextView, matchItem: MatchItem) {
    textView.visibility =
        if (matchItem.winner == null) {
            View.INVISIBLE
        }
        else if (matchItem.home == matchItem.winner) {
            View.VISIBLE
        } else {
            View.INVISIBLE
        }
}

@BindingAdapter("showAwayWinner")
fun showAwayWinner(textView: TextView, matchItem: MatchItem) {
    textView.visibility =
        if (matchItem.winner == null) {
            View.INVISIBLE
        }
        else if (matchItem.away == matchItem.winner) {
            View.VISIBLE
        } else {
            View.INVISIBLE
        }
}

@BindingAdapter("matchReminder")
fun showMatchReminder(button: Button, matchItem: MatchItem) {
    button.visibility =
        if (matchItem.highlights == null)
            View.VISIBLE
        else
            View.GONE
}

@BindingAdapter("showSetReminder")
fun showSetReminder(button: Button, isSetReminder: Boolean) {
    val context = button.context
    if (isSetReminder) {
        button.text = context.getString(R.string.text_cancel_reminder_this_match)
        button.setCompoundDrawablesWithIntrinsicBounds(
            AppCompatResources.getDrawable(context, R.drawable.ic_cancel_24),
            null,
            null,
            null
        )
    }
    else {
        button.text = context.getString(R.string.text_set_reminder_for_this_match)
        button.setCompoundDrawablesWithIntrinsicBounds(
            AppCompatResources.getDrawable(context, R.drawable.ic_check_24),
            null,
            null,
            null
        )
    }
}

