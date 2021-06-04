package xyz.mycast.sagitta.provider

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import xyz.mycast.sagitta.R
import xyz.mycast.sagitta.ui.main.common.preference.PreferenceManager
import xyz.mycast.sagitta.ui.main.common.send.MessageSender


class SgtAppWidgetProvider : AppWidgetProvider() {

    companion object {
        private const val TAG = "SGT/AppWidgetProvider"
        private const val ACTION_BTN = "BUTTON_CLICK"
    }

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?,
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)

        if (context !== null) {
            val remoteViews = RemoteViews(context.packageName, R.layout.app_widget)
            val intent = Intent(context, SgtAppWidgetProvider::class.java).setAction(ACTION_BTN)
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
            remoteViews.setOnClickPendingIntent(R.id.button_send, pendingIntent)

            appWidgetManager?.updateAppWidget(appWidgetIds, remoteViews)
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)

        Log.i(TAG, "onReceive: ${intent?.action}")
        when (intent?.action) {
            ACTION_BTN -> context?.let { onButtonClick(it) }
        }
    }

    private fun onButtonClick(context: Context) {
        val to = PreferenceManager().loadToPref(context)
        Log.i(TAG, "onButtonClick: to: $to")
        MessageSender().sendNotificationMessage(context, to, "이건 위젯에서 보내는 메시지")
    }
}