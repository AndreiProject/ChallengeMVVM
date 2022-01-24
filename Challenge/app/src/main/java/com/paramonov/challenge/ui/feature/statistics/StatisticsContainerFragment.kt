package com.paramonov.challenge.ui.feature.statistics

import android.view.*
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.google.android.material.tabs.TabLayoutMediator
import com.paramonov.challenge.R
import com.paramonov.challenge.databinding.FragmentStatisticsContainerBinding
import com.paramonov.challenge.ui.feature.main.NavigationView
import com.paramonov.challenge.ui.feature.main.ToolbarContract
import com.paramonov.challenge.ui.utils.getNavController

class StatisticsContainerFragment : Fragment(), NavigationView.Item, LifecycleObserver {
    private var binding: FragmentStatisticsContainerBinding? = null
    private val mBinding get() = binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentStatisticsContainerBinding.inflate(layoutInflater, container, false)
        .also {
            binding = it
            val viewPager = mBinding.viewPager
            viewPager.adapter = StatisticsAdapter(this)

            val tabLayout = mBinding.tabLayout
            val tabName = resources.getStringArray(R.array.tab_name_statistics)
            val tabImage = intArrayOf(
                R.drawable.ic_user_statistics,
                R.drawable.ic_all_statistics
            )
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = tabName[position]
                tab.setIcon(tabImage[position])
            }.attach()

            requireActivity().lifecycle.addObserver(this)
        }.root

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun activityOnCreated() {
        val root = requireActivity() as? ToolbarContract
        root?.setTitleToolbar(R.string.nav_statistics)
    }

    override fun navigateToCollection() {
        getNavController().navigate(R.id.action_generalStatisticsFragment_to_collectionFragment)
    }

    override fun navigateToCategoryList() {
        getNavController().navigate(R.id.action_generalStatisticsFragment_to_categoryListFragment)
    }

    override fun navigateToPlanner() {
        getNavController().navigate(R.id.action_generalStatisticsFragment_to_plannerFragment)
    }

    override fun navigateToSettings() {
        getNavController().navigate(R.id.action_generalStatisticsFragment_to_settingsFragment)
    }

    override fun navigateToStatistics() {}

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.viewPager?.adapter = null
        requireActivity().lifecycle.removeObserver(this)
        binding = null
    }
}