package com.valerytimofeev.livehacktesttask.model

import androidx.compose.ui.graphics.vector.ImageVector

data class TextWithIcon(
    val text: String,
    val icon: ImageVector,
    val type: String,
    val contentDescription: String
)
