package xyz.mycast.sagitta.ui.main.common.send

import android.content.Context
import com.android.volley.toolbox.Volley
import xyz.mycast.sagitta.ui.main.view.NotificationRequest

class MessageSender {

    fun sendNotificationMessage(context: Context, to: String, body: String) {
        val queue = Volley.newRequestQueue(context)
        val jsonObjectRequest = NotificationRequest(context, to, body)
        queue.add(jsonObjectRequest)
    }
}