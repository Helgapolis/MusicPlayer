package ru.akulinina.musicplayer.track.presentation

import android.os.Bundle
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import ru.akulinina.musicplayer.databinding.ActivityTrackBinding
import ru.akulinina.musicplayer.tracklist.dto.Track

class TrackVideoActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {

    companion object{
        const val KEY_INTENT_TRACK = "key_intent_track"
    }

    private lateinit var binding: ActivityTrackBinding
    private lateinit var trackId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTrackBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val track = intent?.getSerializableExtra(KEY_INTENT_TRACK) as? Track
        val trackIdItems = track?.eId?.split("/")
        trackId = trackIdItems?.last() ?: ""

        binding.activityTrackYouTubeView.initialize(DeveloperKey.DEVELOPER_KEY, this)
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