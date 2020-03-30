package ru.akulinina.musicplayer.category.data

interface TrackCategoryRepository {
    fun getCategories(): List<String>
}