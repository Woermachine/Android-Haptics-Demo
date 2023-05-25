package com.gc.hapticsdemo

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.view.HapticFeedbackConstants
import android.view.View
import android.view.accessibility.AccessibilityManager
import androidx.activity.ComponentActivity
import androidx.compose.ui.hapticfeedback.HapticFeedbackType

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

val hapticConstantsAPI27 = listOf(
    // Requires API 27
    Pair(HapticFeedbackConstants.KEYBOARD_PRESS,"KEYBOARD_PRESS"),
    Pair(HapticFeedbackConstants.KEYBOARD_RELEASE,"KEYBOARD_RELEASE"),
    Pair(HapticFeedbackConstants.TEXT_HANDLE_MOVE,"TEXT_HANDLE_MOVE"),
    Pair(HapticFeedbackConstants.VIRTUAL_KEY_RELEASE,"VIRTUAL_KEY_RELEASE"),
)

val hapticConstantsAPI30 = listOf(
    // Requires API 30
    Pair(HapticFeedbackConstants.CONFIRM,"CONFIRM"),
    Pair(HapticFeedbackConstants.GESTURE_START,"GESTURE_START"),
    Pair(HapticFeedbackConstants.GESTURE_END,"GESTURE_END"),
    Pair(HapticFeedbackConstants.REJECT,"REJECT"),
)

val composeHapticTypes = listOf(
    Pair(HapticFeedbackType.LongPress, "LongPress"),
    Pair(HapticFeedbackType.TextHandleMove, "TextHandleMove"),
)

val defaultVibratorEffect = listOf(
    Pair(VibrationEffect.DEFAULT_AMPLITUDE, "DEFAULT_AMPLITUDE"),
    // Requires API 29
    // Pair(VibrationEffect.EFFECT_CLICK, "EFFECT_CLICK"),
    // Pair(VibrationEffect.EFFECT_DOUBLE_CLICK, "EFFECT_DOUBLE_CLICK"),
    // Pair(VibrationEffect.EFFECT_HEAVY_CLICK,"EFFECT_HEAVY_CLICK"),
    // Pair(VibrationEffect.EFFECT_TICK,"EFFECT_TICK"),
)


val vibratorEffectAPI29 = listOf(
    // Requires API 29
    Pair(VibrationEffect.EFFECT_CLICK, "EFFECT_CLICK"),
    Pair(VibrationEffect.EFFECT_DOUBLE_CLICK, "EFFECT_DOUBLE_CLICK"),
    Pair(VibrationEffect.EFFECT_HEAVY_CLICK,"EFFECT_HEAVY_CLICK"),
    Pair(VibrationEffect.EFFECT_TICK,"EFFECT_TICK"),
)

val customEffects = listOf(
    Pair(VibrationEffect.createOneShot(1000, 126), "50% One Shot"),
    Pair(VibrationEffect.createOneShot(1000, 255), "100% One Shot"),
    Pair(VibrationEffect.createWaveform(longArrayOf(250L, 250L, 250L), intArrayOf(126, 255, 63), -1), "50%, 100%, 25%"),
    Pair(VibrationEffect.createWaveform(longArrayOf(75L, 75L, 75L), intArrayOf(126, 0, 255), -1), "iOS Success"),
    Pair(VibrationEffect.createWaveform(longArrayOf(75L, 100L, 75L), intArrayOf(255, 0, 126), -1), "iOS Warning"),
    Pair(VibrationEffect.createWaveform(longArrayOf(75L, 50L, 75L, 50L, 75L, 50L, 100L), intArrayOf(126, 0, 126, 0, 255, 0, 100), -1), "iOS Error"),
)

fun View.vibrate(feedbackConstant: Int) {
    if (context.isTouchExplorationEnabled()) {
        // Don't mess with a blind person's vibrations
        return
    }
    // Either this needs to be set to true, or android:hapticFeedbackEnabled="true" needs to be set in XML
    isHapticFeedbackEnabled = true

    // Most of the constants are off by default: for example, clicking on a button doesn't cause the phone to vibrate anymore
    // if we still want to access this vibration, we'll have to ignore the global settings on that.
    performHapticFeedback(feedbackConstant, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING)
}

fun View.vibrate(vibrator: Vibrator, vibrateEffect: VibrationEffect) {
    if (context.isTouchExplorationEnabled()) {
        // Don't mess with a blind person's vibrations
        return
    }
    // Either this needs to be set to true, or android:hapticFeedbackEnabled="true" needs to be set in XML
    isHapticFeedbackEnabled = true

    // Most of the constants are off by default: for example, clicking on a button doesn't cause the phone to vibrate anymore
    // if we still want to access this vibration, we'll have to ignore the global settings on that.
    vibrator.vibrate(vibrateEffect)
}

private fun Context.isTouchExplorationEnabled(): Boolean {
    // can be null during unit tests
    val accessibilityManager = getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager?
    return accessibilityManager?.isTouchExplorationEnabled ?: false
}


fun Context.getSystemVibrator(): Vibrator {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager =
            getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        vibratorManager.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        getSystemService(ComponentActivity.VIBRATOR_SERVICE) as Vibrator
    }
}