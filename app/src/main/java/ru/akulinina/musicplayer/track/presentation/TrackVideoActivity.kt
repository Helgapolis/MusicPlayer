package ru.akulinina.musicplayer.track.presentation

import android.os.Bundle
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import kotlinx.android.synthetic.main.activity_track.*
import ru.akulinina.musicplayer.R
import ru.akulinina.musicplayer.tracklist.dto.Track

class TrackVideoActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {

    companion object{
        const val KEY_INTENT_TRACK = "key_intent_track"
    }

    private lateinit var trackId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_track)

        val track = intent?.getSerializableExtra(KEY_INTENT_TRACK) as? Track
        val trackIdItems = track?.eId?.split("/")
        trackId = trackIdItems?.last() ?: ""

        activityTrackYouTubeView.initialize(DeveloperKey.DEVELOPER_KEY, this)
    }

    override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider?,
        youTubePlayer: YouTubePlayer?,
        b: Boolean
    ) {
        youTubePlayer?.loadVideo(trackId)
        youTubePlayer?.play()
    }

    override fun onInitializationFailure(
        provider: YouTubePlayer.Provider?,
        youTubePlayer: YouTubeInitializationResult?
    ) {
        /* do nothing */
    }
}