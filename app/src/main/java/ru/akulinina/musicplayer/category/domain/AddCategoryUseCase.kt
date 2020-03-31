package ru.akulinina.musicplayer.category.domain

import ru.akulinina.musicplayer.category.dto.Category

interface AddCategoryUseCase {
    suspend fun invoke(categoryName: String): Category?
}