package xyz.mycast.sagitta.ui.main.view

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject
import xyz.mycast.sagitta.BuildConfig

class NotificationRequest(context: Context, to: String, body: String) : JsonObjectRequest(
    URL, getNotification(body, to),
    {
        Log.d(TAG, "success")
        Toast.makeText(context, "success", Toast.LENGTH_SHORT).show()
    },
    {
        Log.e(TAG, "failed")
        Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show()
    }
) {

    companion object {
        private const val TAG = "SGT/NotificationRequest"
        private const val URL = "https://fcm.googleapis.com/fcm/send"
        private const val SERVER_KEY = BuildConfig.FCM_SERVER_KEY

        private const val PARAM_KEY_AUTHORIZATION = "Authorization"
        private const val PARAM_KEY_CONTENT_TYPE = "Content-Type"
        private const val CONTENT_TYPE_APPLICATION_JSON = "application/json"

        private fun getNotification(body: String, to: String): JSONObject {
            val notificationBody = JSONObject()
            notificationBody.put("title", "단춍이")
            notificationBody.put("body", body)
            val notification = JSONObject()
            notification.put("to", to)
            notification.put("priority", "high")
            notification.put("notification", notificationBody)
            return notification
        }
    }

    override fun getHeaders(): MutableMap<String, String> {
        val params: MutableMap<String, String> = HashMap()
        params[PARAM_KEY_AUTHORIZATION] = "key=$SERVER_KEY"
        params[PARAM_KEY_CONTENT_TYPE] = CONTENT_TYPE_APPLICATION_JSON
        return params
    }
}