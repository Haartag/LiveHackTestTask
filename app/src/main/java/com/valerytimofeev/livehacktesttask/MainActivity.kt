package com.valerytimofeev.livehacktesttask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.valerytimofeev.livehacktesttask.ui.theme.LiveHackTestTaskTheme
import com.valerytimofeev.livehacktesttask.ui.theme.Surface
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LiveHackTestTaskTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Surface
                ) {
                    Navigation()
                }
            }
        }
    }
}
