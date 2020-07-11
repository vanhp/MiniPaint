package com.vanh.minipaint.UI

import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
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
    private var currentX = 0f
    private var currentY = 0f
    private val touchTolerance = ViewConfiguration.get(context).scaledTouchSlop
    private lateinit var frame: Rect
    // Path representing the drawing so far
    private val drawing = Path()

    // Path representing what's currently being drawn
    private val curPath = Path()

    // Set up the paint with which to draw.
    private val paint = Paint().apply {
        color = Color.BLACK //drawColor
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
        // Calculate a rectangular frame around the picture.
        val inset = 40
        frame = Rect(inset, inset, width - inset, height - inset)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
//
//        canvas.drawBitmap(extraBitmap,0f,0f,null)
//        canvas.drawRect(frame,paint)
        canvas.drawPath(drawing,paint)
        canvas.drawPath(curPath,paint)
        canvas.drawRect(frame,paint)
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

        drawing.addPath(curPath)
        // Reset the path so it doesn't get drawn again.
        path.reset()
    }

    private fun touchStart() {
       path.reset()
       path.moveTo(motionTouchEventX, motionTouchEventY)
       currentX = motionTouchEventX
       currentY = motionTouchEventY
    }
    private fun touchMove() {
        // calc moue distance
       val dx = Math.abs(motionTouchEventX - currentX)
       val dy = Math.abs(motionTouchEventY - currentY)

        // move too far add a new segment to path
       if (dx >= touchTolerance || dy >= touchTolerance) {
           // QuadTo() adds a quadratic bezier from the last point,
           // approaching control point (x1,y1), and ending at (x2,y2).
           // create a smoothly drawn line without corners
           path.quadTo(currentX,
               currentY,
               (motionTouchEventX + currentX) / 2,
               (motionTouchEventY + currentY) / 2)

           currentX = motionTouchEventX
           currentY = motionTouchEventY
           // Draw the path in the extra bitmap to cache it.
           extraCanvas.drawPath(path, paint)
       }
       invalidate()
}
}