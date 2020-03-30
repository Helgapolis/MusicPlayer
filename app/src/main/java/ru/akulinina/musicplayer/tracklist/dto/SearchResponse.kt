package ru.akulinina.musicplayer.tracklist.dto

import java.io.Serializable

data class SearchResponse(val q: String, val results: SearchResults) : Serializable