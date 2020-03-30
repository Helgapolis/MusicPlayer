package ru.akulinina.musicplayer.tracklist.presentation

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.akulinina.musicplayer.R

class TrackViewHolder(view: View) : RecyclerView.ViewHolder(view), TrackView {

    private var trackPresenter: TrackPresenter? = null
    private var trackRowItemName: TextView = view.findViewById(R.id.trackRowItemName)
    private var trackRowArrow: ImageButton = view.findViewById(R.id.trackRowItemArrow)

    init {
        view.setOnClickListener {
            trackPresenter?.onTrackItemClick()
        }

        trackRowArrow.setOnClickListener {
            trackPresenter?.onTrackItemClick()
        }
    }

    fun onFinishInflate(presenter: TrackPresenter) {
        this.trackPresenter = presenter
    }

    override fun setTrackName(name: String) {
        trackRowItemName.text = name
    }
}