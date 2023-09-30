package com.ch13mob.sample.features.annotatedstring

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.UrlAnnotation
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AnnotatedStringScreen() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TermsAndConditionsText(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            fullText = MockData.PrivacyPolicy.fullText,
            links = MockData.PrivacyPolicy.links,
        )
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
private fun TermsAndConditionsText(
    modifier: Modifier = Modifier,
    fullText: String,
    links: List<Link>,
) {
    val annotatedLinkString: AnnotatedString = buildAnnotatedString {
        append(fullText)
        addStyle(
            start = 0,
            end = fullText.length,
            style = SpanStyle(
                color = Color.Black,
                fontSize = 16.sp,
            )
        )

        links.forEach { link ->
            val startIndex = fullText.indexOf(link.text)
            val endIndex = startIndex + link.text.length
            addStyle(
                start = startIndex,
                end = endIndex,
                style = SpanStyle(
                    color = Color.Blue,
                    fontSize = 16.sp,
                    textDecoration = TextDecoration.Underline
                )
            )
            addUrlAnnotation(
                UrlAnnotation(link.url),
                start = startIndex,
                end = endIndex
            )
        }
    }

    val uriHandler = LocalUriHandler.current

    ClickableText(
        modifier = modifier,
        text = annotatedLinkString,
        onClick = {
            annotatedLinkString.getUrlAnnotations(it, it).firstOrNull()?.let { annotation ->
                uriHandler.openUri(annotation.item.url)
            }
        }
    )
}
