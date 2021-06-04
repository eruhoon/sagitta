package xyz.mycast.sagitta.ui.main.common.preference

import android.content.Context

class PreferenceManager {

    companion object {
        private const val PREF_NAME_CACHE = "xyz.mycast.sagitta.cache"
        private const val PREF_KEY_TO = "to"
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

}