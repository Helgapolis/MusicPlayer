package ru.akulinina.musicplayer.category.domain

import ru.akulinina.musicplayer.category.dto.Category

interface GetCategoriesUseCase {
    suspend fun invoke(): List<Category>
}