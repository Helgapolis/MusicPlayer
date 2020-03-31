package ru.akulinina.musicplayer.category.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.akulinina.musicplayer.category.domain.AddCategoryUseCase
import ru.akulinina.musicplayer.category.domain.GetCategoriesUseCase

class TrackCategoryViewModelProvider(
    val getCategoriesUseCase: GetCategoriesUseCase,
    val addCategoryUseCase: AddCategoryUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TrackCategoryViewModel(getCategoriesUseCase, addCategoryUseCase) as T
    }
}