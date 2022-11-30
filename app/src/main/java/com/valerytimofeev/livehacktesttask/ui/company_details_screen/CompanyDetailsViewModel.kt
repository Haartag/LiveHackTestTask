package com.valerytimofeev.livehacktesttask.ui.company_details_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valerytimofeev.livehacktesttask.data.remote.responses.LifeHackDetailsResponse
import com.valerytimofeev.livehacktesttask.usecase.LoadDataUseCase
import com.valerytimofeev.livehacktesttask.utility.Constants.IMG_URL_TEMPLATE
import com.valerytimofeev.livehacktesttask.utility.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val loadData: LoadDataUseCase
): ViewModel() {

    private val currentCompanyId: Int = checkNotNull(savedStateHandle["companyId"])

    /**
     * Data from API call - [LifeHackDetailsResponse]
     */
    private val _remoteData =
        MutableStateFlow(Resource.success(LifeHackDetailsResponse()))
    val remoteData: StateFlow<Resource<LifeHackDetailsResponse>> = _remoteData

    init {
        viewModelScope.launch {
            loadData.loadCompanyDetails(currentCompanyId).collect {
                _remoteData.value = it
            }
        }
    }

    fun makeUrlForImg(): String {
        return "$IMG_URL_TEMPLATE$currentCompanyId.jpg"
    }

}