package template.core.ui.extensions

import template.core.common.error.Error

fun Error.message(): String {
    return when (this) {
        is Error.NoNetworkConnection -> "⚠️ You aren’t connected to the internet."
        else -> "⚠️ Oops! Something went wrong"
    }
}
