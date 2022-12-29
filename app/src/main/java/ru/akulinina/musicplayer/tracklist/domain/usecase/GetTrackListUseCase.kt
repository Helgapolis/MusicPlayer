package ru.akulinina.musicplayer.tracklist.domain.usecase

import ru.akulinina.musicplayer.tracklist.data.ResultWrapper
import ru.akulinina.musicplayer.tracklist.dto.Track

interface GetTrackListUseCase {
    suspend fun invoke(query: String): ResultWrapper<List<Track>>
}