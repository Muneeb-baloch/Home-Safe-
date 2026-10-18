package com.muneeb.homesafe

import android.graphics.Color as AndroidColor
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.muneeb.homesafe.core.designsystem.theme.HomeSafeTheme
import com.muneeb.homesafe.feature.splash.SplashContent
import com.muneeb.homesafe.navigation.HomeSafeApp
import com.muneeb.homesafe.navigation.MAIN_ROUTE
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
                    minSplash.join()
                    showSplash = false
                }

                if (showSplash) {
                    LaunchedEffect(Unit) {
                        enableEdgeToEdge(
                            statusBarStyle = SystemBarStyle.dark(AndroidColor.parseColor("#176B5B")),
                            navigationBarStyle = SystemBarStyle.dark(AndroidColor.parseColor("#176B5B"))
                        )
                    }
                    SplashContent()
                } else {
                    val navController = rememberNavController()
                    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

                    LaunchedEffect(currentRoute) {
                        val color = if (currentRoute == MAIN_ROUTE) {
                            AndroidColor.WHITE
                        } else {
                            AndroidColor.parseColor("#F4F6F5")
                        }
                        enableEdgeToEdge(
                            statusBarStyle = SystemBarStyle.light(color, color),
                            navigationBarStyle = SystemBarStyle.light(color, color)
                        )
                    }

                    HomeSafeApp(navController = navController)
                }
            }
        }
    }
}
