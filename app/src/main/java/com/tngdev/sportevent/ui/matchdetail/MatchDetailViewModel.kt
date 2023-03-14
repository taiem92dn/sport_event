package com.tngdev.sportevent.ui.matchdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tngdev.sportevent.repository.MatchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MatchDetailViewModel @Inject constructor(
    private val matchRepository: MatchRepository
) : ViewModel() {

    private val _isSetReminder = MutableLiveData(false)
    val isSetReminder : LiveData<Boolean> = _isSetReminder

    fun saveReminderWorker(matchDateTime: String, workerId: String) {
        matchRepository.saveMatchWorker(matchDateTime, workerId)
        _isSetReminder.value = true
    }

    fun getReminderWorker(matchDateTime: String): String? {
        val result = matchRepository.getMatchWorker(matchDateTime)
        _isSetReminder.value = result != null

        return result
    }

    fun deleteReminderWorker(matchDateTime: String) {
        matchRepository.deleteMatchWorker(matchDateTime)
        _isSetReminder.value = false
    }
}