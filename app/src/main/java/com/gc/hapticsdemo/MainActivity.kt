package com.gc.hapticsdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.gc.hapticsdemo.ui.MainUI
import com.gc.hapticsdemo.ui.theme.HapticsDemoTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HapticsDemoTheme {
                MainUI(systemVibrator = getSystemVibrator())
            }
        }
    }
}


