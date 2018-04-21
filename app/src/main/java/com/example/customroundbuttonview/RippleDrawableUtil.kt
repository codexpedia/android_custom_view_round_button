package com.example.customroundbuttonview

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.StateListDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.graphics.drawable.Drawable
import android.content.res.ColorStateList
import android.graphics.drawable.RippleDrawable
import android.os.Build
import java.util.*

object RippleDrawableUtil {
    fun getAdaptiveRippleDrawable(normalColor: Int, pressedColor: Int, radius : Float = 5.0f): Drawable {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            RippleDrawable(ColorStateList.valueOf(pressedColor), null, getRippleMask(normalColor, radius))
        } else {
            getStateListDrawable(normalColor, pressedColor)
        }
    }

    private fun getRippleMask(color: Int, radius : Float = 5.0f): Drawable {
        val radii = FloatArray(8)
        Arrays.fill(radii, radius)

        val r = RoundRectShape(radii, null, null)
        val shapeDrawable = ShapeDrawable(r)
        shapeDrawable.paint.color = color
        return shapeDrawable
    }

    fun getStateListDrawable(
            normalColor: Int, pressedColor: Int): StateListDrawable {
        val states = StateListDrawable()
        states.addState(intArrayOf(android.R.attr.state_pressed),
                ColorDrawable(pressedColor))
        states.addState(intArrayOf(android.R.attr.state_focused),
                ColorDrawable(pressedColor))
        states.addState(intArrayOf(android.R.attr.state_activated),
                ColorDrawable(pressedColor))
        states.addState(intArrayOf(),
                ColorDrawable(normalColor))
        return states
    }
}
