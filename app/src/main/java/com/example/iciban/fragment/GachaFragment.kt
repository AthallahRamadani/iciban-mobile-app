package com.example.iciban.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.cooltechworks.views.ScratchImageView
import com.example.iciban.databinding.FragmentGachaBinding
import com.example.iciban.fragment.selectbanner.SelectBannerViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class GachaFragment : Fragment() {

    private var _binding: FragmentGachaBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SelectBannerViewModel by activityViewModel()
    private lateinit var scratchImageView: ScratchImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGachaBinding.inflate(inflater, container, false)
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
                if (percent > 0.5){
                    Toast.makeText(context, "Up to 50 percent!", Toast.LENGTH_SHORT).show()
                }
            }
        })

        val args: GachaFragmentArgs by navArgs()
        val totalPull = args.totalPull
        binding.txTotalPull.text = "total pull = $totalPull"
        viewModel.bannerSelected.observe(viewLifecycleOwner) { selectedBanner ->
            Log.d("TAG", "onViewCreated: $selectedBanner")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}