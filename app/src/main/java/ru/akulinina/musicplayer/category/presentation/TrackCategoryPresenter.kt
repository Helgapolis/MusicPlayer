package ru.akulinina.musicplayer.category.presentation

class TrackCategoryPresenter(private var model: String) {
    private var view: TrackCategoryView? = null
    private var router: TrackCategoryRouter? = null

    fun attachView(view: TrackCategoryView) {
        this.view = view

        this.view?.setName(model)
    }

    fun detachView() {
        view = null
    }

    fun attachRouter(router: TrackCategoryRouter) {
        this.router = router
    }

    fun detachRouter() {
        this.router = null
    }

    fun onTrackCategoryClick() {
        router?.onCategoryClicked(model)
    }
}