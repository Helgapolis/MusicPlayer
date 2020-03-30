package ru.akulinina.musicplayer.tracklist.data

import ru.akulinina.musicplayer.tracklist.dto.Track

interface TracksRepository {
    suspend fun getTracks(query: String): List<Track>?
}