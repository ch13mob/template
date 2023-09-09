package com.ch13mob.template.core.data.repository

import com.ch13mob.template.core.data.mapper.toEntity
import com.ch13mob.template.core.data.mapper.toModel
import com.ch13mob.template.core.database.dao.PostDao
import com.ch13mob.template.core.database.model.PostEntity
import com.ch13mob.template.core.model.Post
import com.ch13mob.template.core.network.NetworkDataSource
import com.ch13mob.template.core.network.model.NetworkPost
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultPostRepository @Inject constructor(
    private val postDao: PostDao,
    private val networkDataSource: NetworkDataSource
) : PostRepository {

    override suspend fun fetchPosts() {
        val posts = networkDataSource.getPosts()
        postDao.insertPosts(posts.map(NetworkPost::toEntity))
    }

    override fun getPosts(): Flow<List<Post>> {
        return postDao.getPosts().map { postEntities ->
            if (postEntities.isEmpty()) return@map emptyList<Post>()

            return@map postEntities.map(PostEntity::toModel)
        }
    }
}
