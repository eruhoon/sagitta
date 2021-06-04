package xyz.mycast.sagitta.ui.main.model

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

class ClipboardHelper {

    companion object {
        private const val LABEL_TOKEN = "token"
    }

    fun copyToClipboard(context: Context, token: CharSequence?) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(LABEL_TOKEN, token)
        clipboard.setPrimaryClip(clip)
    }
}