package xyz.mycast.sagitta.ui.main.common.preference

import android.content.Context

class PreferenceManager {

    companion object {
        private const val PREF_NAME_CACHE = "xyz.mycast.sagitta.cache"
        private const val PREF_KEY_TO = "to"
        private const val PREF_KEY_TITLE = "title"
        private const val PREF_KEY_BODY = "body"
    }

    fun loadToPref(context: Context): String {
        val cachePref = context.getSharedPreferences(PREF_NAME_CACHE, Context.MODE_PRIVATE)
        return cachePref.getString("to", "") ?: ""
    }

    fun saveToPref(context: Context, text: CharSequence?) {
        val cachePref =
            context.getSharedPreferences(PREF_NAME_CACHE, Context.MODE_PRIVATE)
        with(cachePref.edit()) {
            putString(PREF_KEY_TO, text.toString())
            apply()
        }
    }

    fun loadTitlePref(context: Context): String {
        val cachePref = context.getSharedPreferences(PREF_NAME_CACHE, Context.MODE_PRIVATE)
        return cachePref.getString(PREF_KEY_TITLE, "") ?: ""
    }

    fun saveTitlePref(context: Context, text: CharSequence?) {
        val cachePref =
            context.getSharedPreferences(PREF_NAME_CACHE, Context.MODE_PRIVATE)
        with(cachePref.edit()) {
            putString(PREF_KEY_TITLE, text.toString())
            apply()
        }
    }

    fun loadBodyPref(context: Context): String {
        val cachePref = context.getSharedPreferences(PREF_NAME_CACHE, Context.MODE_PRIVATE)
        return cachePref.getString(PREF_KEY_BODY, "") ?: ""
    }

    fun saveBodyPref(context: Context, text: CharSequence?) {
        val cachePref =
            context.getSharedPreferences(PREF_NAME_CACHE, Context.MODE_PRIVATE)
        with(cachePref.edit()) {
            putString(PREF_KEY_BODY, text.toString())
            apply()
        }
    }

}