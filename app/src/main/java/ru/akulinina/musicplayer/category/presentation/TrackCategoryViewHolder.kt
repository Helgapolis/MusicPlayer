package ru.akulinina.musicplayer.category.presentation

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.akulinina.musicplayer.R

class TrackCategoryViewHolder(view: View) : RecyclerView.ViewHolder(view), TrackCategoryView {

    private var trackCategoryPresenter: TrackCategoryPresenter? = null
    private var trackCategoryRowItemName: TextView = view.findViewById(R.id.trackCategoryRowItemName)
    private var trackCategoryRowItemArrow: ImageButton = view.findViewById(R.id.trackCategoryRowItemArrow)

    init {
        view.setOnClickListener {
            trackCategoryPresenter?.onTrackCategoryClick()
        }

        trackCategoryRowItemArrow.setOnClickListener {
            trackCategoryPresenter?.onTrackCategoryClick()
        }
    }

    fun onFinishInflate(presenter: TrackCategoryPresenter) {
        this.trackCategoryPresenter = presenter
    }

    override fun setName(name: String) {
        trackCategoryRowItemName.text = name
    }
}