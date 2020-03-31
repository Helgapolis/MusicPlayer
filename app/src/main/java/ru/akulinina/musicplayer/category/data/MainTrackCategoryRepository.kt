package ru.akulinina.musicplayer.category.data

import android.content.Context
import ru.akulinina.musicplayer.R
import ru.akulinina.musicplayer.category.dto.Category

class MainTrackCategoryRepository(
    private val context: Context,
    private val db: CategoryDatabase) : TrackCategoryRepository {

    override suspend fun getCategories(): List<Category> {
        val categoryDao = db.getCategoryDao()

        if (categoryDao.getCount() == 0)
        {
            val defaultCategories = context.resources.getStringArray(R.array.category_list)
            for (category in defaultCategories)
            {
                addCategory(category)
            }
        }

        return categoryDao.getAll()
    }

    override suspend fun hasCategory(category: String): Boolean {
        return db.getCategoryDao().findCategoty(category) != null
    }

    override suspend fun addCategory(category: String): Category? {
        db.getCategoryDao().insert(category)
        return db.getCategoryDao().findCategoty(category)
    }
}