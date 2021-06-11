package xyz.mycast.sagitta.ui.main.common.send

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.toolbox.Volley
import xyz.mycast.sagitta.ui.main.view.NotificationRequest

class MessageSender {

    companion object {
        private const val TAG = "SGT/MessageSender"
    }

    fun sendNotificationMessage(context: Context, to: String, body: String) {
        val queue = Volley.newRequestQueue(context)
        val jsonObjectRequest = NotificationRequest(to, body,
            {
                Log.d(TAG, "success")
                Toast.makeText(context, "success", Toast.LENGTH_SHORT).show()
            }, {
                Log.e(TAG, "failed")
                Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(jsonObjectRequest)
    }
}