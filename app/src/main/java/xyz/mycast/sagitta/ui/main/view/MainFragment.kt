package xyz.mycast.sagitta.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import xyz.mycast.sagitta.R
import xyz.mycast.sagitta.ui.main.vm.MainViewModel

open class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var myTokenView: TextView
    private lateinit var toEditText: EditText
    private lateinit var messageEdit: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getMyId().observe(viewLifecycleOwner, { myTokenView.text = it })
        viewModel.getTo().observe(viewLifecycleOwner, { toEditText.setText(it) })

        myTokenView = view.findViewById(R.id.my_token_view)

        val copyButton = view.findViewById<Button>(R.id.button_copy)
        copyButton.setOnClickListener { onCopyClick() }

        toEditText = view.findViewById(R.id.to_edit)
        toEditText.addTextChangedListener(
            onTextChanged = { text, _, _, _ -> onToTextChanged(text) })

        messageEdit = view.findViewById(R.id.edit_message)
        val sendButton: Button = view.findViewById(R.id.button_send)
        sendButton.setOnClickListener { onSendClick() }
    }

    override fun onStart() {
        super.onStart()
        with(viewModel) {
            loadToken()
            loadToPref(requireContext())
        }
    }

    private fun onCopyClick() {
        val token = myTokenView.text
        viewModel.copyTokenToClipboard(requireContext(), token)
        Toast.makeText(requireContext(), "$token copied", Toast.LENGTH_SHORT).show()
    }

    private fun onToTextChanged(text: CharSequence?) {
        viewModel.saveToPref(requireActivity(), text)
    }

    private fun onSendClick() {
        val body = messageEdit.text.toString()
        val to = toEditText.text.toString()
        viewModel.sendNotificationMessage(requireContext(), to, body)
    }

}