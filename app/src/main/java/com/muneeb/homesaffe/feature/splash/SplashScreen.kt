package com.muneeb.homesaffe.feature.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muneeb.homesaffe.R
import com.muneeb.homesaffe.core.designsystem.theme.HomeSafeTheme

@Composable
fun SplashContent(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_homesafe_logo),
                contentDescription = "HomeSafe Logo",
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(68.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "HomeSafe",
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = (-0.5).sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Know they’re home.",
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f),
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi =  true)
@Composable
fun SplashContentPreview() {
    HomeSafeTheme {
        SplashContent()
    }
}