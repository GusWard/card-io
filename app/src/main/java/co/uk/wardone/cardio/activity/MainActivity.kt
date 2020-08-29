package co.uk.wardone.cardio.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.uk.wardone.cardio.R
import co.uk.wardone.cardio.fragments.home.HomeFragment
import co.uk.wardone.viewmodel.activity.MainActivityViewModel


class MainActivity : AppCompatActivity() {

    companion object {

        private const val TAG = "MainActivity"
    }

    private val mainActivityViewModel = MainActivityViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.enter_from_right,
                R.anim.exit_to_left,
                R.anim.enter_from_left,
                R.anim.exit_to_right
            )
            .replace(R.id.fragmentContainer, HomeFragment())
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()

    }
}