package template.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import template.core.database.model.PostEntity

@Dao
interface PostDao {

    @Query(value = "SELECT * FROM post")
    suspend fun getPosts(): List<PostEntity>

    @Query(value = "SELECT * FROM post WHERE id =:id")
    fun getPostById(id: Int): Flow<PostEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(posts: List<PostEntity>)
}
