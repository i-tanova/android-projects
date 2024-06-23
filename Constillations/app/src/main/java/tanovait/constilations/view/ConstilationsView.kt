package tanovait.constilations.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import java.lang.Integer.min

class ConstellationsView@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var radius = 0.0f
    private val pointPosition: PointF = PointF(0.0f, 0.0f)
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 55.0f
        typeface = Typeface.create( "", Typeface.BOLD)
    }

//    override fun onDraw(canvas: Canvas?) {
//        super.onDraw(canvas)
//        paint.color =  Color.GRAY
//        canvas?.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), radius, paint)
//        //constellationPaint.color = Color.RED
//        //canvas?.drawOval(20f, 20f, 20f,20f, constellationPaint);
//    }

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        radius = (min(width, height) / 2.0 * 0.8).toFloat()
    }
}