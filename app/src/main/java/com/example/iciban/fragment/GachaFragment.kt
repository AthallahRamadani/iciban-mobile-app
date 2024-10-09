package com.example.iciban.fragment

import android.animation.Animator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.cooltechworks.views.ScratchImageView
import com.example.iciban.R
import com.example.iciban.data.model.ActionFigure
import com.example.iciban.data.util.ActionFigureSeeder
import com.example.iciban.databinding.FragmentGachaBinding
import com.example.iciban.fragment.items.ItemListAdapter
import com.example.iciban.fragment.selectbanner.SelectBannerViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class GachaFragment : Fragment() {

    private var _binding: FragmentGachaBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SelectBannerViewModel by activityViewModel()
    private lateinit var scratchImageView: ScratchImageView
    private val selectBannerViewModel: SelectBannerViewModel by activityViewModel()
    lateinit var actionFig: ActionFigure


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGachaBinding.inflate(inflater, container, false)
        binding.btnFinish.setOnClickListener{
            findNavController().navigate(R.id.action_gachaFragment_to_selectFragment)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        scratchImageView = binding.scratchView
        scratchImageView.setRevealListener(object : ScratchImageView.IRevealListener {
            override fun onRevealed(iv: ScratchImageView?) {
                // Implement your logic here when the image is fully revealed
            }

            override fun onRevealPercentChangedListener(siv: ScratchImageView, percent: Float) {
                if (percent > 0.7) {
                    binding.tiket.visibility = View.INVISIBLE
                    binding.imgActionFig.visibility = View.INVISIBLE
                    binding.scratchView.visibility = View.INVISIBLE
                    binding.startAnimation.visibility = View.VISIBLE

                    if (::actionFig.isInitialized && actionFig.rating > 4.0) {
                        binding.startAnimation.setAnimation(R.raw.animation_5_star)
                    } else if (::actionFig.isInitialized && actionFig.rating > 3.0) {
                        binding.startAnimation.setAnimation(R.raw.animation_4_star)
                    } else if (::actionFig.isInitialized && actionFig.rating > 2.0) {
                        binding.startAnimation.setAnimation(R.raw.animation_3_star)
                    } else if (::actionFig.isInitialized && actionFig.rating > 1.0) {
                        binding.startAnimation.setAnimation(R.raw.animation_2_star)
                    } else {
                        binding.startAnimation.setAnimation(R.raw.animation_1_star)
                    }
                    binding.startAnimation.playAnimation()



                }
            }


        })

        val args: GachaFragmentArgs by navArgs()
        val totalPull = args.totalPull
        binding.txTotalPull.text = "total pull = $totalPull"
        viewModel.bannerSelected.observe(viewLifecycleOwner) { selectedBanner ->
            Log.d("TAG", "onViewCreated: $selectedBanner")
        }
        binding.startAnimation.visibility = View.GONE

        binding.startAnimation.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                binding.startAnimation.visibility = View.GONE
                binding.imgActionFig.visibility = View.VISIBLE
                binding.titleActionFig.visibility = View.VISIBLE
                binding.btnFinish.visibility = View.VISIBLE

            }
        })
        setUp()
        observeBannerSelection()
    }

    private fun setUp() {
        binding.imgActionFig.setImageResource(R.drawable.img_token)
        binding.imgActionFig.visibility = View.INVISIBLE
        binding.btnFinish.visibility = View.INVISIBLE


    }

    private fun loadActionFigures(category: String): ActionFigure {
        var actionFigure = ActionFigureSeeder.getRandomActionFigByCategory(category)

        return actionFigure;
    }

    private fun observeBannerSelection() {
        selectBannerViewModel.bannerSelected.observe(viewLifecycleOwner) { selectedBanner ->
            binding.txCategory.text = selectedBanner
            actionFig = loadActionFigures(selectedBanner)
            if (::actionFig.isInitialized) {
                when {
                    actionFig.rating >= 4.9f -> binding.scratchView.setImageResource(R.drawable.start_5)
                    actionFig.rating >= 3.9f -> binding.scratchView.setImageResource(R.drawable.start_4)
                    actionFig.rating >= 2.9f -> binding.scratchView.setImageResource(R.drawable.start_3)
                    actionFig.rating >= 1.9f -> binding.scratchView.setImageResource(R.drawable.start_2)
                    else -> binding.scratchView.setImageResource(R.drawable.start_1)
                }
            } else {
                binding.scratchView.setImageResource(R.drawable.start_1)
            }
            if (actionFig != null) {
                binding.imgActionFig.setImageResource(actionFig.imageResId)
                binding.titleActionFig.text = actionFig.title

            }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}