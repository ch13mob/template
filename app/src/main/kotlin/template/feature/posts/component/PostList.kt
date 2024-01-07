package template.feature.posts.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import template.core.model.Post

@Composable
fun PostList(
    modifier: Modifier = Modifier,
    posts: List<Post>,
    onPostClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(count = posts.size) { index ->
            PostCard(
                post = posts[index],
                onPostClick = onPostClick
            )
        }
    }
}
