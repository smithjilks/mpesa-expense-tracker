package com.smithjilks.mpesaexpensetracker.feature.splashscreen

import android.content.IntentFilter
import android.provider.Telephony
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.registerReceiver
import androidx.navigation.NavController
import com.smithjilks.mpesaexpensetracker.feature.R
import com.smithjilks.mpesaexpensetracker.core.constants.MpesaExpenseTrackerScreens
import com.smithjilks.mpesaexpensetracker.core.utils.Permissions
import com.smithjilks.mpesaexpensetracker.core.utils.SmsBroadcastReceiver
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {

    val scale = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = true, block = {
        scale.animateTo(targetValue = 0.9f,
            animationSpec = tween(
                durationMillis = 800,
                easing = {
                    OvershootInterpolator(8f).getInterpolation(it)
                }
            ))
        delay(900L)
        navController.navigate(MpesaExpenseTrackerScreens.DashboardScreen.name) {
            popUpTo(MpesaExpenseTrackerScreens.SplashScreen.name){ inclusive = true }
        }
    })

    Column(
        modifier = Modifier
            .padding(1.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(id = R.drawable.splash_screen_image),
            contentDescription = "Money  Image",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(300.dp)
                .scale(scale.value)
        )

        Text(
            text = "Know where your \nmoney goes.",
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            color = Color.Black
        )

        Text(
            text = "Track your M-pesa transactions easily,\nwith categories and financial report",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = Color.LightGray
        )

    }

}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {

}