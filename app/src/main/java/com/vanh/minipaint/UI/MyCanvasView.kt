package com.vanh.minipaint.UI

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.vanh.minipaint.R

class MyCanvasView (context:Context): View(context){
    private lateinit var extraCanvas: Canvas
    private lateinit var extraBitmap: Bitmap

    private val backgroundColor = ResourcesCompat.getColor(resources, R.color.colorBackground, null)

    override fun onSizeChanged(width: Int, height: Int, oldwidth: Int, oldheight: Int) {
        super.onSizeChanged(width, height, oldwidth, oldheight)

        extraBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888) // for caching
        extraCanvas = Canvas(extraBitmap)
        extraCanvas.drawColor(backgroundColor)
        // recyle old bitmap when a new size is created
        if (::extraBitmap.isInitialized) extraBitmap.recycle()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawBitmap(extraBitmap,0f,0f,null)
    }
}