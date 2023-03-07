package xyz.mycast.sagitta.provider

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.util.Log
import android.widget.RemoteViews
import xyz.mycast.sagitta.R
import java.util.*

class SgtDayCountWidgetProvider : AppWidgetProvider() {

    companion object {
        private const val TAG = "SGT/SgtDayCountWidgetProvider"
    }

    override fun onUpdate(
        context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)

        Log.d(TAG, "onUpdate")
        context?.let {
            val remoteViews = RemoteViews(context.packageName, R.layout.day_count_widget)
            val count = getDayCount()
            remoteViews.setTextViewText(R.id.day_count, "+$count")
            appWidgetManager?.partiallyUpdateAppWidget(appWidgetIds, remoteViews)
        }
    }

    private fun getDayCount(): Long {
        val dayInMillis = 1000 * 60 * 60 * 24
        val calendar = Calendar.getInstance()
        val now = calendar.timeInMillis
        calendar.set(2017, 1, 25)
        val target = calendar.timeInMillis
        return (now - target) / dayInMillis + 1
    }
}