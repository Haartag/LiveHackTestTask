package com.valerytimofeev.livehacktesttask.ui.company_list_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.valerytimofeev.livehacktesttask.data.remote.responses.LifeHackResponseItem
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
            /*TODO*/
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
) {
    LazyColumn(
        contentPadding = PaddingValues(8.dp)
    ) {
        items(count = data.size) {
            CompanyTile(
                modifier = Modifier.fillParentMaxHeight(0.22f),
                name = data[it].name,
                onClick = {
                    navController.navigate("company_list/${data[it].id.toInt()}")
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
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .shadow(elevation = 4.dp)
            .clickable {
                onClick()
            }
            .background(TileDefaultBackground)
    ) {
        Text(text = name)
    }
}

