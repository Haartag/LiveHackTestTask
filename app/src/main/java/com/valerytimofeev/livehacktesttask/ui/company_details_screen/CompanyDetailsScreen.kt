package com.valerytimofeev.livehacktesttask.ui.company_details_screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.valerytimofeev.livehacktesttask.R.drawable.big_image_placeholder
import com.valerytimofeev.livehacktesttask.ui.common_composables.LoadingNow
import com.valerytimofeev.livehacktesttask.ui.theme.TileDefaultBackground
import com.valerytimofeev.livehacktesttask.utility.Status

/**
 * Details screen, can be in one of three states - loading animation, company details or error message
 */
@Composable
fun CompanyDetailsScreen(
    navController: NavController,
    companyId: Int,
    viewModel: CompanyDetailsViewModel = hiltViewModel()
) {
    val stateFlow = viewModel.remoteData.collectAsState()

    when (stateFlow.value.status) {
        Status.LOADING -> {
            LoadingNow()
        }
        Status.SUCCESS -> {
            DetailsTile()
        }
        Status.ERROR -> {
            Log.d("TestTag", "CompanyDetailsScreen: Error ${stateFlow.value.message}")
        }
    }
}

/**
 * Company tile screen for [Status.SUCCESS]
 */
@Composable
fun DetailsTile(
    viewModel: CompanyDetailsViewModel = hiltViewModel()
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxSize()
            .shadow(elevation = 12.dp)
            .background(color = TileDefaultBackground)
    ) {
        Column {
            ImageBox(imageUrl = viewModel.makeUrlForImg())
            ContentBox()
        }
    }

}

/**
 * Company image or placeholder
 */
@Composable
fun ImageBox(
    imageUrl: String
) {
    val painter =
        rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current)
                .data(data = imageUrl)
                .build()
        )

    val painterState = painter.state

    if (painterState is AsyncImagePainter.State.Loading) {
        CircularProgressIndicator(
            color = MaterialTheme.colors.primary,
            modifier = Modifier
                .scale(0.5f)
        )
    }
    if (painterState is AsyncImagePainter.State.Error) {
        ImageError()
    } else {
        Image(
            painter = painter,
            contentDescription = "", /*TODO*/
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.25f),
            contentScale = ContentScale.Crop
        )
    }
}

/**
 * Placeholder (if the image cannot be loaded, but rest of data can be)
 */
@Composable
fun ImageError() {
    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current)
            .data(data = big_image_placeholder)
            .build()
    )
    Image(
        painter = painter,
        contentDescription = "Can`t load image placeholder",
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.25f),
        contentScale = ContentScale.Crop
        //.align(Alignment.CenterHorizontally),
    )
}

/**
 * Text description of company
 */
@Composable
fun ContentBox() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Text(
            text = "name",
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(8.dp)
        )
        Text(
            text = "Long text description",
            style = MaterialTheme.typography.body2,
        )
    }
}








