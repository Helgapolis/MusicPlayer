package ru.akulinina.musicplayer.category.data

import ru.akulinina.musicplayer.category.dto.Category

interface TrackCategoryRepository {
    suspend fun getCategories(): List<Category>
    suspend fun hasCategory(category: String) : Boolean
    suspend fun addCategory(category: String): Category?
}