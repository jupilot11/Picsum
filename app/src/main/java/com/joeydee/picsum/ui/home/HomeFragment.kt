package com.joeydee.picsum.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.joeydee.picsum.databinding.FragmentHomeBinding
import com.joeydee.picsum.ui.adapter.LoaderStateAdapter
import com.joeydee.picsum.ui.adapter.PersonsAdapter
import com.joeydee.picsum.ui.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private val adapter = PersonsAdapter()
    private val binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(requireActivity().layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loaderStateAdapter = LoaderStateAdapter { adapter.retry() }

        binding.rvPosts.layoutManager = GridLayoutManager(requireActivity(), 2)
        binding.rvPosts.adapter = adapter.withLoadStateFooter(loaderStateAdapter)
        adapter.onItemClicked = { person ->
            val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(person)
            findNavController().navigate(action)
        }
        lifecycleScope.launch {
            viewModel.result().collect {
                println("${it.size}")
            }
        }
        lifecycleScope.launch {
            viewModel.fetchImages().collectLatest {
                adapter.submitData(it)
            }
        }
    }
}