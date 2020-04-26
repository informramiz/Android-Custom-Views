package github.informramiz.androidcustomviews.dialview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.StringRes
import github.informramiz.androidcustomviews.R
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

/**
 * Created by Ramiz Raja on 27/04/2020
 */
class DialView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    companion object {
        private enum class FanSpeed(@StringRes val label: Int) {
            OFF(R.string.fan_off),
            LOW(R.string.fan_low),
            MEDIUM(R.string.fan_medium),
            HIGH(R.string.fan_high)
        }

        private const val RADIUS_OFFSET_LABEL = 30
        private const val RADIUS_OFFSET_INDICATOR = -35
    }

    private var radius = 0.0f   //radius of the dial view circle
    private var currentFanSpeed = FanSpeed.OFF  //current selection of fan speed
    private var pointPosition = PointF(0f, 0f)  //creating here to avoid creation in onDraw()
    // a default paint that will be used for drawing
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 50f
        typeface = Typeface.create("my-bold", Typeface.BOLD)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        //called whenever this view changes size so we should calculate our dial radius here
        //for formula understanding: https://www.mathopenref.com/arcradiusderive.html
        radius = (min(width, height)/2.0f * 0.8f)
    }

    private fun PointF.computeXYForSpeed(position: FanSpeed, radius: Float) {
        // Angles are in radians.
        val startAngle = Math.PI * (9 / 8.0)
        val angle = startAngle + position.ordinal * (Math.PI / 4)
        x = (radius * cos(angle)).toFloat() + width / 2
        y = (radius * sin(angle)).toFloat() + height / 2
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //use green color if fan on otherwise fan off
        paint.color = if (currentFanSpeed == FanSpeed.OFF) Color.GRAY else Color.GREEN
        //draw the dial circle
        canvas.drawCircle(width/2f, height/2f, radius, paint)

        //now draw the indicator with color back
        paint.color = Color.BLACK
        //calculate position of indicator
        val indicatorRadius = radius + RADIUS_OFFSET_INDICATOR
        pointPosition.computeXYForSpeed(currentFanSpeed, indicatorRadius)
        canvas.drawCircle(pointPosition.x, pointPosition.y, radius/12, paint)

        //now draw the labels for each fan speed
        val labelRadius = radius + RADIUS_OFFSET_LABEL
        //calculate the position for labels
        for (speed in FanSpeed.values()) {
            pointPosition.computeXYForSpeed(speed, labelRadius)
            val label = resources.getString(speed.label)
            canvas.drawText(label, pointPosition.x, pointPosition.y, paint)
        }
    }
}