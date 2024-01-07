package com.sample.features.annotatedstring.component

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

private const val TextTag = "text"
private const val LinkTag = "link"
private val hashtagRegex = Regex("((?=[^\\w!])[#@][\\u4e00-\\u9fa5\\w]+)")

@Suppress("LongMethod")
@Composable
fun HashtagsText(
    modifier: Modifier = Modifier,
    text: String,
    onTagClick: (String) -> Unit
) {
    val annotatedStringList = remember {
        var lastIndex = 0
        val annotatedStringList = mutableStateListOf<AnnotatedString.Range<String>>()

        for (match in hashtagRegex.findAll(text)) {
            val start = match.range.first
            val end = match.range.last + 1
            val string = text.substring(start, end)

            if (start > lastIndex) {
                annotatedStringList.add(
                    AnnotatedString.Range(
                        text.substring(lastIndex, start),
                        lastIndex,
                        start,
                        TextTag
                    )
                )
            }
            annotatedStringList.add(
                AnnotatedString.Range(string, start, end, LinkTag)
            )
            lastIndex = end
        }

        if (lastIndex < text.length) {
            annotatedStringList.add(
                AnnotatedString.Range(
                    text.substring(lastIndex, text.length),
                    lastIndex,
                    text.length,
                    TextTag
                )
            )
        }
        annotatedStringList
    }

    val annotatedString = buildAnnotatedString {
        annotatedStringList.forEach {
            if (LinkTag == it.tag) {
                pushStringAnnotation(tag = it.tag, annotation = it.item)
                withStyle(style = SpanStyle(color = Color.Blue)) {
                    append(it.item)
                }
                pop()
            } else {
                withStyle(style = SpanStyle(color = Color.Black)) {
                    append(it.item)
                }
            }
        }
    }

    ClickableText(
        modifier = modifier,
        text = annotatedString,
        style = MaterialTheme.typography.bodyLarge,
        onClick = { position ->
            val annotatedStringRange = annotatedStringList.first {
                it.start < position && position < it.end
            }

            if (LinkTag == annotatedStringRange.tag) {
                onTagClick(annotatedStringRange.item)
            }
        }
    )
}
