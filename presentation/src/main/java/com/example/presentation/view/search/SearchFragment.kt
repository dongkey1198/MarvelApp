package com.example.presentation.view.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.presentation.model.MarvelCharacterItem
import com.example.presentation.extension.ViewExtensions.setVisibility
import com.example.presentation.view.adapter.CharacterListAdapter
import com.example.presentation.viewmodel.search.SearchViewModel
import com.example.presentation.databinding.FragmentSearchBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModels()
    private val characterListAdapter by lazy { CharacterListAdapter(itemClickedCallback) }
    private val itemClickedCallback: (MarvelCharacterItem) -> Unit = {
        viewModel.characterItemClicked(it)
    }

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
        initSnackBar()
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
            itemAnimator = null
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = (layoutManager as GridLayoutManager)
                    val lastItemPosition = layoutManager.findLastCompletelyVisibleItemPosition()
                    if (lastItemPosition == layoutManager.itemCount - 1) {
                        val query = binding.editTextSearch.text?.toString() ?: ""
                        viewModel.performSearch(query)
                    }
                }
            })
        }
        lifecycleScope.launch(Dispatchers.Main) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.marvelCharacterItemsFlow.collect { items ->
                    characterListAdapter.submitList(items)
                }
            }
        }
    }

    private fun initProgress() {
        lifecycleScope.launch(Dispatchers.Main) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.progressStateFlow.collect { isProgressing ->
                    binding.progressBarSearch.setVisibility(isProgressing)
                }
            }
        }
    }

    private fun initSnackBar() {
        lifecycleScope.launch(Dispatchers.Main) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.resultMessageFlow.collect { message ->
                    Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
