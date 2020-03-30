package ru.akulinina.musicplayer.tracklist.presentation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_track_list.*
import ru.akulinina.musicplayer.R
import ru.akulinina.musicplayer.track.presentation.TrackVideoActivity
import ru.akulinina.musicplayer.tracklist.data.ApiFactory
import ru.akulinina.musicplayer.tracklist.data.MainTracksRepository
import ru.akulinina.musicplayer.tracklist.domain.GetTrackListMainUseCase
import ru.akulinina.musicplayer.tracklist.dto.Track

class TrackListFragment : Fragment(), TrackItemRouter {

    companion object {
        const val KEY_BUNDLE_QUERY = "key_bundle_query"
    }

    private lateinit var trackListViewModel: TrackListViewModel
    private lateinit var contentAdapter: TracksAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_track_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val query = arguments?.getString(KEY_BUNDLE_QUERY) ?: ""

        val layoutManager = LinearLayoutManager(view.context)
        fragmentTrackListContentList.layoutManager = layoutManager
        val dividerItemDecoration = DividerItemDecoration(view.context, layoutManager.orientation)
        fragmentTrackListContentList.addItemDecoration(dividerItemDecoration)

        contentAdapter = TracksAdapter(this)
        fragmentTrackListContentList.adapter = contentAdapter
        val viewModelProvider = TrackListViewModelProvider(
            GetTrackListMainUseCase(MainTracksRepository(ApiFactory.getApi(view.context)))
        )
        trackListViewModel = ViewModelProvider(this, viewModelProvider).get(TrackListViewModel::class.java)

        trackListViewModel.fetchTracks(query)
        trackListViewModel.tracksLiveData.observe(viewLifecycleOwner, Observer {
            contentAdapter.tracks = it
        })
    }

    override fun onTrackClick(track: Track) {
        val intent = Intent(activity, TrackVideoActivity::class.java)
        intent.putExtra(TrackVideoActivity.KEY_INTENT_TRACK, track)
        startActivity(intent)
    }

    override fun onDestroyView() {
        trackListViewModel.cancelAllRequests()
        super.onDestroyView()
    }
}