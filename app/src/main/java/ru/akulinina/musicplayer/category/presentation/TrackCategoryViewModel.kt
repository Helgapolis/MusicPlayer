package ru.akulinina.musicplayer.category.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import ru.akulinina.musicplayer.category.domain.AddCategoryUseCase
import ru.akulinina.musicplayer.category.domain.GetCategoriesUseCase
import ru.akulinina.musicplayer.category.dto.Category
import kotlin.coroutines.CoroutineContext

class TrackCategoryViewModel(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val addCategoryUseCase: AddCategoryUseCase
) : ViewModel(){

    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + parentJob

    private val scope = CoroutineScope(coroutineContext)

    private var router: TrackCategoryRouter? = null

    var categoriesLiveData = MutableLiveData<List<Category>>()

    fun attachRouter(router: TrackCategoryRouter) {
        this.router = router
    }

    fun detachRouter() {
        this.router = null
    }

    fun fetchCategories(){
        scope.launch {
            withContext(Dispatchers.IO) {

                val categories = getCategoriesUseCase.invoke()
                categoriesLiveData.postValue(categories)
            }
        }
    }

    fun cancelAllRequests() = coroutineContext.cancel()

    fun addCategory(query: String) {
        scope.launch {
            withContext(Dispatchers.IO) {

                val category = addCategoryUseCase.invoke(query)
                if (category != null) {
                    val categories = categoriesLiveData.value?.toMutableList() ?: mutableListOf()
                    categories.add(category)
                    categoriesLiveData.postValue(categories)
                }
                else {
                    withContext(Dispatchers.Main) {
                        router?.showAddCategoryErrorDialog()
                    }
                }
            }
        }
    }
}