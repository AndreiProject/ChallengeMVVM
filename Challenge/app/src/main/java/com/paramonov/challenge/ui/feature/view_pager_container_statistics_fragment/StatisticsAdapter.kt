package com.paramonov.challenge.ui.feature.view_pager_container_statistics_fragment

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.paramonov.challenge.ui.feature.all_statistics_fragment.AllStatisticsFragment
import com.paramonov.challenge.ui.feature.user_statistics.UserStatisticsFragment

class StatisticsAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> UserStatisticsFragment()
        1 -> AllStatisticsFragment()
        else -> UserStatisticsFragment()
    }
}