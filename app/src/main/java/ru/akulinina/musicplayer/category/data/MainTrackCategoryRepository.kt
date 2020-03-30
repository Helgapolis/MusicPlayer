package ru.akulinina.musicplayer.category.data

import android.content.Context
import ru.akulinina.musicplayer.R

class MainTrackCategoryRepository(private val context: Context) : TrackCategoryRepository {

    override fun getCategories(): List<String> {
        return context.resources.getStringArray(R.array.category_list).asList()
    }
}