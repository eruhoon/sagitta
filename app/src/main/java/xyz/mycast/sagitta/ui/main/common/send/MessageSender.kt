package xyz.mycast.sagitta.ui.main.common.send

import android.content.Context
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import xyz.mycast.sagitta.ui.main.view.NotificationRequest

class MessageSender {

    companion object {
        private const val TAG = "SGT/MessageSender"
    }

    fun sendNotificationMessage(
        context: Context,
        to: String,
        body: String,
        listener: Response.Listener<JSONObject>,
        errorListener: Response.ErrorListener,
    ) {
        val queue = Volley.newRequestQueue(context)
        val jsonObjectRequest = NotificationRequest(to, body, listener, errorListener)
        queue.add(jsonObjectRequest)
    }
}