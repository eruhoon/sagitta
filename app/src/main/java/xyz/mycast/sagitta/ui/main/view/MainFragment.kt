package xyz.mycast.sagitta.ui.main.view

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
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
import com.android.volley.toolbox.Volley
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getMyId().observe(this, {
            myTokenView.text = it
        })
        viewModel.getTo().observe(this, {
            toEditText.setText(it)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
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

    private fun onCopyClick() {
        val token = myTokenView.text
        val clipboard =
            requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("token", token)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(requireContext(), "$token copied", Toast.LENGTH_SHORT).show()
    }

    private fun onToTextChanged(text: CharSequence?) {
        viewModel.saveToPref(requireActivity(), text)
    }

    private fun onSendClick() {
        val body = messageEdit.text
        val to = toEditText.text
        val queue = Volley.newRequestQueue(context)
        val context = requireContext()
        val jsonObjectRequest = NotificationRequest(context, to, body)
        queue.add(jsonObjectRequest)
    }

    override fun onStart() {
        super.onStart()
        with(viewModel) {
            loadToken()
            loadToPref(requireContext())
        }
    }

}