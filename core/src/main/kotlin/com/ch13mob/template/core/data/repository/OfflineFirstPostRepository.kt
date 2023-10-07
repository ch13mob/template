package com.ch13mob.template.core.data.repository

import com.ch13mob.template.core.data.Synchronizer
import com.ch13mob.template.core.data.mapper.toEntity
import com.ch13mob.template.core.data.mapper.toModel
import com.ch13mob.template.core.data.sync
import com.ch13mob.template.core.database.dao.PostDao
import com.ch13mob.template.core.database.model.PostEntity
import com.ch13mob.template.core.model.Post
import com.ch13mob.template.core.network.NetworkDataSource
import com.ch13mob.template.core.network.model.NetworkPost
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OfflineFirstPostRepository @Inject constructor(
    private val postDao: PostDao,
    private val networkDataSource: NetworkDataSource
) : PostRepository {

    override fun getPosts(): Flow<List<Post>> {
        return postDao.getPosts().map { postEntities ->
            return@map postEntities.map(PostEntity::toModel)
        }
    }

    override suspend fun syncWith(synchronizer: Synchronizer): Boolean {
        return synchronizer.sync(
            updater = {
                val networkPosts = networkDataSource.getPosts()
                postDao.upsertPosts(networkPosts.map(NetworkPost::toEntity))
            }
        )
    }
}
