package ru.akulinina.musicplayer.category.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_track_category.*
import ru.akulinina.musicplayer.R
import ru.akulinina.musicplayer.category.data.MainTrackCategoryRepository

class CategoryFragment : Fragment() {

    private var trackCategoryRouter: TrackCategoryRouter? = null

    private lateinit var trackCategoryViewModel: TrackCategoryViewModel
    private lateinit var contentAdapter: TrackCategoryAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)

        trackCategoryRouter = context as? TrackCategoryRouter
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_track_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(view.context)
        fragmentTrackCategoryContentList.layoutManager = layoutManager
        val dividerItemDecoration = DividerItemDecoration(view.context, layoutManager.orientation)
        fragmentTrackCategoryContentList.addItemDecoration(dividerItemDecoration)

        contentAdapter = TrackCategoryAdapter(trackCategoryRouter)
        fragmentTrackCategoryContentList.adapter = contentAdapter
        val viewModelProvider = TrackCategoryViewModelProvider(MainTrackCategoryRepository(view.context))
        trackCategoryViewModel = ViewModelProvider(this, viewModelProvider).get(TrackCategoryViewModel::class.java)

        trackCategoryViewModel.fetchCategories()
        trackCategoryViewModel.categoriesLiveData.observe(viewLifecycleOwner, Observer {
            contentAdapter.tracks = it
        })
    }

    override fun onDestroyView() {
        trackCategoryViewModel.cancelAllRequests()
        super.onDestroyView()
    }

    override fun onDetach() {
        trackCategoryRouter = null
        super.onDetach()
    }
}