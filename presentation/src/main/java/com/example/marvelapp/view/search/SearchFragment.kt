package com.example.marvelapp.view.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.marvelapp.databinding.FragmentSearchBinding
import com.example.marvelapp.utils.Utils.setVisibility
import com.example.marvelapp.view.search.adapter.CharacterListAdapter
import com.example.marvelapp.viewmodel.search.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val characterListAdapter by lazy { CharacterListAdapter() }
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEditText()
        initRecyclerView()
        initProgress()
    }

    private fun initEditText() {
        binding.editTextSearch.doAfterTextChanged {
            val query = it?.toString() ?: ""
            viewModel.performSearch(query)
        }

    }

    private fun initRecyclerView() {
        binding.recyclerViewMarvelCharacter.apply {
            adapter = characterListAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    private fun initProgress() {
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.progressStateFlow.collect { isProgressing ->
                binding.progressBarSearch.setVisibility(isProgressing)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
