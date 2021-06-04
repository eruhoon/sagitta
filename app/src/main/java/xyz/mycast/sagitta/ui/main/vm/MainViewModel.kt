package xyz.mycast.sagitta.ui.main.vm

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import xyz.mycast.sagitta.MainActivity
import xyz.mycast.sagitta.ui.main.common.preference.PreferenceManager
import xyz.mycast.sagitta.ui.main.common.send.MessageSender
import xyz.mycast.sagitta.ui.main.model.ClipboardHelper

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
            myId.value = token
        })
    }

    fun loadToPref(context: Context) {
        to.value = PreferenceManager().loadToPref(context)
    }

    fun saveToPref(context: Context, text: CharSequence?) {
        PreferenceManager().saveToPref(context, text)
    }

    fun copyTokenToClipboard(context: Context, token: CharSequence?) {
        ClipboardHelper().copyToClipboard(context, token)
    }

    fun sendNotificationMessage(context: Context, to: String, body: String) {
        MessageSender().sendNotificationMessage(context, to, body)
    }
}