package com.gc.hapticsdemo

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.view.HapticFeedbackConstants
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import com.gc.hapticsdemo.ui.theme.HapticsDemoTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HapticsDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {

                    LazyColumn {
                        item {
                            Text(text = "Haptics: These are predefined \"haptics\"")
                        }
                        items(legacyHapticConstants) {
                            val haptic = LocalHapticFeedback.current
                            val view = LocalView.current
                            Button(onClick = {
                                view.vibrate(it.first)
                            }) {
                                Text(text = it.second)
                            }
                        }

                        item {
                            Text(text = "Vibrations: These are predefined \"vibrations\"")
                        }
                        items(defaultVibratorEffect) {
                            val haptic = LocalHapticFeedback.current
                            val view = LocalView.current
                            Button(onClick = {
                                view.vibrate(it.first)
                            }) {
                                Text(text = it.second)
                            }
                        }

                        item {
                            Text(text = "Vibrations: These are custom \"vibration patterns\" I made")
                        }
                        items(customEffects) {
                            val haptic = LocalHapticFeedback.current
                            val view = LocalView.current
                            Button(onClick = {
                                view.vibrate(getSystemVibrator(), it.first)
                            }) {
                                Text(text = it.second)
                            }
                        }

                    }
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HapticsDemoTheme {

    }
}