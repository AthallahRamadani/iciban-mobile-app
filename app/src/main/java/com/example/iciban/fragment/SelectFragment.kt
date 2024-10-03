package com.example.iciban.fragment

import android.animation.ObjectAnimator
import android.content.res.ColorStateList
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.BounceInterpolator
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
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
    private var rvState = MutableLiveData(RvState())

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

        rvState.observe(viewLifecycleOwner) {
            binding.frame.imageTintList = ColorStateList.valueOf(
                requireContext().getColor(
                    if (it.isLongPressed || it.progress > 0.5) R.color.grey else R.color.primary
                )
            )

            binding.tvDragDown.apply {
                setTextColor(requireContext().getColor(if (!it.isLongPressed) R.color.grey else R.color.primary))
                alpha = if (it.progress > 0.5) 0f else 1f
            }

            binding.tvMain.apply {
                if (it.progress > 0.5) {
                    setTextColor(requireContext().getColor(R.color.primary))
                    text = getString(
                        if (it.isLongPressed) R.string.release_to_choose_banner else R.string.activating_offer
                    )
                } else {
                    setTextColor(requireContext().getColor(R.color.white))
                    text = getString(R.string.choose_your_action_figure_banner)
                }
            }

            if (it.progress > 0.5 && !it.isLongPressed) binding.ml.transitionToEnd()

            layoutManager.scrollEnabled = !it.isLongPressed
        }

        initRv(binding.rv)
        initArrowAnim()
    }

    private fun initArrowAnim() {
        val arrowView = binding.doubleArrow
        ObjectAnimator.ofFloat(arrowView, "translationY", 0f, 50f).apply {
            interpolator = BounceInterpolator()
            repeatMode = ObjectAnimator.REVERSE
            repeatCount = ObjectAnimator.INFINITE
            duration = 2000
            start()
        }
    }

    private fun initRv(rv: RecyclerView) {
        val rewards = listOf(R.drawable.naruto_logo, R.drawable.naruto_logo, R.drawable.naruto_logo)

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

        rv.adapter = ImageSelectAdapter(rewards, rvState, viewWidth, viewHeight)
        rv.layoutManager = ArcLayoutManager(resources, screenWidth, viewWidth, viewHeight).apply {
            layoutManager = this
        }

        (binding.frame.layoutParams as ConstraintLayout.LayoutParams).apply {
            width = effViewWidth
            height = effViewHeight
            topMargin = rvHeight + extraPad
        }

        binding.glowView.layoutParams.apply {
            width = effViewWidth
        }

        snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(rv)

        val snapOnScrollListener = SnapOnScrollListener(snapHelper)
        rv.addOnScrollListener(snapOnScrollListener)
        snapOnScrollListener.snapPosition.observe(viewLifecycleOwner) {
            rvState.apply { value = value?.copy(snapPosition = it) }
        }

        ItemDragDownHelper(
            requireContext(),
            rvState,
            0.5f,
            viewScaleFactor
        ).attachToRv(rv)
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
