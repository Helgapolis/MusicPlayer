package ru.akulinina.musicplayer.tracklist.presentation

import ru.akulinina.musicplayer.tracklist.dto.Track

interface TrackItemRouter {
    fun onTrackClick(track: Track)
}