package com.example.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.presentation.model.MarvelCharacterItem
import com.example.presentation.R
import com.example.presentation.databinding.ItemViewholderCharacterBinding

class CharacterListAdapter(
    private val itemClickedCallback: (MarvelCharacterItem) -> Unit
) : ListAdapter<MarvelCharacterItem, CharacterListAdapter.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder = ViewHolder(
        ItemViewholderCharacterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int)
    {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemViewholderCharacterBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                itemClickedCallback(getItem(layoutPosition))
            }
        }

        fun bind(item: MarvelCharacterItem) {
            initImage(item.thumbnail)
            initName(item.name)
            initDescription(item.description)
            initBackgroundState(item.isFavorite)
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

        private fun initBackgroundState(isFavorite: Boolean) {
            val backgroundColor = if (isFavorite) R.color.gray else R.color.white
            binding.root.apply {
                setBackgroundColor(ContextCompat.getColor(context, backgroundColor))
            }
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
