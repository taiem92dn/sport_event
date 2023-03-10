package com.tngdev.sportevent.ui.teams

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tngdev.sportevent.model.TeamItem
import com.tngdev.sportevent.network.ApiResource
import com.tngdev.sportevent.repository.TeamRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TeamListViewModel @Inject constructor(
    private val teamRepository: TeamRepository
) : ViewModel() {

    private var _teamListResponse = MutableLiveData<ApiResource<List<TeamItem>>>()
    val teamListResponse: LiveData<ApiResource<List<TeamItem>>>
        get() = _teamListResponse

    val errorMessage = MutableLiveData<String>()
    val showError = MutableLiveData<Boolean>(false)

    fun getAllTeam() {
        Timber.d("get All Team")
        viewModelScope.launch {
            _teamListResponse.value = ApiResource.Loading()
            _teamListResponse.value = teamRepository.getAllTeams()
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
        val TAG = TeamListViewModel::class.simpleName
    }
}