package com.gc.hapticsdemo.ui

import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gc.hapticsdemo.customEffects
import com.gc.hapticsdemo.defaultVibratorEffect
import com.gc.hapticsdemo.vibratorEffectAPI29
import com.gc.hapticsdemo.hapticConstantsAPI27
import com.gc.hapticsdemo.hapticConstantsAPI30
import com.gc.hapticsdemo.legacyHapticConstants
import com.gc.hapticsdemo.vibrate

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainUI()
}

@Composable
fun MainUI(systemVibrator: Vibrator? = null) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {

        LazyColumn {
            // Haptics
            item {
                Text(text = "Haptics: These are predefined \"haptics\"")
            }
            buttonListItems(legacyHapticConstants)


            item {
                Text(text = "Haptics: Min API 27")
            }
            buttonListItems(hapticConstantsAPI27)


            item {
                Text(text = "Haptics: Min API 30")
            }
            buttonListItems(hapticConstantsAPI30)

            // Vibrations
            item {
                Spacer(modifier = Modifier.height(30.dp))
                Text(text = "Vibrations: These are predefined \"vibrations\"")
            }
            buttonListItems(defaultVibratorEffect)

            item {
                Text(text = "Vibrations: Min API 29")
            }
            buttonListItems(vibratorEffectAPI29)

            // Custom Vibrations
            item {
                Spacer(modifier = Modifier.height(30.dp))
                Text(text = "Custom Vibrations: These are custom \"vibration patterns\" I made")
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

            // Vibrations Composer
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
                        } catch (e: Throwable) {
                        }
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(100.dp)) }
        }
    }
}

fun LazyListScope.buttonListItems(list: List<Pair<Int, String>>) {
    items(list) {
        val view = LocalView.current
        Button(onClick = {
            view.vibrate(it.first)
        }) {
            Text(text = it.second)
        }
    }
}
