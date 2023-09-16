package com.example.marvelapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.marvelapp.databinding.ActivityMainBinding
import com.example.marvelapp.view.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewPagerAdapter by lazy { ViewPagerAdapter(supportFragmentManager, lifecycle) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewPagerAdapter()
        initTabLayout()
    }

    private fun initViewPagerAdapter() {
        binding.viewPager.apply {
            adapter = viewPagerAdapter
        }
    }

    private fun initTabLayout() {
        binding.tabLayout.apply {
            TabLayoutMediator(this, binding.viewPager) { tab, position ->
                tab.text = when (position) {
                    0 -> "Search"
                    else -> "Favorite"
                }
            }.attach()
        }
    }
}
