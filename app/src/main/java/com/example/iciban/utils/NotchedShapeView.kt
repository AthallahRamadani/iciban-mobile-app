package com.example.iciban.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

class NotchedShapeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint().apply {
        color = Color.WHITE
        style = Paint.Style.FILL
    }

    private val path = Path()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = width.toFloat()
        val height = height.toFloat()

        path.reset()
        path.moveTo(0f, height * 0.2f)
        path.lineTo(width * 0.15f, height * 0.2f)
        path.quadTo(width * 0.2f, height * 0.2f, width * 0.2f, 0f)
        path.lineTo(width * 0.8f, 0f)
        path.quadTo(width * 0.8f, height * 0.2f, width * 0.85f, height * 0.2f)
        path.lineTo(width, height * 0.2f)
        path.lineTo(width, height)
        path.lineTo(0f, height)
        path.close()

        canvas.drawPath(path, paint)
    }
}