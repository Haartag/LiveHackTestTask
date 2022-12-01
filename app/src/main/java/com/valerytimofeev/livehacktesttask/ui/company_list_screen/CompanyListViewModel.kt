package com.valerytimofeev.livehacktesttask.ui.company_list_screen

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.valerytimofeev.livehacktesttask.data.remote.responses.LifeHackResponse
import com.valerytimofeev.livehacktesttask.ui.theme.LoadingAnimation
import com.valerytimofeev.livehacktesttask.ui.theme.LoadingAnimationLight
import com.valerytimofeev.livehacktesttask.usecase.LoadDataUseCase
import com.valerytimofeev.livehacktesttask.utility.Constants
import com.valerytimofeev.livehacktesttask.utility.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyListViewModel @Inject constructor(
    private val loadData: LoadDataUseCase,
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
        loadCompanyList()
    }

    fun loadCompanyList() {
        viewModelScope.launch {
            loadData.loadCompanyList().collect {
                _remoteData.value = it
            }
        }
    }

    fun makeUrlForImg(id: String): String {
        return "${Constants.IMG_URL_TEMPLATE}$id.jpg"
    }

    /**
     * Get most recent color of image to use it as background
     */
    fun calcDominantColor(drawable: Drawable, onFinish: (Color) -> Unit) {
        val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)

        Palette.from(bmp).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish(Color(colorValue))
            }
        }

    }

}