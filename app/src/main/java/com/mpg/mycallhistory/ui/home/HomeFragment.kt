package com.mpg.mycallhistory.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mpg.mycallhistory.data.network.ApiService
import com.mpg.mycallhistory.data.network.SampleRepository
import com.mpg.mycallhistory.databinding.FragmentHomeBinding
import com.mpg.mycallhistory.ui.base.BaseViewModelFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val recyclerView = binding.rvCallHistory
        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = RvAdapter()
        recyclerView.adapter = adapter
        // Initialize ViewModel
        val apiService = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
        val repository = SampleRepository(apiService)
        val homeViewModel =
            ViewModelProvider(this, BaseViewModelFactory(repository))[HomeViewModel::class.java]

        homeViewModel.list.observe(viewLifecycleOwner) {
            println("List Items=>" + it.size)
            adapter.setItems(it)
        }
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}