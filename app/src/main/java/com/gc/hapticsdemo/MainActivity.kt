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

        val legacyHapticConstants = listOf(
            Pair(HapticFeedbackConstants.CLOCK_TICK, "CLOCK_TICK"),
            Pair(HapticFeedbackConstants.KEYBOARD_TAP, "KEYBOARD_TAP"),
            Pair(HapticFeedbackConstants.LONG_PRESS, "LONG_PRESS"),
            Pair(HapticFeedbackConstants.VIRTUAL_KEY, "VIRTUAL_KEY"),
            // Requires API 27
            // Pair(HapticFeedbackConstants.KEYBOARD_PRESS,"KEYBOARD_PRESS"),
            // Pair(HapticFeedbackConstants.KEYBOARD_RELEASE,"KEYBOARD_RELEASE"),
            // Pair(HapticFeedbackConstants.TEXT_HANDLE_MOVE,"TEXT_HANDLE_MOVE"),
            // Pair(HapticFeedbackConstants.VIRTUAL_KEY_RELEASE,"VIRTUAL_KEY_RELEASE"),
            // Requires API 30
            // Pair(HapticFeedbackConstants.CONFIRM,"CONFIRM"),
            // Pair(HapticFeedbackConstants.GESTURE_START,"GESTURE_START"),
            // Pair(HapticFeedbackConstants.GESTURE_END,"GESTURE_END"),
            // Pair(HapticFeedbackConstants.REJECT,"REJECT"),
        )

        val composeHapticTypes = listOf(
            Pair(HapticFeedbackType.LongPress, "LongPress"),
            Pair(HapticFeedbackType.TextHandleMove, "TextHandleMove"),
        )

        val defaultVibratorEffect = listOf(
            Pair(VibrationEffect.DEFAULT_AMPLITUDE, "DEFAULT_AMPLITUDE"),
            // Pair(VibrationEffect.EFFECT_CLICK, "EFFECT_CLICK"),
            // Pair(VibrationEffect.EFFECT_DOUBLE_CLICK, "EFFECT_DOUBLE_CLICK"),
            // Pair(VibrationEffect.EFFECT_HEAVY_CLICK,"EFFECT_HEAVY_CLICK"),
            // Pair(VibrationEffect.EFFECT_TICK,"EFFECT_TICK"),
        )

        val customEffects = listOf(
            Pair(VibrationEffect.createOneShot(1000, 126), "50% One Shot"),
            Pair(VibrationEffect.createOneShot(1000, 255), "100% One Shot"),
            Pair(VibrationEffect.createWaveform(longArrayOf(250L, 250L, 250L), intArrayOf(126, 255, 63), -1), "50%, 100%, 25%"),
            Pair(VibrationEffect.createWaveform(longArrayOf(75L, 75L, 75L), intArrayOf(126, 0, 255), -1), "iOS Success"),
            Pair(VibrationEffect.createWaveform(longArrayOf(75L, 100L, 75L), intArrayOf(255, 0, 126), -1), "iOS Warning"),
            Pair(VibrationEffect.createWaveform(longArrayOf(75L, 50L, 75L, 50L, 75L, 50L, 100L), intArrayOf(126, 0, 126, 0, 255, 0, 100), -1), "iOS Error"),
        )

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