package com.valerytimofeev.livehacktesttask.ui.company_details_screen

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.content.ContextCompat.startActivity
import androidx.core.graphics.ColorUtils
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.valerytimofeev.livehacktesttask.data.remote.responses.LifeHackDetailsResponse
import com.valerytimofeev.livehacktesttask.model.TextWithIcon
import com.valerytimofeev.livehacktesttask.usecase.LoadDataUseCase
import com.valerytimofeev.livehacktesttask.usecase.MakeTextsWithIconUseCase
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
    private val loadData: LoadDataUseCase,
    private val makeTextsWithIcon: MakeTextsWithIconUseCase
) : ViewModel() {

    private val currentCompanyId: Int = checkNotNull(savedStateHandle["companyId"])

    /**
     * Data from API call - [LifeHackDetailsResponse]
     */
    private val _remoteData =
        MutableStateFlow(Resource.success(LifeHackDetailsResponse()))
    val remoteData: StateFlow<Resource<LifeHackDetailsResponse>> = _remoteData

    val mapText = mutableStateOf("Показать карту")
    val mapIcon = mutableStateOf(Icons.Default.KeyboardArrowDown)
    val isMapOpen = mutableStateOf(false)

    init {
        viewModelScope.launch {
            loadData.loadCompanyDetails(currentCompanyId).collect {
                _remoteData.value = it
            }
        }
    }

    val isMapLoaded = mutableStateOf(false)

    fun makeUrlForImg(): String {
        return "$IMG_URL_TEMPLATE$currentCompanyId.jpg"
    }

    fun getTextWithIconList(response: LifeHackDetailsResponse): List<TextWithIcon> {
        return makeTextsWithIcon(response)
    }

    /**
     * Make intent for clickable items - phone number, url.
     */
    fun makeIntent(
        context: Context,
        textWithIcon: TextWithIcon
    ) {
        when (textWithIcon.type) {
            "Phone" -> {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${textWithIcon.text}"))
                startActivity(context, intent, null)
            }
            "URL" -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://${textWithIcon.text}"))
                startActivity(context, intent, null)
            }
        }
    }

    /**
     * Brightener for background color
     */
    fun brightenerAlpha(
        color: Color
    ): Float {
        return when (ColorUtils.calculateLuminance(color.toArgb())) {
            in 0.0..0.1 -> 0.7f
            in 0.65..1.0 -> 0.0f
            else -> 0.4f
        }
    }

    /**
     * Show/hide Google map
     */
    fun mapOnClick() {
        if (isMapOpen.value) {
            mapText.value = "Показать карту"
            mapIcon.value = Icons.Default.KeyboardArrowDown
            isMapOpen.value = !isMapOpen.value
        } else {
            mapText.value = "Скрыть карту"
            mapIcon.value = Icons.Default.KeyboardArrowUp
            isMapOpen.value = !isMapOpen.value
        }
    }

    /**
     * Check Google Play availability, if response in not 0 hide map.
     */
    fun checkGooglePlayServices(context: Context): Boolean {
        val response = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context)
        return response == ConnectionResult.SUCCESS
    }

}