package ru.akulinina.musicplayer.category.domain

import ru.akulinina.musicplayer.category.data.TrackCategoryRepository
import ru.akulinina.musicplayer.category.dto.Category

class GetCategoriesMainUseCase(private val repository: TrackCategoryRepository) : GetCategoriesUseCase {

    override suspend fun invoke(): List<Category> {
        return repository.getCategories()
    }
}