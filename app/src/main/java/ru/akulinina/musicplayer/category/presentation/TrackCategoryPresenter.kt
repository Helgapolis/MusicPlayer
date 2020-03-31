package ru.akulinina.musicplayer.category.presentation

class TrackCategoryPresenter(private var model: String) {
    private var view: TrackCategoryView? = null
    private var mediator: TrackCategoryMediator? = null

    fun attachView(view: TrackCategoryView) {
        this.view = view

        this.view?.setName(model)
    }

    fun detachView() {
        view = null
    }

    fun attachRouter(mediator: TrackCategoryMediator) {
        this.mediator = mediator
    }

    fun detachRouter() {
        this.mediator = null
    }

    fun onTrackCategoryClick() {
        mediator?.onCategoryClicked(model)
    }
}