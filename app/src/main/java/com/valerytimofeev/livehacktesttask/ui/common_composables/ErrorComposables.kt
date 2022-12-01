package com.valerytimofeev.livehacktesttask.ui.common_composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.valerytimofeev.livehacktesttask.R
import com.valerytimofeev.livehacktesttask.ui.company_list_screen.CompanyListViewModel
import com.valerytimofeev.livehacktesttask.utility.Status
import com.valerytimofeev.livehacktesttask.ui.company_list_screen.CompanyListScreen
import com.valerytimofeev.livehacktesttask.ui.company_details_screen.CompanyDetailsScreen

/**
 * Error screen for [Status.ERROR] at [CompanyListScreen]
 */
@Composable
fun CompanyListError(
    viewModel: CompanyListViewModel = hiltViewModel()
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ErrorBox("Попробовать снова") { viewModel.loadCompanyList() }
    }
}

/**
 * Error screen for [Status.ERROR] at [CompanyDetailsScreen]
 */
@Composable
fun DetailsError(
    navController: NavController,
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ErrorBox("Вернуться") { navController.popBackStack() }
    }
}

@Composable
fun ErrorBox(
    button: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .fillMaxWidth()
            .fillMaxHeight(0.44f)
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxHeight()
        ) {
            Icon(
                Icons.Default.Warning,
                contentDescription = "Warning",
                tint = Color.Red,
                modifier = Modifier.size(60.dp)
            )
            Text(
                textAlign = TextAlign.Center,
                text = "Невозможно загрузить данные.\nПроверьте подключение " +
                    "к интернету или попробуйте позднее."
            )
            OutlinedButton(
                onClick = { onClick() }
            ) {
                Text(
                    text = button,
                    color = Color.Black
                )
            }
        }
    }
}

/**
 * Placeholder (if the image cannot be loaded, but rest of data can be)
 * @param height height of image, 0.0f to 1.0f
 */
@Composable
fun ImageError(
    height: Float
) {
    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current)
            .data(data = R.drawable.big_image_placeholder)
            .build()
    )
    Image(
        painter = painter,
        contentDescription = "Can`t load image placeholder",
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(height),
        contentScale = ContentScale.Crop
    )
}