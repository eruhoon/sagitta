package xyz.mycast.sagitta.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import xyz.mycast.sagitta.R
import xyz.mycast.sagitta.ui.main.data.Preset
import xyz.mycast.sagitta.ui.main.vm.PresetViewModel

open class PresetListFragment : Fragment() {

    private lateinit var viewModel: PresetViewModel
    private lateinit var listView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.preset_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(PresetViewModel::class.java)
        viewModel.getPresets().observe(viewLifecycleOwner, { updatePresets(it) })


        listView = view.findViewById(R.id.preset_list_view)
        listView.layoutManager = LinearLayoutManager(context)
        listView.adapter = PresetListAdapter(Preset.DEFAULT_LIST)
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadPresets(requireContext())
    }

    private fun updatePresets(presets: List<Preset>) {
        listView.adapter = PresetListAdapter(presets)
    }
}