package ui.anwesome.com.vtoxview

/**
 * Created by anweshmishra on 02/04/18.
 */
import android.content.Context
import android.graphics.*
import android.view.*

class VToXView(ctx : Context) : View(ctx) {

    val paint : Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    val renderer : VToXRenderer = VToXRenderer(this)

    override fun onDraw(canvas : Canvas) {
        renderer.render(canvas, paint)
    }

    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                renderer.handleTap()
            }
        }
        return true
    }
    
    data class VToXState(var prevScale : Float = 0f, var dir : Float = 0f, var j : Int = 0) {

        val scales : Array<Float> = arrayOf(0f, 0f, 0f)

        fun update(stopcb : (Float) -> Unit) {
            scales[j] += 0.1f * this.dir
            if (Math.abs(scales[j] - prevScale) > 1) {
                scales[j] = prevScale + dir
                j += dir.toInt()
                if (j == scales.size || j == -1) {
                    j -= dir.toInt()
                    dir = 0f
                    prevScale = scales[j]
                    stopcb(prevScale)
                }
            }
        }
        fun startUpdating(startcb : () -> Unit) {
            if (dir == 0f) {
                dir = 1 - 2 * prevScale
                startcb()
            }
        }
    }
    data class VToXAnimator(var view : View, var animated : Boolean = false) {
        fun animate(updatecb : () -> Unit) {
            if (animated) {
                updatecb()
                try {
                    Thread.sleep(50)
                    view.invalidate()
                }
                catch (ex : Exception) {

                }
            }
        }

        fun start() {
            if (!animated) {
                animated = true
                view.postInvalidate()
            }
        }

        fun stop() {
            if (animated) {
                animated = false
            }
        }
    }

    data class VToXShape (var i : Int, val state : VToXState = VToXState()) {
        fun draw(canvas : Canvas, paint : Paint) {
            val w = canvas.width.toFloat()
            val h = canvas.height.toFloat()
            val size : Float  = Math.min(w,h)/5
            paint.color = Color.parseColor("#9b59b6")
            paint.strokeWidth = size/11
            paint.strokeCap = Paint.Cap.ROUND
            canvas.save()
            canvas.translate(w/2, h/2)
            for (i in 0..1) {
                canvas.save()
                canvas.translate((size/(2 * Math.sqrt(2.0).toFloat())) * (1 -state.scales[1]) * (1 - 2 * i), 0f)
                canvas.rotate(45f * (1 - state.scales[0] + state.scales[2]) * (1 - 2 * i))
                canvas.drawLine(0f, -size/2,0f, size/2, paint)
                canvas.restore()
            }
            canvas.restore()
        }
        fun update(stopcb : (Float) -> Unit) {
            state.update(stopcb)
        }
        fun startUpdating(startcb : () -> Unit) {
            state.startUpdating(startcb)
        }
    }

    data class VToXRenderer(var view : VToXView) {

        val animator : VToXAnimator = VToXAnimator(view)

        val shape : VToXShape = VToXShape(0)

        fun render(canvas : Canvas, paint : Paint) {
            canvas.drawColor(Color.parseColor("#212121"))
            shape.draw(canvas, paint)
            animator.animate {
                shape.update {
                    animator.stop()
                }
            }
        }
        fun handleTap() {
            shape.startUpdating {
                animator.start()
            }
        }
    }
}