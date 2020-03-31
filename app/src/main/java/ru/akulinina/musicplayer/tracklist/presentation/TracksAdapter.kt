package ru.akulinina.musicplayer.tracklist.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.akulinina.musicplayer.R
import ru.akulinina.musicplayer.tracklist.dto.Track

class TracksAdapter(private val trackItemRouter: TrackItemRouter) : RecyclerView.Adapter<TrackViewHolder>() {

    var tracks: List<Track> = listOf()
        set(value) {
            field = value
            presenters.clear()

            for (track in tracks) {
                val presenter = TrackPresenter(track)
                presenters.add(presenter)
            }

            notifyDataSetChanged()
        }

    var presenters: MutableList<TrackPresenter> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.track_row_item, parent, false)

        return TrackViewHolder(view)
    }

    override fun getItemCount(): Int {
        return presenters.size
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        if (presenters.size <= position) {
            return
        }

        val presenter = presenters[position]
        presenter.attachView(holder)
        presenter.attachRouter(trackItemRouter)

        holder.onFinishInflate(presenter)
    }
}