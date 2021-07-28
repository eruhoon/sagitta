package xyz.mycast.sagitta.ui.main.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import xyz.mycast.sagitta.R
import xyz.mycast.sagitta.ui.main.data.Preset

class PresetListAdapter(private val presets: List<Preset>) :
    RecyclerView.Adapter<PresetListAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.preset_list_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val preset = presets[position]
        holder.bind(preset)
    }

    override fun getItemCount(): Int {
        return presets.size
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.title)
        private val bodyTextView: TextView = itemView.findViewById(R.id.body)

        fun bind(preset: Preset) {
            titleTextView.text = preset.title
            bodyTextView.text = preset.body
        }
    }
}