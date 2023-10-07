package com.ch13mob.template.core.data.repository

import com.ch13mob.template.core.data.Syncable
import com.ch13mob.template.core.model.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository : Syncable {
    fun getPosts(): Flow<List<Post>>
}
