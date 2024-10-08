package com.example.iciban.fragment.items

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.iciban.R
import com.example.iciban.data.model.ActionFigure
import com.example.iciban.databinding.FragmentItemsListBinding
import com.example.iciban.fragment.selectbanner.SelectBannerViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class ItemsListFragment : Fragment() {
    private var _binding: FragmentItemsListBinding? = null
    private val binding get() = _binding!!
    private lateinit var itemListAdapter: ItemListAdapter

    private val selectBannerViewModel: SelectBannerViewModel by activityViewModel()


    private val actionFigure = listOf(
        ActionFigure(
            "Naruto",
            R.drawable.naturo_action_fig,
            5.0f,
            1,
            2,
            "Naruto follows the journey of Naruto Uzumaki, a young ninja striving to gain recognition from his village and become the Hokage. Along the way, he faces numerous enemies, hones his skills, and searches for his true identity.",
            "naturo"
        ), ActionFigure(
            "Naruto",
            R.drawable.naturo_action_fig,
            5.0f,
            1,
            2,
            "Naruto follows the journey of Naruto Uzumaki, a young ninja striving to gain recognition from his village and become the Hokage. Along the way, he faces numerous enemies, hones his skills, and searches for his true identity.",
            "naturo"
        ),
        ActionFigure(
            "Naruto",
            R.drawable.naturo_action_fig,
            5.0f,
            1,
            2,
            "Naruto follows the journey of Naruto Uzumaki, a young ninja striving to gain recognition from his village and become the Hokage. Along the way, he faces numerous enemies, hones his skills, and searches for his true identity.",
            "naturo"
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvActionFigure.layoutManager = GridLayoutManager(requireContext(),2)
        itemListAdapter = ItemListAdapter(requireContext(), actionFigure)
        binding.rvActionFigure.adapter = itemListAdapter

        selectBannerViewModel.bannerSelected.observe(viewLifecycleOwner) { selectedBanner ->
            binding.titleCategory.text = selectedBanner
        }
    }
}