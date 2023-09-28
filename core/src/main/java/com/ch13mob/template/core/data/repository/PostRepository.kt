package com.ch13mob.template.core.data.repository

import com.ch13mob.template.core.model.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    suspend fun fetchPosts()
    fun getPosts(): Flow<List<Post>>
}
