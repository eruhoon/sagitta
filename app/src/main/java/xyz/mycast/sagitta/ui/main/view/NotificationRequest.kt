package xyz.mycast.sagitta.ui.main.view

import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject
import xyz.mycast.sagitta.BuildConfig

class NotificationRequest(
    to: String,
    title: String,
    body: String,
    listener: Response.Listener<JSONObject>,
    errorListener: Response.ErrorListener,
) : JsonObjectRequest(
    URL, getNotification(title, body, to), listener, errorListener
) {

    companion object {
        private const val URL = "https://fcm.googleapis.com/fcm/send"
        private const val SERVER_KEY = BuildConfig.FCM_SERVER_KEY

        private const val PARAM_KEY_AUTHORIZATION = "Authorization"
        private const val PARAM_KEY_CONTENT_TYPE = "Content-Type"
        private const val CONTENT_TYPE_APPLICATION_JSON = "application/json"

        private fun getNotification(title: String, body: String, to: String): JSONObject {
            val notificationBody = JSONObject()
            notificationBody.put("title", title)
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