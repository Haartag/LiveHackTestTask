package com.valerytimofeev.livehacktesttask.ui.common_composables

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.valerytimofeev.livehacktesttask.ui.company_list_screen.CompanyListViewModel
import com.valerytimofeev.livehacktesttask.utility.Status


/**
 * Loading screen for [Status.LOADING]
 */
@Composable
fun LoadingNow(
    viewModel: CompanyListViewModel = hiltViewModel()
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoadingAnimation()
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Loading",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                color = viewModel.loadingAnimationColor
            )
        }

    }

}

/**
 * Loading animated image
 */
@Composable
fun LoadingAnimation(
    viewModel: CompanyListViewModel = hiltViewModel()
) {
    val infiniteTransition = rememberInfiniteTransition()

    val arcAngle1 by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 180F,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val arcAngle2 by infiniteTransition.animateFloat(
        initialValue = 180F,
        targetValue = 360F,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Canvas(
        modifier = Modifier
            .padding(12.dp)
            .size(100.dp)
    ) {
        drawArc(
            color = viewModel.loadingAnimationColor,
            startAngle = arcAngle1,
            sweepAngle = 90f,
            useCenter = false,
            style = Stroke(width = 10f, cap = StrokeCap.Round),
        )

        drawArc(
            color = viewModel.loadingAnimationColor,
            startAngle = arcAngle2,
            sweepAngle = 90f,
            useCenter = false,
            style = Stroke(width = 10f, cap = StrokeCap.Round),
        )

        drawCircle(
            color = viewModel.loadingAnimationLightColor,
            radius = 60f,
        )
    }
}