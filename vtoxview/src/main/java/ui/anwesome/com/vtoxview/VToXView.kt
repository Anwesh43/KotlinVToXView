package ui.anwesome.com.vtoxview

/**
 * Created by anweshmishra on 02/04/18.
 */
import android.content.Context
import android.graphics.*
import android.view.*

class VToXView(ctx : Context) : View(ctx) {

    val paint : Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas : Canvas) {

    }

    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }
}