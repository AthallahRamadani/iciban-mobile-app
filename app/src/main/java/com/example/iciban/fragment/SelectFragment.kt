package com.example.iciban.fragment

import android.animation.ObjectAnimator
import android.content.res.ColorStateList
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.BounceInterpolator
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.iciban.R
import com.example.iciban.adapter.ImageSelectAdapter
import com.example.iciban.databinding.FragmentSelectBinding
import com.example.iciban.utils.ArcLayoutManager
import com.example.iciban.utils.ItemDragDownHelper
import com.example.iciban.utils.RvState
import com.example.iciban.utils.SnapOnScrollListener

class SelectFragment : Fragment() {

    private var _binding: FragmentSelectBinding? = null
    private val binding get() = _binding!!
    private lateinit var snapHelper: LinearSnapHelper
    private lateinit var layoutManager: ArcLayoutManager
    private val rvState = MutableLiveData(RvState())
    private val rewards = listOf(R.drawable.banner_1, R.drawable.banner_2, R.drawable.banner_3)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvState.value = RvState()
        setupRecyclerView()
        setupArrowAnimation()
        observeRvState()
    }

    private fun observeRvState() {
        rvState.observe(viewLifecycleOwner) { state ->
            updateUIBasedOnState(state)

            // Periksa apakah progress cukup tinggi untuk interaksi yang signifikan
            if (state.progress > 0.5) {
                // Pastikan snapPosition valid sebelum digunakan
                if (state.snapPosition >= 0 && state.snapPosition < rewards.size) {
                    if (!state.isLongPressed) {
                        navigateToAnotherFragment(rewards[state.snapPosition])
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
        binding.frame.imageTintList = ColorStateList.valueOf(requireContext().getColor(R.color.primary))
        binding.tvDragDown.setTextColor(requireContext().getColor(R.color.grey))
        binding.tvDragDown.alpha = 1f
        binding.tvMain.setTextColor(requireContext().getColor(R.color.white))
        binding.tvMain.text = getString(R.string.choose_your_action_figure_banner)
    }

    private fun updateUIBasedOnState(state: RvState) {
        val context = requireContext()
        binding.frame.imageTintList = ColorStateList.valueOf(
            context.getColor(if (state.isLongPressed || state.progress > 0.5) R.color.grey else R.color.primary)
        )

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
                setTextColor(context.getColor(R.color.white))
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
        val rvHeight = resources.getDimension(R.dimen.recyclerview_height).toInt()
        val pad = resources.getDimension(R.dimen.item_spacing).toInt()
        val selectBoxPadFraction = 0.5f
        val selectBoxPad = (selectBoxPadFraction * pad).toInt()
        val extraPad = pad - selectBoxPad
        val viewWidth = screenWidth / 2
        val viewHeight = (1.25f * viewWidth).toInt()

        val effViewWidth = viewWidth - 2 * extraPad
        val effViewHeight = viewHeight - 2 * extraPad
        val viewScaleFactor = effViewWidth.toFloat() / (effViewWidth - 2 * selectBoxPad)

        binding.rv.apply {
            adapter = ImageSelectAdapter(rewards, rvState, viewWidth, viewHeight)
            layoutManager = ArcLayoutManager(resources, screenWidth, viewWidth, viewHeight).also {
                this@SelectFragment.layoutManager = it
            }
        }

        adjustFrameLayoutParams(effViewWidth, effViewHeight, rvHeight, extraPad)
        setupSnapHelper()
    }

    private fun adjustFrameLayoutParams(
        effViewWidth: Int,
        effViewHeight: Int,
        rvHeight: Int,
        extraPad: Int
    ) {
        (binding.frame.layoutParams as ConstraintLayout.LayoutParams).apply {
            width = effViewWidth
            height = effViewHeight
            topMargin = rvHeight + extraPad
        }

        binding.glowView.layoutParams.width = effViewWidth
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

    private fun navigateToAnotherFragment(selectedImageResId: Int) {
        val action =
            SelectFragmentDirections.actionSelectFragmentToGachaFragment(selectedImageResId)
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