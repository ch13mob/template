package template.core.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import template.core.common.coroutines.AppDispatchers
import template.core.common.coroutines.Dispatcher
import template.core.common.result.Result
import template.core.common.result.asResult
import template.core.data.mapper.toEntity
import template.core.data.mapper.toModel
import template.core.database.dao.PostDao
import template.core.database.model.PostEntity
import template.core.model.Post
import template.core.network.NetworkDataSource
import template.core.network.model.NetworkPost
import javax.inject.Inject

class DefaultPostRepository @Inject constructor(
    private val postDao: PostDao,
    private val networkDataSource: NetworkDataSource,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : PostRepository {

    override fun getPosts(): Flow<Result<List<Post>>> {
        return flow {
            emit(getLocalPosts())
            val networkPosts = networkDataSource.getPosts()
            postDao.insertPosts(networkPosts.map(NetworkPost::toEntity))
            emit(getLocalPosts())
        }
            .asResult()
            .flowOn(ioDispatcher)
    }

    private suspend fun getLocalPosts() = postDao.getPosts().map(PostEntity::toModel)
}
