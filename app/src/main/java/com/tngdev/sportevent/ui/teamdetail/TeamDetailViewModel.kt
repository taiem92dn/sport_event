package com.tngdev.sportevent.ui.teamdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tngdev.sportevent.model.MatchItem
import com.tngdev.sportevent.network.ApiResource
import com.tngdev.sportevent.repository.MatchRepository
import com.tngdev.sportevent.repository.TeamRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TeamDetailViewModel @Inject constructor(
    private val teamRepository: TeamRepository
) : ViewModel() {

    private var _matchListResponse = MutableLiveData<ApiResource<List<MatchItem>>>()
    val matchListResponse: LiveData<ApiResource<List<MatchItem>>>
        get() = _matchListResponse

    val errorMessage = MutableLiveData<String>()
    val showError = MutableLiveData<Boolean>(false)

    fun getTeamMatches(teamId: String) {
        Timber.d("call getTeamMatches")
        viewModelScope.launch {
            _matchListResponse.value = ApiResource.Loading()
            _matchListResponse.value = teamRepository.getTeamMatches(teamId)
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
        val TAG = TeamDetailViewModel::class.simpleName
    }
}