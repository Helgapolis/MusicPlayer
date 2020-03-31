package ru.akulinina.musicplayer.category.domain

import ru.akulinina.musicplayer.category.data.TrackCategoryRepository
import ru.akulinina.musicplayer.category.dto.Category

class AddCategoryMainUseCase(private val repository: TrackCategoryRepository) : AddCategoryUseCase {

    override suspend fun invoke(categoryName: String): Category? {
        if (repository.hasCategory(categoryName)) {
            return null
        }

        return repository.addCategory(categoryName)
    }
}