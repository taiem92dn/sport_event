package com.tngdev.sportevent.ui.matchlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tngdev.sportevent.model.MatchItem
import com.tngdev.sportevent.network.ApiResource
import com.tngdev.sportevent.repository.MatchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MatchListViewModel @Inject constructor(
    private val matchRepository: MatchRepository
) : ViewModel() {

    private var _matchListResponse = MutableLiveData<ApiResource<List<MatchItem>>>()
    val matchListResponse: LiveData<ApiResource<List<MatchItem>>>
        get() = _matchListResponse

    val errorMessage = MutableLiveData<String>()
    val showError = MutableLiveData<Boolean>(false)

    fun getMatchList() {
        Timber.d("call getMatchList")
        viewModelScope.launch {
            _matchListResponse.value = ApiResource.Loading()
            _matchListResponse.value = matchRepository.getMatches()
        }
    }

    fun setShowError(message: String) {
        errorMessage.value = message
        showError.value = true
    }

    fun hideError() {
        showError.value = false
    }

    companion object {
        val TAG = MatchListViewModel::class.simpleName
    }
}