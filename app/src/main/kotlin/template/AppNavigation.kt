package template

import kotlinx.serialization.Serializable

@Serializable
sealed class NavigationGraph {
    @Serializable
    data object Login : NavigationGraph()

    @Serializable
    data object Posts : NavigationGraph()
}

@Serializable
sealed class Screen {
    @Serializable
    data object Launch : Screen()

    @Serializable
    data object Login : Screen()

    @Serializable
    data object Posts : Screen()

    @Serializable
    data class PostDetail(val postId: Int) : Screen() {
        companion object {
            const val PostIdArg = "postId"
            const val DeepLinkUriPattern = "https://template.com/{$PostIdArg}"
        }
    }
}
