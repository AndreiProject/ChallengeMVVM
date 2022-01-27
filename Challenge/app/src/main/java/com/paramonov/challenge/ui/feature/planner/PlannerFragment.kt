package com.paramonov.challenge.ui.feature.planner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.*
import androidx.lifecycle.*
import com.paramonov.challenge.R
import com.paramonov.challenge.databinding.FragmentPlannerBinding
import com.paramonov.challenge.ui.feature.main.ToolbarContract

class PlannerFragment : Fragment(), LifecycleObserver {
    private var binding: FragmentPlannerBinding? = null
    private val mBinding get() = binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentPlannerBinding.inflate(layoutInflater, container, false)
        .also {
            binding = it
            requireActivity().lifecycle.addObserver(this)
        }.root

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun activityOnCreated() {
        val root = requireActivity() as? ToolbarContract
        root?.setTitleToolbar(R.string.nav_planner)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().lifecycle.removeObserver(this)
        binding = null
    }
}