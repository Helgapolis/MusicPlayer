package ru.akulinina.musicplayer.tracklist.domain

import ru.akulinina.musicplayer.tracklist.dto.Track

interface GetTrackListUseCase {
    suspend fun invoke(query: String): List<Track>
}