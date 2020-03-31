package ru.akulinina.musicplayer.tracklist.domain

import ru.akulinina.musicplayer.tracklist.data.TracksRepository
import ru.akulinina.musicplayer.tracklist.dto.Track

class GetTrackListMainUseCase(private val repository: TracksRepository) : GetTrackListUseCase {

    @Suppress("UselessCallOnCollection")
    override suspend fun invoke(query: String): List<Track> {
        return repository.getTracks(query)?.filterNotNull() ?: listOf()
    }
}