package ru.akulinina.musicplayer.tracklist.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.akulinina.musicplayer.tracklist.domain.usecase.GetTrackListUseCase

class TrackListViewModelProvider(private val getTrackListUseCase: GetTrackListUseCase,
                                 application: Application
) : ViewModelProvider.AndroidViewModelFactory(application) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TrackListViewModel(getTrackListUseCase) as T
    }
}