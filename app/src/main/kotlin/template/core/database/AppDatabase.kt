package template.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import template.core.database.dao.PostDao
import template.core.database.model.PostEntity
import template.core.database.utils.InstantConverter

@Database(
    entities = [
        PostEntity::class
    ],
    version = 1,
    autoMigrations = [],
    exportSchema = false,
)
@TypeConverters(
    InstantConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
}
