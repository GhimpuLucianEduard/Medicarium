package com.medicarium.Presentation.CustomControls

import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.graphics.drawable.TransitionDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.TouchDelegate
import android.view.View
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
            val leftImage = resources.getDrawable(typedArray.getResourceId(R.styleable.custom_navigation_bar_navBarLeftImage, R.drawable.back_arrow))
            // TODO: not sure how to set null as default drawable
            val rightImage = resources.getDrawable(typedArray.getResourceId(R.styleable.custom_navigation_bar_navBarRightImage, R.drawable.empty_drawable))
            titleTextView.text = title
            imageButton.background = leftImage
            rightImageButton.background = rightImage


            val parent = imageButton.parent as View
            parent.post {
                Runnable {
                    val rect = Rect()
                    imageButton.getHitRect(rect)
                    rect.top -= 200
                    rect.left -= 200
                    rect.bottom += 200
                    rect.right += 200
                    parent.touchDelegate = TouchDelegate(rect, imageButton)
                }
            }

            typedArray.recycle()
        }
    }

    fun setTitletext(text: String) {
        titleTextView.text = text
    }

    fun setLeftImageDrawnable(drawable: Drawable) {
        imageButton.background = drawable
    }

    fun setRightImageDrawnable(drawable: Drawable) {
        rightImageButton.background = drawable
    }

    fun setLeftButtonClickListener(clickListener: OnClickListener) {
        imageButton?.setOnClickListener(clickListener)
    }

    fun setLeftButtonClickListener(clickListener: () -> Unit) {
        imageButton?.setOnClickListener {
            clickListener.invoke()
        }
    }

    fun setRightButtonClickListener(clickListener: () -> Unit) {
        rightImageButton?.setOnClickListener {
            clickListener.invoke()
        }
    }

}

