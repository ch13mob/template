package com.ch13mob.template.core.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone.getTimeZone

data class Quote(
    val id: Int = 0,
    val text: String = "",
    val author: String = "",
    val date: Instant = Clock.System.now()
) {
    fun lastUpdatedTime(): String {
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        parser.timeZone = getTimeZone("GMT")
        val formatter = SimpleDateFormat("HH:mm:ss")
        val time: String = formatter.format(parser.parse(date.toString()))

        return "Last updated at $time"
    }
}
