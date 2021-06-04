package xyz.mycast.sagitta.ui.main.vm

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import xyz.mycast.sagitta.MainActivity
import xyz.mycast.sagitta.ui.main.model.ClipboardHelper

class MainViewModel : ViewModel() {

    private val myId: MutableLiveData<String> = MutableLiveData("")
    private val to: MutableLiveData<String> = MutableLiveData("")

    companion object {
        private const val PREF_NAME_CACHE = "xyz.mycast.sagitta.cache"
        private const val PREF_KEY_TO = "to"
    }

    fun getMyId(): LiveData<String> {
        return myId
    }

    fun getTo(): LiveData<String> {
        return to
    }

    fun loadToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(MainActivity.TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            // Get new FCM registration token
            val token = task.result
            myId.value = token
        })
    }

    fun loadToPref(context: Context) {
        val cachePref = context.getSharedPreferences(PREF_NAME_CACHE, Context.MODE_PRIVATE)
        to.value = cachePref.getString("to", "")
    }

    fun saveToPref(context: Context, text: CharSequence?) {
        val cachePref = context.getSharedPreferences(PREF_NAME_CACHE, Context.MODE_PRIVATE)
        with(cachePref.edit()) {
            putString(PREF_KEY_TO, text.toString())
            apply()
        }
    }

    fun copyTokenToClipboard(context: Context, token: CharSequence?) {
        ClipboardHelper().copyToClipboard(context, token)
    }
}