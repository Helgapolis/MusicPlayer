package ru.akulinina.musicplayer.tracklist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.akulinina.musicplayer.tracklist.domain.GetTrackListUseCase

class TrackListViewModelProvider(private val getTrackListUseCase: GetTrackListUseCase) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TrackListViewModel(getTrackListUseCase) as T
    }
}