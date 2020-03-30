package ru.akulinina.musicplayer.category.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import ru.akulinina.musicplayer.category.data.TrackCategoryRepository
import kotlin.coroutines.CoroutineContext

class TrackCategoryViewModel(private val repository: TrackCategoryRepository) : ViewModel(){

    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + parentJob

    private val scope = CoroutineScope(coroutineContext)

    val categoriesLiveData = MutableLiveData<List<String>>()

    fun fetchCategories(){
        scope.launch {
            withContext(Dispatchers.IO) {

                val categories = repository.getCategories()
                categoriesLiveData.postValue(categories)
            }
        }
    }

    fun cancelAllRequests() = coroutineContext.cancel()
}