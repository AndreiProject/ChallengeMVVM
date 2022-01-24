package com.paramonov.challenge.ui.feature.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.*
import androidx.lifecycle.*
import com.paramonov.challenge.R
import com.paramonov.challenge.databinding.FragmentSettingsBinding
import com.paramonov.challenge.ui.feature.main.NavigationView
import com.paramonov.challenge.data.repository.remote.firebase.model.User
import com.paramonov.challenge.ui.feature.main.ToolbarContract
import com.paramonov.challenge.ui.utils.getNavController
import com.paramonov.challenge.ui.utils.isValid
import org.koin.android.viewmodel.ext.android.viewModel

class SettingsFragment : Fragment(), NavigationView.Item, LifecycleObserver {
    private var binding: FragmentSettingsBinding? = null
    private val mBinding get() = binding!!

    private val model: SettingsViewModel by viewModel()
    private val observer = Observer<User> { user ->
        user.let {
            mBinding.name.setText(it.name)
            mBinding.surname.setText(it.surname)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentSettingsBinding.inflate(layoutInflater, container, false)
        .also {
            binding = it
            model.user.observe(viewLifecycleOwner, observer)

            mBinding.update.setOnClickListener {
                updateUser()
            }
            mBinding.name.setOnEditorActionListener { _, _, _ ->
                return@setOnEditorActionListener false
            }
            mBinding.surname.setOnEditorActionListener { _, _, _ ->
                return@setOnEditorActionListener false
            }

            requireActivity().lifecycle.addObserver(this)
        }.root

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun activityOnCreated() {
        val root = requireActivity() as? ToolbarContract
        root?.setTitleToolbar(R.string.nav_settings)
    }

    private fun updateUser() {
        with(mBinding) {
            if (name.isValid(requireContext(), R.string.name_input_warning)) {
                if (surname.isValid(requireContext(), R.string.surname_input_warning)) {
                    model.updateUser(User(name.text.toString(), surname.text.toString()))
                }
            }
        }
    }

    override fun navigateToStatistics() {
        getNavController().navigate(R.id.action_settingsFragment_to_generalStatisticsFragment)
    }

    override fun navigateToCollection() {
        getNavController().navigate(R.id.action_settingsFragment_to_collectionFragment)
    }

    override fun navigateToCategoryList() {
        getNavController().navigate(R.id.action_settingsFragment_to_categoryListFragment)
    }

    override fun navigateToPlanner() {
        getNavController().navigate(R.id.action_settingsFragment_to_plannerFragment)
    }

    override fun navigateToSettings() {}

    override fun onDestroyView() {
        super.onDestroyView()
        model.user.observeForever(observer)
        requireActivity().lifecycle.removeObserver(this)
        binding = null
    }
}