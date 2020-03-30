package ru.akulinina.musicplayer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import ru.akulinina.musicplayer.category.presentation.TrackCategoryRouter
import ru.akulinina.musicplayer.tracklist.presentation.TrackListFragment

class MainActivity : AppCompatActivity(), TrackCategoryRouter {

    private var navigationController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigationController = (supportFragmentManager.findFragmentById(R.id.activityMainNavigationFragment) as? NavHostFragment)?.navController
    }

    override fun onCategoryClicked(trackCategory: String) {
        val navController = navigationController ?: return

        val bundle = Bundle()
        bundle.putString(TrackListFragment.KEY_BUNDLE_QUERY, trackCategory)
        navController.navigate(R.id.trackListFragment, bundle)
    }
}