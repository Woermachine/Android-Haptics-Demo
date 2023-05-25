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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.dp
import com.gc.hapticsdemo.ui.theme.HapticsDemoTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainUI(systemVibrator = getSystemVibrator())
        }
    }
}

@Composable
fun MainUI(systemVibrator: Vibrator? = null) {
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
                        systemVibrator?.let { vibe ->
                            view.vibrate(vibe, it.first)
                        }
                    }) {
                        Text(text = it.second)
                    }
                }

                val statePairs = arrayOf(
                    Pair(TextFieldState(), TextFieldState()),
                    Pair(TextFieldState(), TextFieldState()),
                    Pair(TextFieldState(), TextFieldState()),
                    Pair(TextFieldState(), TextFieldState()),
                )
                item {
                    Text(text = "Compose Vibration Pattern")
                    VibrationComposerUI(statePairs) {
                        systemVibrator?.let { vibe ->
                            try {
                                val a = VibrationEffect.createWaveform(
                                    longArrayOf(
                                        statePairs[0].second.text.toLong(),
                                        statePairs[1].second.text.toLong(),
                                        statePairs[2].second.text.toLong(),
                                        statePairs[3].second.text.toLong()
                                    ),
                                    intArrayOf(
                                        statePairs[0].first.text.toInt(),
                                        statePairs[1].first.text.toInt(),
                                        statePairs[2].first.text.toInt(),
                                        statePairs[3].first.text.toInt()
                                    ),
                                    -1
                                )
                                it.vibrate(vibe, a)
                            } catch (e: Throwable) {}
                        }
                    }
                }
                item { Spacer(modifier = Modifier.height(100.dp)) }
            }
        }

    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainUI()
}