package com.sample.features.textselection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TextSelectionScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        SelectionContainer {
            Column {
                Text("This is a selectable text.")
                DisableSelection {
                    Text("This text can't be selected.")
                }
                Text("This is also a selectable text.")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        val customTextSelectionColors = TextSelectionColors(
            handleColor = Color.Green,
            backgroundColor = Color.Yellow
        )

        CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
            SelectionContainer {
                Column {
                    Text("This is a selectable text with custom colors.")
                }
            }
        }
    }
}
