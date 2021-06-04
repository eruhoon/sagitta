package xyz.mycast.sagitta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import xyz.mycast.sagitta.ui.main.view.MainFragment

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "SGT/MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }
}