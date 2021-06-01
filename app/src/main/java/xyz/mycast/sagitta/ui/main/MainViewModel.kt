package xyz.mycast.sagitta.ui.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import xyz.mycast.sagitta.MainActivity

class MainViewModel : ViewModel() {

    private val myId: MutableLiveData<String> = MutableLiveData("")
    private val to: MutableLiveData<String> = MutableLiveData("")

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
            myId.value = token;
        })
    }

    fun loadToPref(context: Context) {
        val cachePref = context.getSharedPreferences(
            "xyz.mycast.sagitta.cache",
            Context.MODE_PRIVATE
        )
        to.value = cachePref.getString("to", "")
    }
}