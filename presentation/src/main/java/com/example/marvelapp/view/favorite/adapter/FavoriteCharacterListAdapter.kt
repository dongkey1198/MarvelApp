package com.example.marvelapp.view.favorite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marvelapp.R
import com.example.marvelapp.databinding.ItemViewholderCharacterBinding
import com.example.marvelapp.model.MarvelCharacterItem

class FavoriteCharacterListAdapter : ListAdapter<MarvelCharacterItem, FavoriteCharacterListAdapter.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteCharacterListAdapter.ViewHolder = ViewHolder(
        ItemViewholderCharacterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(
        holder: FavoriteCharacterListAdapter.ViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemViewholderCharacterBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MarvelCharacterItem) {
            initImage(item.thumbnail)
            initName(item.name)
            initDescription(item.description)
        }

        private fun initImage(thumbnail: String) {
            Glide.with(binding.root.context)
                .load(thumbnail)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(binding.imageViewCharacterImage)
        }

        private fun initName(name: String) {
            binding.textViewCharacterName.text = name
        }

        private fun initDescription(description: String) {
            binding.textViewCharacterDescription.text = description
        }
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<MarvelCharacterItem>() {

            override fun areItemsTheSame(
                oldItem: MarvelCharacterItem,
                newItem: MarvelCharacterItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: MarvelCharacterItem,
                newItem: MarvelCharacterItem
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}
