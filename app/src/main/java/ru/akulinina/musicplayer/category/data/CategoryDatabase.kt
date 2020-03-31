package ru.akulinina.musicplayer.category.data

interface CategoryDatabase {
    fun getCategoryDao() : CategoryDao
}