package com.example.iciban.fragment.items

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.iciban.data.util.ActionFigureSeeder
import com.example.iciban.databinding.FragmentItemsListBinding
import com.example.iciban.fragment.selectbanner.SelectBannerViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class ItemsListFragment : Fragment() {
    private var _binding: FragmentItemsListBinding? = null
    private val binding get() = _binding!!
    private lateinit var itemListAdapter: ItemListAdapter

    private val selectBannerViewModel: SelectBannerViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemsListBinding.inflate(inflater, container, false)

        binding.btnPullOne.setOnClickListener {
            val action = ItemsListFragmentDirections.actionItemsListFragmentToGachaFragment(totalPull = 1)
            findNavController().navigate(action)
        }
        binding.btnPullFive.setOnClickListener {
            val action = ItemsListFragmentDirections.actionItemsListFragmentToGachaFragment(totalPull = 5)
            findNavController().navigate(action)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeBannerSelection()
    }

    private fun setupRecyclerView() {
        binding.rvActionFigure.layoutManager = GridLayoutManager(requireContext(), 2)
        itemListAdapter = ItemListAdapter(requireContext(), emptyList()) // Start with empty list
        binding.rvActionFigure.adapter = itemListAdapter
    }

    private fun observeBannerSelection() {
        selectBannerViewModel.bannerSelected.observe(viewLifecycleOwner) { selectedBanner ->
            binding.titleCategory.text = selectedBanner
            loadActionFigures(selectedBanner)
        }
    }

    private fun loadActionFigures(category: String) {
        val actionFigures = ActionFigureSeeder.getActionFigByCategory(category)
        itemListAdapter.updateData(actionFigures)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}