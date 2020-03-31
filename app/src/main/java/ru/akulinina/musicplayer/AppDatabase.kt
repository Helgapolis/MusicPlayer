package ru.akulinina.musicplayer

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.akulinina.musicplayer.category.data.CategoryDao
import ru.akulinina.musicplayer.category.data.CategoryDatabase
import ru.akulinina.musicplayer.category.dto.Category

@Database(entities = [Category::class], version = 1)
abstract class AppDatabase : RoomDatabase(), CategoryDatabase {
    abstract override fun getCategoryDao() : CategoryDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getDatabase(context: Context) : AppDatabase {
            val instFirst = instance
            if (instFirst == null) {
                synchronized(AppDatabase::class.java) {
                    val instSecond = instance
                    if (instSecond == null) {
                        val db = Room.databaseBuilder(context.applicationContext,
                            AppDatabase::class.java, "music.db")
                            .build()

                        instance = db
                        return db
                    }
                    else return instSecond
                }
            }
            else return instFirst
        }
    }
}