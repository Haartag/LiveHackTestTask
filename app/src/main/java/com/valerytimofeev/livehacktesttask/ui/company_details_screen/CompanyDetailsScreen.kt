package com.valerytimofeev.livehacktesttask.ui.company_details_screen

import android.telephony.PhoneNumberUtils
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.valerytimofeev.livehacktesttask.model.TextWithIcon
import com.valerytimofeev.livehacktesttask.ui.common_composables.DetailsError
import com.valerytimofeev.livehacktesttask.ui.common_composables.ImageError
import com.valerytimofeev.livehacktesttask.ui.common_composables.LoadingNow
import com.valerytimofeev.livehacktesttask.utility.Status

/**
 * Details screen, can be in one of three states - loading animation, company details or error message
 */
@Composable
fun CompanyDetailsScreen(
    navController: NavController,
    companyId: Int,
    tileColor: Color,
    viewModel: CompanyDetailsViewModel = hiltViewModel()
) {
    val stateFlow = viewModel.remoteData.collectAsState()

    when (stateFlow.value.status) {
        Status.LOADING -> {
            LoadingNow()
        }
        Status.SUCCESS -> {
            DetailsTile(
                name = stateFlow.value.data!![0].name,
                longText = stateFlow.value.data!![0].description,
                background = tileColor,
                textWithIconList = viewModel.getTextWithIconList(stateFlow.value.data!!)
            )
        }
        Status.ERROR -> {
            DetailsError(navController = navController)
        }
    }
}

/**
 * Company tile screen for [Status.SUCCESS]
 */
@Composable
fun DetailsTile(
    name: String,
    longText: String,
    background: Color,
    textWithIconList: List<TextWithIcon>,
    viewModel: CompanyDetailsViewModel = hiltViewModel()
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxSize()
            .shadow(elevation = 12.dp)
            .background(color = background)
    ) {

        Column {
            ImageBox(
                imageUrl = viewModel.makeUrlForImg(),
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White.copy(viewModel.brightenerAlpha(background)))
            ){
                ContentBox(
                    name = name,
                    longText = longText,
                    textWithIconList = textWithIconList
                )
            }

        }
    }

}

/**
 * Company image or placeholder
 */
@Composable
fun ImageBox(
    imageUrl: String,
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
        ImageError(0.25f)
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
 * Text description of company
 */
@Composable
fun ContentBox(
    name: String,
    longText: String,
    textWithIconList: List<TextWithIcon>
) {
    val scrollableState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(8.dp)
        )
        LinksBox(
            textWithIconList = textWithIconList
        )
        Text(
            text = longText,
            modifier = Modifier.verticalScroll(scrollableState),
            style = MaterialTheme.typography.body2,
        )
    }
}

@Composable
fun LinksBox(
    textWithIconList: List<TextWithIcon>,
    viewModel: CompanyDetailsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .padding(4.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            textWithIconList.forEach {
                TextWithIcon(
                    text = if (it.type == "Phone") {
                        PhoneNumberUtils.formatNumber(it.text, Locale.current.region)
                    } else it.text,
                    icon = it.icon,
                    description = it.contentDescription,
                    onClick = {
                        viewModel.makeIntent(context, it)
                    }
                )
            }
        }
    }
}

@Composable
fun TextWithIcon(
    text: String,
    icon: ImageVector,
    description: String,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(vertical = 6.dp)
            .clickable {
                onClick()
            }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = description
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = text)
    }
}








