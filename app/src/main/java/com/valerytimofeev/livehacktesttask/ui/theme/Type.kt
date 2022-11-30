package com.valerytimofeev.livehacktesttask.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.valerytimofeev.livehacktesttask.R

val Ubuntu = FontFamily(
    Font(R.font.ubuntu_light, FontWeight.Light),
    Font(R.font.ubuntu_regular, FontWeight.Normal),
)

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = Ubuntu,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.5.sp
    ),
    body2 = TextStyle(
        fontFamily = Ubuntu,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        letterSpacing = 0.25.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = Ubuntu,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.15.sp
    ),
    subtitle2 = TextStyle(
        fontFamily = Ubuntu,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        letterSpacing = 0.1.sp
    ),
    h4 = TextStyle(
        fontFamily = Ubuntu,
        fontWeight = FontWeight.Light,
        fontSize = 35.sp,
        letterSpacing = 0.25.sp
    ),
    h5 = TextStyle(
        fontFamily = Ubuntu,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    ),
    caption = TextStyle(
        fontFamily = Ubuntu,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        letterSpacing = 0.4.sp
    ),
)