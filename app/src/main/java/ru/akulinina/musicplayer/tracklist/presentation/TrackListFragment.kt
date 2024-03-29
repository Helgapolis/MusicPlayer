package ru.akulinina.musicplayer.tracklist.presentation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import ru.akulinina.musicplayer.R
import ru.akulinina.musicplayer.databinding.FragmentTrackListBinding
import ru.akulinina.musicplayer.track.presentation.TrackVideoActivity
import ru.akulinina.musicplayer.tracklist.data.ApiFactory
import ru.akulinina.musicplayer.tracklist.data.MainTracksRepository
import ru.akulinina.musicplayer.tracklist.domain.usecase.GetTrackListMainUseCase
import ru.akulinina.musicplayer.tracklist.dto.Track

class TrackListFragment : Fragment(), TrackItemRouter, TrackListRouter {

    companion object {
        const val KEY_BUNDLE_QUERY = "key_bundle_query"
    }

    private lateinit var trackListViewModel: TrackListViewModel
    private lateinit var contentAdapter: TracksAdapter

    private var _binding: FragmentTrackListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTrackListBinding.inflate(layoutInflater)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val query = arguments?.getString(KEY_BUNDLE_QUERY) ?: ""

        val layoutManager = LinearLayoutManager(view.context)
        val fragmentTrackListContentList = binding.fragmentTrackListContentList
        fragmentTrackListContentList.layoutManager = layoutManager
        val dividerItemDecoration = DividerItemDecoration(view.context, layoutManager.orientation)
        fragmentTrackListContentList.addItemDecoration(dividerItemDecoration)

        contentAdapter = TracksAdapter(this)
        fragmentTrackListContentList.adapter = contentAdapter
        val viewModelProvider = TrackListViewModelProvider(
            GetTrackListMainUseCase(MainTracksRepository(ApiFactory.getApi(view.context))), this.requireActivity().application)
        trackListViewModel = ViewModelProvider(this, viewModelProvider).get(TrackListViewModel::class.java)
        trackListViewModel.attachRouter(this)

        trackListViewModel.fetchTracks(query)
        trackListViewModel.tracksLiveData.observe(viewLifecycleOwner) {
            contentAdapter.tracks = it
        }
    }

    override fun onTrackClick(track: Track) {
        val intent = Intent(activity, TrackVideoActivity::class.java)
        intent.putExtra(TrackVideoActivity.KEY_INTENT_TRACK, track)
        startActivity(intent)
    }

    override fun showProgressBar() {
        binding.fragmentTrackListProgressBar.show()
    }

    override fun hideProgressBar() {
        binding.fragmentTrackListProgressBar.hide()
    }

    override fun showContentList() {
        binding.fragmentTrackListContentList.isVisible = true
    }

    override fun showNetworkErrorDialog() {
        val context = context ?: return
        MaterialDialog(context).show {
            title(R.string.dlg_network_error)
            positiveButton(android.R.string.ok)
        }
    }

    override fun showResponseErrorDialog(code: Int?, cause: String) {
        val context = context ?: return
        MaterialDialog(context).show {
            title(R.string.dlg_network_error)
            positiveButton(android.R.string.ok)
        }
    }

    override fun onDestroyView() {
        trackListViewModel.cancelAllRequests()
        trackListViewModel.detachRouter()
        _binding = null
        super.onDestroyView()
    }
}