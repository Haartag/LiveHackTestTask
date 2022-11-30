package com.valerytimofeev.livehacktesttask.ui.company_list_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valerytimofeev.livehacktesttask.data.remote.responses.LifeHackResponse
import com.valerytimofeev.livehacktesttask.ui.theme.LoadingAnimation
import com.valerytimofeev.livehacktesttask.ui.theme.LoadingAnimationLight
import com.valerytimofeev.livehacktesttask.usecase.LoadDataUseCase
import com.valerytimofeev.livehacktesttask.utility.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyListViewModel @Inject constructor(
    private val loadCompanyList: LoadDataUseCase
) : ViewModel() {

    val loadingAnimationColor = LoadingAnimation
    val loadingAnimationLightColor = LoadingAnimationLight

    /**
     * Data from API call - [LifeHackResponse]
     */
    private val _remoteData =
        MutableStateFlow(Resource.success(LifeHackResponse()))
    val remoteData: StateFlow<Resource<LifeHackResponse>> = _remoteData

    init {
        viewModelScope.launch {
            loadCompanyList.loadCompanyList().collect {
                _remoteData.value = it
            }
        }
    }
}