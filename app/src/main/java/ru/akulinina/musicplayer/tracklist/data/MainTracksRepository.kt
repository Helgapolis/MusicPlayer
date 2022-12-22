package ru.akulinina.musicplayer.tracklist.data

import retrofit2.HttpException
import ru.akulinina.musicplayer.tracklist.dto.Track
import java.io.IOException

class MainTracksRepository(private val api : TrackApiService) : TracksRepository {

    override suspend fun getTracks(query: String): ResultWrapper<List<Track>> {

        return try {
            val response = api.getTracksAsync(query).await()
            val videoResponse = response.body()
            val trackList: List<Track?>? = videoResponse?.results?.tracks?.toList()
            ResultWrapper.Success(trackList?.filterNotNull() ?: listOf())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> ResultWrapper.NetworkError
                is HttpException -> {
                    val code = throwable.code()
                    val errorResponse = null//convertErrorBody(throwable)
                    ResultWrapper.Error(code, errorResponse)
                }
                else -> {
                    ResultWrapper.Error(null, null)
                }
            }
        }
    }
}