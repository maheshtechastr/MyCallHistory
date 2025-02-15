package com.mpg.mycallhistory.ui.notifications

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.CallLog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mpg.mycallhistory.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private lateinit var adapter : CallLogAdapter
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textNotifications
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = CallLogAdapter()
        recyclerView.adapter = adapter

        notificationsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        // Check permissions and fetch call logs
        checkPermissions()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val REQUEST_CODE_READ_CALL_LOG = 100

    private fun checkPermissions() {
        this.activity?.let {
            if (ContextCompat.checkSelfPermission(
                    it.baseContext,
                    Manifest.permission.READ_CALL_LOG
                )
                != PackageManager.PERMISSION_GRANTED
            ) {

                ActivityCompat.requestPermissions(
                    it,
                    arrayOf(Manifest.permission.READ_CALL_LOG),
                    REQUEST_CODE_READ_CALL_LOG
                )

            } else {
                fetchCallLogs()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_READ_CALL_LOG && grantResults.isNotEmpty()
            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            fetchCallLogs()
        }
    }

    @SuppressLint("MissingPermission")
    private fun fetchCallLogs() : List<CallLogItem> {
        val callLogs = mutableListOf<CallLogItem>()

        val cursor = requireContext().contentResolver.query(
            CallLog.Calls.CONTENT_URI,
            null,
            null,
            null,
            CallLog.Calls.DATE + " DESC"
        )

        cursor?.use {
            val numberIndex = it.getColumnIndex(CallLog.Calls.NUMBER)
            val typeIndex = it.getColumnIndex(CallLog.Calls.TYPE)
            val dateIndex = it.getColumnIndex(CallLog.Calls.DATE)
            val durationIndex = it.getColumnIndex(CallLog.Calls.DURATION)

            while (it.moveToNext()) {
                val number = it.getString(numberIndex)
                val type = it.getInt(typeIndex)
                val date = it.getLong(dateIndex)
                val duration = it.getLong(durationIndex)

                callLogs.add(CallLogItem(number, type, date, duration))
            }
        }

        // Update RecyclerView with callLogs
        adapter.setItems(callLogs)
        return callLogs
    }

}