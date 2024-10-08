package com.example.iciban.fragment.items

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.iciban.R
import com.example.iciban.data.model.ActionFigure

class ItemListAdapter(
    private val context : Context,
    private val actionFigures: List<ActionFigure>
) : RecyclerView.Adapter<ItemListAdapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivActionFigure: ImageView = itemView.findViewById(R.id.iv_action_figure_item)
        val tvTitle: TextView = itemView.findViewById(R.id.title_action_fig_item)
        val ratingBar: RatingBar = itemView.findViewById(R.id.rating_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_layout_action_figure, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val actionFigure = actionFigures[position]
        holder.tvTitle.text = actionFigure.title
        holder.ratingBar.rating = actionFigure.rating
        Glide.with(context)
            .load(actionFigure.imageResId)
            .into(holder.ivActionFigure)
    }

    override fun getItemCount(): Int = actionFigures.size
}