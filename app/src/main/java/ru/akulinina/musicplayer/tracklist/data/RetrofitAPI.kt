package ru.akulinina.musicplayer.tracklist.data

import android.content.Context
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import ru.akulinina.musicplayer.R
import ru.akulinina.musicplayer.tracklist.dto.SearchResponse

object ApiFactory {

    private val okHttpClient = OkHttpClient().newBuilder().build()

    private fun retrofit(context: Context) : Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(context.getString(R.string.base_url))
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    fun getApi(context: Context): TrackApiService {
        return retrofit(context).create(TrackApiService::class.java)
    }
}

interface TrackApiService {
    @GET("search?format=json")
    fun getTracksAsync(@Query("q") query: String): Deferred<Response<SearchResponse>>
}