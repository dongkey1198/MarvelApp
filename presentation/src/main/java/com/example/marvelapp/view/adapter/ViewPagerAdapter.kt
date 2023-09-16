package com.example.marvelapp.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.marvelapp.view.favorite.FavoriteFragment
import com.example.marvelapp.view.search.SearchFragment
import java.lang.IllegalStateException

class ViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = NUMBER_OF_PAGES

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            POSITION_ZERO -> SearchFragment()
            POSITION_ONE -> FavoriteFragment()
            else -> throw IllegalStateException()
        }
    }

    companion object {
        private const val NUMBER_OF_PAGES = 2
        private const val POSITION_ZERO = 0
        private const val POSITION_ONE = 1
    }
}
