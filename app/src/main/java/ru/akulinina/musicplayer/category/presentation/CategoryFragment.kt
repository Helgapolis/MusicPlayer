package ru.akulinina.musicplayer.category.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.EditText
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.getInputField
import com.afollestad.materialdialogs.input.input
import ru.akulinina.musicplayer.AppDatabase
import ru.akulinina.musicplayer.R
import ru.akulinina.musicplayer.category.data.MainTrackCategoryRepository
import ru.akulinina.musicplayer.category.domain.AddCategoryMainUseCase
import ru.akulinina.musicplayer.category.domain.GetCategoriesMainUseCase
import ru.akulinina.musicplayer.category.presentation.*
import ru.akulinina.musicplayer.category.presentation.model.CategoryItem
import ru.akulinina.musicplayer.databinding.FragmentTrackCategoryBinding

class CategoryFragment : Fragment(), TrackCategoryRouter {

    private var composeView: ComposeView? = null
    
    private var trackCategoryMediator: TrackCategoryMediator? = null

    private lateinit var trackCategoryViewModel: TrackCategoryViewModel
    private lateinit var contentAdapter: TrackCategoryAdapter

    private var _binding: FragmentTrackCategoryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var addCategoryMenuItem: MenuItem? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        trackCategoryMediator = context as? TrackCategoryMediator
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       // _binding = FragmentTrackCategoryBinding.inflate(layoutInflater)
       // return _binding?.root
        composeView = ComposeView(requireContext())
        return composeView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*val layoutManager = LinearLayoutManager(view.context)
        val fragmentTrackCategoryContentList = binding.fragmentTrackCategoryContentList
        fragmentTrackCategoryContentList.layoutManager = layoutManager
        val dividerItemDecoration = DividerItemDecoration(view.context, layoutManager.orientation)
        fragmentTrackCategoryContentList.addItemDecoration(dividerItemDecoration)

        contentAdapter = TrackCategoryAdapter(trackCategoryMediator)
        fragmentTrackCategoryContentList.adapter = */
        val db = AppDatabase.getDatabase(view.context)

        val repository = MainTrackCategoryRepository(view.context, db)
        val getCategoriesUseCase = GetCategoriesMainUseCase(repository)
        val addCategoryUseCase = AddCategoryMainUseCase(repository)
        val viewModelProvider = TrackCategoryViewModelProvider(getCategoriesUseCase, addCategoryUseCase, this.requireActivity().application)
        trackCategoryViewModel = ViewModelProvider(this, viewModelProvider).get(
            TrackCategoryViewModel::class.java)
        trackCategoryViewModel.attachRouter(this)

        trackCategoryViewModel.fetchCategories()
        trackCategoryViewModel.categoriesLiveData.observe(viewLifecycleOwner) {
            composeView?.setContent {
                LazyColumn(modifier = Modifier.fillMaxSize()
                    .background(Color.White)) {
                    itemsIndexed(it.map { it.name }) { _, item ->
                        CategoryRow(categoryItem = CategoryItem(item), mediator = trackCategoryMediator)
                    }
                }
            }
            //contentAdapter.tracks = it.map { it.name }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)

        addCategoryMenuItem = menu.findItem(R.id.add_category)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_category -> {
                showAddCategoryDialog()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("CheckResult")
    private fun showAddCategoryDialog() {
        val context = context ?: return
        val dialog = MaterialDialog(context).show {
            title(R.string.dlg_input_new_category)
            input(allowEmpty = false)
            positiveButton(android.R.string.ok)
            negativeButton(android.R.string.cancel)
        }

        dialog.positiveButton {
            val inputField: EditText = dialog.getInputField()
            val query = inputField.text.toString()
            trackCategoryViewModel.addCategory(query)
        }
    }

    override fun showAddCategoryErrorDialog() {
        val context = context ?: return
        MaterialDialog(context).show {
            message(R.string.dlg_category_name_exists)
            positiveButton(android.R.string.ok)
        }
    }

    override fun onDestroyView() {
        trackCategoryViewModel.cancelAllRequests()
        trackCategoryViewModel.detachRouter()
        _binding = null
        super.onDestroyView()
    }

    override fun onDetach() {
        trackCategoryMediator = null
        super.onDetach()
    }
}