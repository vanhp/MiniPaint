package com.vanh.minipaint.UI

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.view.MotionEvent
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.vanh.minipaint.R

private const val STROKE_WIDTH = 12f  //must be float

class MyCanvasView (context:Context): View(context){
    private lateinit var extraCanvas: Canvas
    private lateinit var extraBitmap: Bitmap

    private val backgroundColor = ResourcesCompat.getColor(resources, R.color.colorBackground, null)
    private val drawColor = ResourcesCompat.getColor(resources, R.color.colorPaint, null)
    private var path = Path() //drawing path
    var motionTouchEventX = 0f
    var motionTouchEventY = 0f

    // Set up the paint with which to draw.
    private val paint = Paint().apply {
        color = drawColor
        // Smooths out edges of what is drawn without affecting shape.
        isAntiAlias = true
        // Dithering affects how colors with higher-precision than the device are down-sampled.
        isDither = true
        style = Paint.Style.STROKE // default: FILL
        strokeJoin = Paint.Join.ROUND // default: MITER
        strokeCap = Paint.Cap.ROUND // default: BUTT
        strokeWidth = STROKE_WIDTH // default: Hairline-width (really thin)
    }


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

    override fun onTouchEvent(event: MotionEvent): Boolean {
        motionTouchEventX = event.x
        motionTouchEventY = event.y

        when (event.action) {
               MotionEvent.ACTION_DOWN -> touchStart()
               MotionEvent.ACTION_MOVE -> touchMove()
               MotionEvent.ACTION_UP -> touchUp()
        }
        return true


    }

    private fun touchUp() {
        TODO("Not yet implemented")
    }

    private fun touchMove() {
        TODO("Not yet implemented")
    }

    private fun touchStart() {
        TODO("Not yet implemented")
    }
}