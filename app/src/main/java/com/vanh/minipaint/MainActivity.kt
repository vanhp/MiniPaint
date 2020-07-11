package com.vanh.minipaint

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vanh.minipaint.UI.MyCanvasView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//         cannot get the size of the view in the onCreate() method,
//         because the size has not been determined at this point. need screen size to draw
        val myCanvasView = MyCanvasView(this)
        myCanvasView.contentDescription = getString(R.string.canvasContentDescription)
        setContentView(myCanvasView)
    }
}
