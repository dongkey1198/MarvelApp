package com.example.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.presentation.R
import com.example.presentation.databinding.ActivityMainBinding
import com.example.presentation.view.adapter.ViewPagerAdapter
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
        initViewPager()
        initTabLayout()
    }

    private fun initViewPager() {
        binding.viewPager.adapter = viewPagerAdapter
    }

    private fun initTabLayout() {
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.label_search)
                else ->  getString(R.string.label_favorite)
            }
        }.attach()
    }
}
