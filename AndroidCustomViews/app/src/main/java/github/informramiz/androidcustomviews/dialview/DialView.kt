package github.informramiz.androidcustomviews.dialview

import android.content.Context
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import androidx.annotation.StringRes
import github.informramiz.androidcustomviews.R

/**
 * Created by Ramiz Raja on 27/04/2020
 */
class DialView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    companion object {
        private enum class FanSpeed(@StringRes val value: Int) {
            OFF(R.string.fan_off),
            LOW(R.string.fan_low),
            MEDIUM(R.string.fan_medium),
            HIGH(R.string.fan_high)
        }

        private const val RADIUS_OFFSET_LABEL = 50
        private const val RADIUS_OFFSET_INDICATOR = -30
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
}