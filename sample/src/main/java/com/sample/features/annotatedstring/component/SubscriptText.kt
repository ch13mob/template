package com.sample.features.annotatedstring.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle

@Composable
fun SubscriptText(
    modifier: Modifier = Modifier,
    normalText: String,
    subText: String
) {
    Text(
        modifier = modifier,
        text = buildAnnotatedString {
            append(normalText)
            withStyle(
                style = SpanStyle(
                    baselineShift = BaselineShift.Subscript
                )
            ) {
                append(subText)
            }
        }
    )
}
