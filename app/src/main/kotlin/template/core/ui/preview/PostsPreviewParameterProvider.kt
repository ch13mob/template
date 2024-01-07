package template.core.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import template.core.model.Post

class PostsPreviewParameterProvider : PreviewParameterProvider<List<Post>> {
    override val values: Sequence<List<Post>>
        get() = sequenceOf(
            listOf(
                Post(
                    id = 1,
                    title = "Title 1",
                    body = "Body 1",
                    userId = 1
                ),
                Post(
                    id = 2,
                    title = "Title 2",
                    body = "Body 2",
                    userId = 2
                ),
                Post(
                    id = 3,
                    title = "Title 3",
                    body = "Body 3",
                    userId = 3
                ),
                Post(
                    id = 4,
                    title = "Title 4",
                    body = "Body 4",
                    userId = 4
                ),
                Post(
                    id = 5,
                    title = "Title 5",
                    body = "Body 5",
                    userId = 5
                )
            )
        )
}
