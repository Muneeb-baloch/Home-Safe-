package com.muneeb.homesafe.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.muneeb.homesafe.core.designsystem.theme.Page

@Composable
fun HomeSafeScreen(
    modifier: Modifier = Modifier,
    background: Color = Page,
    topBar: (@Composable () -> Unit)? = null,
    bottomBar: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(background)
            .windowInsetsPadding(WindowInsets.statusBars)
    ) {
        topBar?.invoke()

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            content()
        }

        if (bottomBar != null) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .imePadding()
            ) {
                bottomBar()
            }
        }
    }
}
