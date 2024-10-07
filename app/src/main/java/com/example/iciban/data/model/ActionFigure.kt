package com.example.iciban.data.model

data class ActionFigure(
    val title: String,
    val imageResId: Int,
    val rating: Float,
    val stock: Int, val totalStock: Int,
    val description: String,
    val category: String
)