package com.ch13mob.template.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ch13mob.template.core.database.dao.PostDao
import com.ch13mob.template.core.database.model.PostEntity
import com.ch13mob.template.core.database.utils.InstantConverter

@Database(
    entities = [
        PostEntity::class
    ],
    version = 1,
    autoMigrations = [],
    exportSchema = true,
)
@TypeConverters(
    InstantConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
}
