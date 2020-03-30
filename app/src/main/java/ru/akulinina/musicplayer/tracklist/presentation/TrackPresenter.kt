package ru.akulinina.musicplayer.tracklist.presentation

import ru.akulinina.musicplayer.tracklist.dto.Track

class TrackPresenter(private val model: Track) {

    private var view: TrackView? = null
    private var router: TrackItemRouter? = null

    fun attachView(view: TrackView) {
        this.view = view

        this.view?.setTrackName(model.name ?: "")
    }

    fun detachView() {
        view = null
    }

    fun attachRouter(router: TrackItemRouter) {
        this.router = router
    }

    fun detachRouter() {
        this.router = null
    }

    fun onTrackItemClick() {
        router?.onTrackClick(model)
    }
}