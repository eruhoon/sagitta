package xyz.mycast.sagitta.ui.main.view

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.util.Log
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
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import xyz.mycast.sagitta.BuildConfig
import xyz.mycast.sagitta.R
import xyz.mycast.sagitta.ui.main.vm.MainViewModel

class MainFragment : Fragment() {

    companion object {
        private const val TAG = "SGT/MainFragment"
        private const val PREF_NAME_CACHE = "xyz.mycast.sagitta.cache"
        private const val URL = "https://fcm.googleapis.com/fcm/send"

        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var myTokenView: TextView
    private lateinit var toEditText: EditText

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
        copyButton.setOnClickListener {
            val token = myTokenView.text;
            val clipboard =
                requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("token", token)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(requireContext(), "$token copied", Toast.LENGTH_SHORT).show()
        }

        toEditText = view.findViewById(R.id.to_edit)
        toEditText.addTextChangedListener(onTextChanged = { text, _, _, _ ->
            val cachePref = requireActivity().getSharedPreferences(
                PREF_NAME_CACHE, Context.MODE_PRIVATE
            )
            with(cachePref.edit()) {
                putString("to", text.toString())
                apply()
            }
        })

        val messageEdit: EditText = view.findViewById(R.id.edit_message)
        val sendButton: Button = view.findViewById(R.id.button_send)
        sendButton.setOnClickListener {
            val body = messageEdit.text
            val to = toEditText.text
            val notification = getNotification(body, to)
            val queue = Volley.newRequestQueue(context)
            val serverKey = BuildConfig.FCM_SERVER_KEY
            val jsonObjectRequest = object : JsonObjectRequest(URL, notification,
                {
                    Log.d(TAG, "success")
                    Toast.makeText(context, "success", Toast.LENGTH_SHORT).show()
                },
                {
                    Log.e(TAG, "failed")
                    Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show()
                }
            ) {
                override fun getHeaders(): MutableMap<String, String> {
                    val params: MutableMap<String, String> = HashMap()
                    params["Authorization"] = "key=$serverKey"
                    params["Content-Type"] = "application/json"
                    return params
                }
            }
            queue.add(jsonObjectRequest)
        }
    }

    private fun getNotification(body: Editable, to: Editable): JSONObject {
        val notificationBody = JSONObject()
        notificationBody.put("title", "단춍이")
        notificationBody.put("body", body)
        val notification = JSONObject()
        notification.put("to", to)
        notification.put("priority", "high")
        notification.put("notification", notificationBody)
        return notification
    }

    override fun onStart() {
        super.onStart()
        with(viewModel) {
            loadToken()
            loadToPref(requireContext())
        }
    }

}