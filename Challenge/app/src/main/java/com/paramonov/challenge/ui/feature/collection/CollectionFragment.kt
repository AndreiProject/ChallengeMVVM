package com.paramonov.challenge.ui.feature.collection

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.*
import androidx.lifecycle.*
import androidx.recyclerview.widget.RecyclerView
import com.paramonov.challenge.R
import com.paramonov.challenge.databinding.FragmentCollectionBinding
import com.paramonov.challenge.data.repository.model.Category
import com.paramonov.challenge.ui.feature.main.ToolbarContract
import org.koin.android.viewmodel.ext.android.viewModel

class CollectionFragment : Fragment(), LifecycleObserver {
    private var binding: FragmentCollectionBinding? = null
    private val mBinding get() = binding!!

    private lateinit var rvCollection: RecyclerView

    private val model: CollectionViewModel by viewModel()
    private val observer = Observer<List<Category>> {
        (rvCollection.adapter as CollectionAdapter).run {
            categories = it
            notifyDataSetChanged()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentCollectionBinding.inflate(layoutInflater, container, false)
        .also {
            binding = it
            rvCollection = mBinding.collectionRv
            rvCollection.setHasFixedSize(true)
            rvCollection.adapter = CollectionAdapter()

            model.categories.observe(viewLifecycleOwner, observer)
            requireActivity().lifecycle.addObserver(this)
        }.root

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun activityOnCreated() {
        val root = requireActivity() as? ToolbarContract
        root?.setTitleToolbar(R.string.nav_collection)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        model.categories.removeObserver(observer)
        requireActivity().lifecycle.removeObserver(this)
        binding = null
    }
}