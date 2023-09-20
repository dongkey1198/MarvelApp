package com.example.presentation.view.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.marvelapp.extension.ViewExtensions.setVisibility
import com.example.marvelapp.model.MarvelCharacterItem
import com.example.presentation.view.adapter.CharacterListAdapter
import com.example.marvelapp.viewmodel.favorite.FavoriteViewModel
import com.example.presentation.databinding.FragmentFavoriteBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FavoriteViewModel by viewModels()
    private val characterListAdapter by lazy { CharacterListAdapter(itemClickedCallback) }
    private val itemClickedCallback: (MarvelCharacterItem) -> Unit = { marvelCharacterItem ->
        viewModel.characterItemClicked(marvelCharacterItem)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initEmptyMessage()
        initProgressBar()
        initSnackBar()
    }

    private fun initRecyclerView() {
        binding.recyclerViewMarvelFavoriteCharacter.apply {
            adapter = characterListAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
            itemAnimator = null
        }
        lifecycleScope.launch(Dispatchers.Main) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.favoriteMarvelCharacterFlow.collect { favoriteCharacters ->
                    characterListAdapter.submitList(favoriteCharacters)
                    binding.recyclerViewMarvelFavoriteCharacter.setVisibility(favoriteCharacters.isNotEmpty())
                }
            }
        }
    }

    private fun initEmptyMessage() {
        lifecycleScope.launch(Dispatchers.Main) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.emptyMessageStateFlow.collect { isShow ->
                    binding.textViewEmptyMessage.setVisibility(isShow)
                }
            }
        }
    }

    private fun initProgressBar() {
        lifecycleScope.launch(Dispatchers.Main) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.progressStateFlow.collect { isProgressing ->
                    binding.progressBarFavorite.setVisibility(isProgressing)
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
