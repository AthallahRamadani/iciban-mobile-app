package com.example.iciban.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.iciban.R
import com.example.iciban.adapter.ImageSelectAdapter
import com.example.iciban.databinding.FragmentGachaBinding
import com.example.iciban.databinding.FragmentHomeBinding
import com.example.iciban.databinding.FragmentSelectBinding


class SelectFragment : Fragment() {

    private var _binding: FragmentSelectBinding? = null
    private val binding get() = _binding!!
    private lateinit var imageSelectAdapter: ImageSelectAdapter
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val images = listOf(
            R.drawable.dragon_ball,
            R.drawable.naruto_logo,
            R.drawable.kiss_sis_logo,
            R.drawable.high_school_logo,
            R.drawable.boku_no_pico_logo
        )

        imageSelectAdapter = ImageSelectAdapter(images)
        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.rvTitleAnime.apply {
            this.layoutManager = this@SelectFragment.layoutManager
            adapter = imageSelectAdapter

            PagerSnapHelper().attachToRecyclerView(this)
        }

        binding.btnGacha.setOnClickListener {
            val visiblePosition = layoutManager.findFirstVisibleItemPosition()

            val selectedImage = images[visiblePosition]

            val action = SelectFragmentDirections.actionSelectFragmentToGachaFragment(selectedImage)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
