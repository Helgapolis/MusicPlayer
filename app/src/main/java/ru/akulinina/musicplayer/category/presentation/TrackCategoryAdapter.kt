package ru.akulinina.musicplayer.category.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.akulinina.musicplayer.R

class TrackCategoryAdapter(private val trackCategoryMediator: TrackCategoryMediator?): RecyclerView.Adapter<TrackCategoryViewHolder>() {

    @Suppress("SENSELESS_COMPARISON")
    var tracks: List<String> = listOf()
        set(value) {
            field = value
            presenters.clear()

            for (track in tracks) {
                if (track == null) {
                    continue
                }

                val presenter = TrackCategoryPresenter(track)
                presenters.add(presenter)
            }

            notifyDataSetChanged()
        }

    private var presenters: MutableList<TrackCategoryPresenter> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackCategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.track_category_row_item, parent, false)

        return TrackCategoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return presenters.size
    }

    override fun onBindViewHolder(holder: TrackCategoryViewHolder, position: Int) {
        if (presenters.size <= position) {
            return
        }

        val presenter = presenters[position]
        presenter.attachView(holder)
        if (trackCategoryMediator != null) {
            presenter.attachRouter(trackCategoryMediator)
        }

        holder.onFinishInflate(presenter)
    }
}