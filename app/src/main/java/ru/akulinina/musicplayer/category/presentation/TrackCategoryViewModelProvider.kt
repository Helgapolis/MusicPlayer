package ru.akulinina.musicplayer.category.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.akulinina.musicplayer.category.data.TrackCategoryRepository

class TrackCategoryViewModelProvider(private val repository: TrackCategoryRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TrackCategoryViewModel(repository) as T
    }
}