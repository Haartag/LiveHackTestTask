package com.valerytimofeev.livehacktesttask.ui.company_list_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.valerytimofeev.livehacktesttask.data.remote.responses.LifeHackResponseItem
import com.valerytimofeev.livehacktesttask.ui.common_composables.CompanyListError
import com.valerytimofeev.livehacktesttask.ui.common_composables.ImageError
import com.valerytimofeev.livehacktesttask.ui.common_composables.LoadingNow
import com.valerytimofeev.livehacktesttask.ui.theme.TileDefaultBackground
import com.valerytimofeev.livehacktesttask.utility.Status

/**
 * Main screen, can be in one of three states - loading animation, company list or error message
 */
@Composable
fun CompanyListScreen(
    navController: NavController,
    viewModel: CompanyListViewModel = hiltViewModel()
) {
    val stateFlow = viewModel.remoteData.collectAsState()

    when (stateFlow.value.status) {
        Status.LOADING -> {
            LoadingNow()
        }
        Status.SUCCESS -> {
            CompanyList(
                data = stateFlow.value.data!!,
                navController = navController
            )
        }
        Status.ERROR -> {
            CompanyListError()
        }
    }
}

/**
 * Company list screen for [Status.SUCCESS]
 */
@Composable
fun CompanyList(
    data: List<LifeHackResponseItem>,
    navController: NavController,
    viewModel: CompanyListViewModel = hiltViewModel()
) {
    val gradient = Brush.horizontalGradient(
        colors = listOf(Color.Transparent, Color.Black)
    )

    LazyColumn(
        contentPadding = PaddingValues(8.dp)
    ) {
        items(count = data.size) { index ->
            CompanyTile(
                modifier = Modifier.fillParentMaxHeight(0.22f),
                name = data[index].name,
                imgUrl = viewModel.makeUrlForImg(data[index].id),
                gradient = gradient,
                onClick = { color ->
                    navController.navigate("company_list/${data[index].id.toInt()}/${color.toArgb()}")
                }
            )
        }
    }
}

/**
 * Tile for company list
 */
@Composable
fun CompanyTile(
    modifier: Modifier,
    name: String,
    imgUrl: String,
    gradient: Brush,
    onClick: (Color) -> Unit,
    viewModel: CompanyListViewModel = hiltViewModel()
) {
    var tileColor by remember {
        mutableStateOf(TileDefaultBackground)
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .shadow(elevation = 4.dp)
            .clickable {
                onClick(tileColor)
            }
            .background(TileDefaultBackground)
    ) {

        val painter =
            rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current)
                    .data(data = imgUrl)
                    .build()
            )

        (painter.state as? AsyncImagePainter.State.Success)?.let { successState ->
            LaunchedEffect(Unit) {
                val drawable = successState.result.drawable
                viewModel.calcDominantColor(drawable) { color ->
                    tileColor = color
                }
            }
        }

        val painterState = painter.state
        if (painterState is AsyncImagePainter.State.Loading) {
            CircularProgressIndicator(
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .scale(0.5f)
            )
        }
        Box(modifier = Modifier.fillMaxSize()) {
            if (painterState is AsyncImagePainter.State.Error) {
                ImageError(1.0f)
            } else {
                Image(
                    painter = painter,
                    contentDescription = name,
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(gradient),
                contentAlignment = Alignment.BottomEnd
            ) {
                Text(
                    text = name,
                    color = Color.LightGray,
                    modifier = Modifier.padding(4.dp)
                )
            }

        }

    }
}

