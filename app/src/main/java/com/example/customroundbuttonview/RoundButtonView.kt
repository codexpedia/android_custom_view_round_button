package com.example.customroundbuttonview

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.round_button_view.view.*



class RoundButtonView : LinearLayout {
    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    private var _bgColor = ContextCompat.getColor(context, R.color.round_button_default_bg_color)
    private var _borderColor = ContextCompat.getColor(context, R.color.round_button_default_border_color)
    private var _textColor = ContextCompat.getColor(context, R.color.round_button_default_text_color)
    private var _textSize = context.resources.getDimensionPixelSize(R.dimen.round_button_default_text_size)
    private var _text = ""
    private var _cornerRadius = 1000.0f
    private var _borderWidth = 2
    private val textColorDarkenRatio = 0.7f
    private lateinit var buttonText: TextView

    var text: String
        get() = _text
        set(value) {
            _text = value
            buttonText.text = _text
        }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        val view = inflate(context, R.layout.round_button_view, this)
        buttonText = view.tv_button_text

        val styledAttributes = context.obtainStyledAttributes(attrs, R.styleable.RoundButtonView, defStyle, 0)

        _bgColor = styledAttributes.getInt(R.styleable.RoundButtonView_bgColor, _bgColor)
        _borderColor = styledAttributes.getInt(R.styleable.RoundButtonView_borderColor, darkenColor(_bgColor))
        _textColor = styledAttributes.getInt(R.styleable.RoundButtonView_textColor, darkenColor(_borderColor))
        _cornerRadius = styledAttributes.getFloat(R.styleable.RoundButtonView_cornerRadius, _cornerRadius)
        _borderWidth = styledAttributes.getInt(R.styleable.RoundButtonView_borderWidth, _borderWidth)
        _textSize = styledAttributes.getDimensionPixelSize(R.styleable.RoundButtonView_textSize, _textSize)
        _text = styledAttributes.getString(R.styleable.RoundButtonView_text) ?: ""

        styledAttributes.recycle()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            foreground = RippleDrawableUtil.getAdaptiveRippleDrawable(_borderColor, _textColor, _cornerRadius)
//            foreground = ContextCompat.getDrawable(context, R.drawable.round_button_foreground_ripple_effect)
        }

        buttonText.text = _text
        buttonText.setTextSize(TypedValue.COMPLEX_UNIT_PX, _textSize.toFloat())
        setButtonStyle()
    }

    private fun setButtonStyle() {
        val shape = GradientDrawable()
        shape.shape = GradientDrawable.RECTANGLE
        shape.setStroke(_borderWidth, _borderColor)
        shape.setColor(_bgColor)
        shape.cornerRadius = _cornerRadius
        background = shape

        buttonText.setTextColor(_textColor)
    }

    private fun darkenColor(color: Int): Int {
        val hsv = FloatArray(3)
        Color.colorToHSV(color, hsv)
        hsv[2] *= textColorDarkenRatio
        return Color.HSVToColor(hsv)
    }
}
