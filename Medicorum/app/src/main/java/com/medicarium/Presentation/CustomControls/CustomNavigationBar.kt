package com.medicarium.Presentation.CustomControls

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.medicarium.R
import kotlinx.android.synthetic.main.custom_navigation_bar.view.*

class CustomNavigationBar @JvmOverloads constructor(
    context: Context,
    attributes: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attributes, defStyle, defStyleRes) {

    init {
        LayoutInflater.from(context).inflate(R.layout.custom_navigation_bar, this, true)

        attributes?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.custom_navigation_bar, 0, 0)
            val title = resources.getText(typedArray.getResourceId(R.styleable.custom_navigation_bar_navbarTitle, R.string.continue_text))
            titleTextView.text = title
            typedArray.recycle()
        }
    }

    public fun setOnclickListener(clickListener: OnClickListener) {
        imageButton?.setOnClickListener(clickListener)
    }

}

