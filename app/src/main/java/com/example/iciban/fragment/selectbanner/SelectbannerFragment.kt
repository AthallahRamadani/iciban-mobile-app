package com.example.iciban.fragment.selectbanner

import android.animation.ObjectAnimator
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.BounceInterpolator
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.iciban.R
import com.example.iciban.adapter.ImageSelectAdapter
import com.example.iciban.data.model.Category
import com.example.iciban.databinding.FragmentSelectBinding
import com.example.iciban.utils.ArcLayoutManager
import com.example.iciban.utils.ItemDragDownHelper
import com.example.iciban.utils.RvState
import com.example.iciban.utils.SnapOnScrollListener
import org.koin.androidx.viewmodel.ext.android.activityViewModel


class SelectbannerFragment : Fragment() {

    private var _binding: FragmentSelectBinding? = null
    private val binding get() = _binding!!
    private lateinit var snapHelper: LinearSnapHelper
    private lateinit var layoutManager: ArcLayoutManager
    private val rvState = MutableLiveData(RvState())
    private val rewards = listOf(
        Category(1, "Bleach", R.drawable.banner_bleach),
        Category(2, "Naruto", R.drawable.banner_naturo),
        Category(3, "One Piece", R.drawable.banner_half_piece)
    )

    private val viewModel: SelectBannerViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectBinding.inflate(inflater, container, false)
        setupArrowAnimation()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvState.value = RvState()
        binding.rv.smoothScrollToPosition(0)
        setupRecyclerView()
        setupArrowAnimation()
        observeRvState()
    }

    private fun observeRvState() {
        rvState.observe(viewLifecycleOwner) { state ->
            updateUIBasedOnState(state)

            if (state.progress > 0.5) {
                if (state.snapPosition >= 0 && state.snapPosition < rewards.size) {
                    if (!state.isLongPressed) {
                        val rewardCategory = rewards.map { it.name }
                        viewModel.bannerSelected.value = rewardCategory[state.snapPosition]
                        Log.d("TAG", "observeRvState: ${viewModel.bannerSelected.value}")
                        navigateToAnotherFragment()
                    }
                } else {
                    resetToInitialState()
                }
            }

            layoutManager.scrollEnabled = !state.isLongPressed
        }
    }

    private fun resetToInitialState() {
        rvState.value = RvState()
        binding.rv.smoothScrollToPosition(0)

        binding.tvDragDown.setTextColor(requireContext().getColor(R.color.grey))
        binding.tvDragDown.alpha = 1f
        binding.tvMain.setTextColor(requireContext().getColor(R.color.black))
        binding.tvMain.text = getString(R.string.choose_your_action_figure_banner)
    }

    private fun updateUIBasedOnState(state: RvState) {
        val context = requireContext()

        binding.tvDragDown.apply {
            setTextColor(context.getColor(if (!state.isLongPressed) R.color.grey else R.color.primary))
            alpha = if (state.progress > 0.5) 0f else 1f
        }

        binding.tvMain.apply {
            if (state.progress > 0.5) {
                setTextColor(context.getColor(R.color.primary))
                text =
                    getString(if (state.isLongPressed) R.string.release_to_choose_banner else R.string.loading)
            } else {
                setTextColor(context.getColor(R.color.black))
                text = getString(R.string.choose_your_action_figure_banner)
            }
        }
    }

    private fun setupArrowAnimation() {
        ObjectAnimator.ofFloat(binding.doubleArrow, "translationY", 0f, 50f).apply {
            interpolator = BounceInterpolator()
            repeatMode = ObjectAnimator.REVERSE
            repeatCount = ObjectAnimator.INFINITE
            duration = 2000
            start()
        }
    }

    private fun setupRecyclerView() {
        val screenWidth = Resources.getSystem().displayMetrics.widthPixels
        val pad = resources.getDimension(R.dimen.item_spacing).toInt() - 100000
        val selectBoxPadFraction = 0.25f
        val selectBoxPad = (selectBoxPadFraction * pad).toInt()
        val extraPad = pad - selectBoxPad
        val viewWidth = (screenWidth / 1)
        val viewHeight = (1.25f * viewWidth).toInt()

        val effViewWidth = viewWidth - 2 * extraPad
        effViewWidth.toFloat() / (effViewWidth - 2 * selectBoxPad)
        val rewardImages = rewards.map { it.imageResId }


        binding.rv.apply {
            adapter = ImageSelectAdapter(rewardImages, viewWidth, viewHeight)
            layoutManager = ArcLayoutManager(resources, screenWidth, viewWidth, viewHeight).also {
                this@SelectbannerFragment.layoutManager = it
            }
        }

        setupSnapHelper()
    }


    private fun setupSnapHelper() {
        snapHelper = LinearSnapHelper().apply {
            attachToRecyclerView(binding.rv)
        }

        val snapOnScrollListener = SnapOnScrollListener(snapHelper)
        binding.rv.addOnScrollListener(snapOnScrollListener)
        snapOnScrollListener.snapPosition.observe(viewLifecycleOwner) { position ->
            rvState.value = rvState.value?.copy(snapPosition = position)

            if (position != RecyclerView.NO_POSITION) {
                binding.rv.smoothScrollToPosition(position)
            }
        }

        ItemDragDownHelper(requireContext(), rvState, 0.5f, 1f).attachToRv(binding.rv)
    }

    private fun navigateToAnotherFragment() {
        val action =
            SelectbannerFragmentDirections.actionSelectFragmentToItemsListFragment()
        findNavController().navigate(action)
    }

    override fun onResume() {
        super.onResume()
        binding.rv.smoothScrollBy(1, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}