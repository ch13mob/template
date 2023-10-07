package com.ch13mob.template.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.ch13mob.template.core.database.model.PostEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {

    @Query(value = "SELECT * FROM post")
    fun getPosts(): Flow<List<PostEntity>>

    @Query(value = "SELECT * FROM post WHERE id =:id")
    fun getPostById(id: Int): Flow<PostEntity>

    @Upsert
    suspend fun upsertPosts(posts: List<PostEntity>)
}
