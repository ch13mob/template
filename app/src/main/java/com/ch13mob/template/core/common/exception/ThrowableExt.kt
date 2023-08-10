package com.ch13mob.template.core.common.exception

import java.net.UnknownHostException

typealias NoNetworkConnectionException = UnknownHostException

fun Throwable.message(): String {
    return when (this) {
        is NoNetworkConnectionException -> "No network connection"
        else -> "General error:${this.message ?: "Unknown error"}"
    }
}
