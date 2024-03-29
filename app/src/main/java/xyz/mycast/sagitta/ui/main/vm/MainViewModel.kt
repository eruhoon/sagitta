package xyz.mycast.sagitta.ui.main.vm

import android.content.Context
import android.util.Log
import android.widget.Toast
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

    companion object {
        private const val TAG = "SGT/MainViewModel"
    }

    private val myId: MutableLiveData<String> = MutableLiveData("")
    private val to: MutableLiveData<String> = MutableLiveData("")
    private val title: MutableLiveData<String> = MutableLiveData("")
    private val body: MutableLiveData<String> = MutableLiveData("")

    fun getMyId(): LiveData<String> {
        return myId
    }

    fun getTo(): LiveData<String> {
        return to
    }

    fun getTitle(): LiveData<String> {
        return title
    }

    fun getBody(): LiveData<String> {
        return body
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

    fun loadTitlePref(context: Context) {
        title.value = PreferenceManager().loadTitlePref(context)
    }

    fun saveTitlePref(context: Context, text: CharSequence?) {
        PreferenceManager().saveTitlePref(context, text)
    }

    fun loadBodyPref(context: Context) {
        body.value = PreferenceManager().loadBodyPref(context)
    }

    fun saveBodyPref(context: Context, text: CharSequence?) {
        PreferenceManager().saveBodyPref(context, text)
    }

    fun copyTokenToClipboard(context: Context, token: CharSequence?) {
        ClipboardHelper().copyToClipboard(context, token)
    }

    fun sendNotificationMessage(context: Context, to: String, title: String, body: String) {
        MessageSender().sendNotificationMessage(context, to, title, body, {
            Log.d(TAG, "success")
            Toast.makeText(context, "전송!", Toast.LENGTH_SHORT).show()
        }, {
            Log.e(TAG, "failed")
            Toast.makeText(context, "전송 실패 ㅠㅠ", Toast.LENGTH_SHORT).show()
        })
    }
}