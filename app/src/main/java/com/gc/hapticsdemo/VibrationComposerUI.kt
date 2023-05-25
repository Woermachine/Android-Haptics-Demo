package com.gc.hapticsdemo

import android.view.View
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gc.hapticsdemo.ui.theme.HapticsDemoTheme

class TextFieldState {
    var text: String by mutableStateOf("")
}

@Composable
fun VibrationComposerUI(statePairs: Array<Pair<TextFieldState, TextFieldState>>, play: (view: View) -> Unit) {
    Column {
        Row {
            val view = LocalView.current
            Button(onClick = { play(view) } ) {
                Text(text = "Play")
            }
        }
        statePairs.forEachIndexed { index, pair ->
            ComposerRow(index + 1, pair.first, pair.second)
        }
    }
}



private fun isValidInt255(text : String): Boolean {
    if (text == "") return true
    return try {
        val tmp = text.toInt()
        tmp in 1..255

    } catch (e : Throwable) {
        false
    }
}

private fun isValidLong(text : String): Boolean {
    if (text == "") return true
    return try {
        val tmp = text.toLong()
        tmp > -1L
    } catch (e : Throwable) {
        false
    }
}

@Composable
fun ComposerRow(
    index: Int,
    textState : TextFieldState = remember { TextFieldState() },
    timeState: TextFieldState = remember { TextFieldState() }
) {

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(modifier = Modifier.padding(10.dp), text = "$index")
        TextField(
            modifier = Modifier.weight(1f),
            value = textState.text,
            onValueChange = {
                textState.text = it
            },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            isError = !isValidInt255(textState.text),
            label = { Text(text = "Amplitude (0-255)")},
            placeholder = { Text(text = "250") },
            maxLines = 1
        )
        TextField(
            modifier = Modifier.weight(1f),
            value = timeState.text,
            onValueChange = {
                timeState.text = it
            },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            isError = !isValidLong(textState.text),
            label = { Text(text = "Time (ms)")},
            placeholder = { Text(text = "100") },
            maxLines = 1
        )
    }
}

@Preview
@Composable
fun VibrationComposerUIPreview() {
    HapticsDemoTheme {
        val statePairs = arrayOf(
            Pair(TextFieldState(), TextFieldState()),
            Pair(TextFieldState(), TextFieldState()),
            Pair(TextFieldState(), TextFieldState()),
            Pair(TextFieldState(), TextFieldState()),
        )
        VibrationComposerUI(statePairs) { }
    }
}

@Preview
@Composable
fun ComposerRowPreview() {
    HapticsDemoTheme {
        ComposerRow(1, TextFieldState(), TextFieldState())
    }
}