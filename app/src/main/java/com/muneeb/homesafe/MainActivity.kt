package com.muneeb.homesafe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.muneeb.homesafe.core.designsystem.theme.HomeSafeTheme
import com.muneeb.homesafe.feature.splash.SplashContent
import com.muneeb.homesafe.navigation.MainScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val splash = installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        splash.setOnExitAnimationListener { provider ->
            provider.view.animate().alpha(0f).setDuration(250)
                .withEndAction { provider.remove() }.start()
        }

        setContent {
            HomeSafeTheme {
                var showSplash by remember { mutableStateOf(true) }

                LaunchedEffect(Unit) {
                    val minSplash = launch { delay(700) }
                    // future: val session = sessionRepo.load()
                    minSplash.join()
                    showSplash = false
                }

                if (showSplash) {
                    SplashContent()
                } else {
                    MainScreen()
                }
            }
        }
    }
}