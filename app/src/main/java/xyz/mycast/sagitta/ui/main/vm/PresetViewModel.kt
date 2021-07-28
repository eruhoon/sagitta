package xyz.mycast.sagitta.ui.main.vm

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import xyz.mycast.sagitta.ui.main.common.preference.PreferenceManager
import xyz.mycast.sagitta.ui.main.data.Preset
import java.util.*

class PresetViewModel : ViewModel() {

    private val presets: MutableLiveData<List<Preset>> = MutableLiveData(ArrayList())

    fun getPresets(): LiveData<List<Preset>> {
        return presets
    }

    fun loadPresets(context: Context) {
        presets.value = PreferenceManager().loadPresetPref(context)
    }
}