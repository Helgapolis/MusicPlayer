package ru.akulinina.musicplayer.tracklist.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import ru.akulinina.musicplayer.tracklist.data.ResultWrapper
import ru.akulinina.musicplayer.tracklist.domain.GetTrackListUseCase
import ru.akulinina.musicplayer.tracklist.dto.Track
import kotlin.coroutines.CoroutineContext

class TrackListViewModel(private val getTrackListUseCase: GetTrackListUseCase) : ViewModel() {

    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + parentJob

    private val scope = CoroutineScope(coroutineContext)

    private var router: TrackListRouter? = null

    val tracksLiveData = MutableLiveData<List<Track>>()

    fun attachRouter(router: TrackListRouter) {
        this.router = router
    }

    fun detachRouter() {
        this.router = null
    }

    fun fetchTracks(query: String) {
        scope.launch {
            withContext(Dispatchers.IO) {

                when (val trackListResponse = getTrackListUseCase.invoke(query)) {
                    is ResultWrapper.Success -> {
                        withContext(Dispatchers.Main) {
                            val trackList = trackListResponse.value
                            tracksLiveData.postValue(trackList)

                            router?.hideProgressBar()
                            router?.showContentList()
                        }
                    }

                    is ResultWrapper.NetworkError -> withContext(Dispatchers.Main) {
                        router?.hideProgressBar()
                        router?.showNetworkErrorDialog()
                    }

                    is ResultWrapper.Error -> withContext(Dispatchers.Main) {
                        router?.hideProgressBar()
                        router?.showResponseErrorDialog(
                            trackListResponse.code,
                            trackListResponse.error.toString()
                        )
                    }
                }
            }
        }
    }

    fun cancelAllRequests() = coroutineContext.cancel()
}