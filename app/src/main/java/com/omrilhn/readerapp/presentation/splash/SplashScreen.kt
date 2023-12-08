package com.omrilhn.readerapp.presentation.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.omrilhn.readerapp.R
import com.omrilhn.readerapp.navigation.Screen
import com.omrilhn.readerapp.ui.theme.SpaceLarge
import com.omrilhn.readerapp.utils.Constants
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@Composable
fun SplashScreen(
    navController: NavController,
    dispatcher: CoroutineDispatcher = Dispatchers.Main) {
    val scale = remember {
        Animatable(0f)
    }
    val overshootInterpolator = remember {
        OvershootInterpolator(2f)
    }
    LaunchedEffect(key1 = true) {
        withContext(dispatcher) {
            scale.animateTo(
                targetValue = 0.5f,
                animationSpec = tween(
                    durationMillis = 500,
                    easing = {//Adjusts elasticity of the Animation.
                        overshootInterpolator.getInterpolation(it)
                    }
                )
            )
            delay(Constants.SPLASH_SCREEN_DURATION)
            navController.popBackStack()
            navController.navigate(Screen.LoginScreen.route)
        }
    }

//    Surface(modifier = Modifier
//        .padding(15.dp)
//        .size(300.dp),
//        shape = CircleShape,
//        color = Color.White,
//        border = BorderStroke(width = 2.dp,
//            color = Color.LightGray)){
        Box(modifier = Modifier.fillMaxSize()
            .clip(CircleShape)
            .border(BorderStroke(width = 2.dp,
                color = Color.LightGray))
            .size(330.dp),
            contentAlignment = Alignment.Center){

            Image(
                painter = painterResource(id = R.drawable.booklogo),
                contentDescription = "Logo",
                modifier = Modifier.scale(scale.value)
            )

//        }

    }
}

