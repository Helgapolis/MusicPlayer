package ru.akulinina.musicplayer.tracklist.data

import ru.akulinina.musicplayer.tracklist.dto.Track

class MainTracksRepository(private val api : TrackApiService) : TracksRepository {

    override suspend fun getTracks(query: String): List<Track>? {

        val response = api.getTracksAsync(query).await()
        val videoResponse = response.body()
        return videoResponse?.results?.tracks?.toList()
    }
}