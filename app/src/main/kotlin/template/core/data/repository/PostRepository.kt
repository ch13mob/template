package template.core.data.repository

import kotlinx.coroutines.flow.Flow
import template.core.common.result.Result
import template.core.model.Post

interface PostRepository {
    fun getPosts(): Flow<Result<List<Post>>>
}
