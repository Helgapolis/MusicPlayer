package ru.akulinina.musicplayer.category.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.akulinina.musicplayer.category.domain.AddCategoryUseCase
import ru.akulinina.musicplayer.category.domain.GetCategoriesUseCase

class TrackCategoryViewModelProvider(
    val getCategoriesUseCase: GetCategoriesUseCase,
    val addCategoryUseCase: AddCategoryUseCase,
    application: Application
) : ViewModelProvider.AndroidViewModelFactory(application) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TrackCategoryViewModel(getCategoriesUseCase, addCategoryUseCase) as T
    }
}