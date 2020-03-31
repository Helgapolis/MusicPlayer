package ru.akulinina.musicplayer.tracklist.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import ru.akulinina.musicplayer.tracklist.domain.GetTrackListUseCase
import ru.akulinina.musicplayer.tracklist.dto.Track
import kotlin.coroutines.CoroutineContext

class TrackListViewModel(private val getTrackListUseCase: GetTrackListUseCase): ViewModel(){

    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + parentJob

    private val scope = CoroutineScope(coroutineContext)

    val tracksLiveData = MutableLiveData<List<Track>>()

    fun fetchTracks(query: String) {
        scope.launch {
            withContext(Dispatchers.IO) {

                val trackList = getTrackListUseCase.invoke(query)
                tracksLiveData.postValue(trackList)
            }
        }
    }

    fun cancelAllRequests() = coroutineContext.cancel()
}