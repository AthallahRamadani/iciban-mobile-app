package com.example.iciban.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.iciban.databinding.ItemBinding


class ImageSelectAdapter(
    private val rewards: List<Int>,
    val itemWidth: Int,
    val itemHeight: Int
): RecyclerView.Adapter<ImageSelectAdapter.RewardsHolder>() {

    @SuppressLint("ClickableViewAccessibility")
    inner class RewardsHolder(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.layoutParams = binding.root.layoutParams.apply {
                width = itemWidth
                height = itemHeight
            }
        }
        fun bind(id: Int) {
            binding.imageView.setImageDrawable(ContextCompat.getDrawable(itemView.context, id))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RewardsHolder {
        return RewardsHolder(
            ItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RewardsHolder, position: Int) {
        holder.bind(rewards[position])
    }


    override fun getItemCount(): Int {
        return rewards.size
    }
}