package com.app.myapplication

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.BaseTransientBottomBar
import kotlinx.android.synthetic.main.custom_snackbar_model_two.view.*


class CustomSnackbar2(
    parent: ViewGroup,
    content: View,
    contentViewCallback: com.google.android.material.snackbar.ContentViewCallback

) : BaseTransientBottomBar<CustomSnackbar2>(parent, content, contentViewCallback) {



    init {
        this.getView().setPadding(0, 0, 0, 0)
        this.getView()
            .setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))


    }

    /**
     * Sets the title of this custom snackbar.
     */
    private fun setTitle(title: String): CustomSnackbar2 {
        val titleView = getView().findViewById<TextView>(R.id.tvTitle)
        titleView.text = title
        return this
    }


    companion object {

        fun make(
            view: View,
            title: Int,
            duration: Int,
            gravity: Int = Gravity.BOTTOM
        ): CustomSnackbar2 {
            return make(
                view,
                view.context.getString(title),
                duration,
                gravity
            )
        }

        fun make(
            view: View,
            title: String,
            duration: Int,
            gravity: Int = Gravity.BOTTOM
        ): CustomSnackbar2 {
            val customSnackbar = createCustomSnackbar(view).apply {
                setTitle(title)
                setDuration(duration)
            }

            val mView = customSnackbar.view
            val params = mView.layoutParams as FrameLayout.LayoutParams
            params.gravity = gravity
            mView.layoutParams = params
            // customSnackbar.animationMode = ANIMATION_MODE_SLIDE
            return customSnackbar
        }

        private fun createCustomSnackbar(view: View): CustomSnackbar2 {
            val parent = findSuitableParent(view) ?: throw IllegalArgumentException(
                "No suitable parent found from the given view. Please provide a valid view."
            )

            val inflater = LayoutInflater.from(view.context)
            val content = inflater.inflate(
                R.layout.custom_snackbar_model_two,
                parent,
                false
            )

            content.ivIcon.startAnimation(AnimationUtils.loadAnimation(view.context, R.anim.heart));


            val contentViewCallback =
                object : com.google.android.material.snackbar.ContentViewCallback {
                    override fun animateContentIn(delay: Int, duration: Int) {
                        content.alpha = 0f
                        content.animate().alpha(1f).setDuration(duration.toLong())
                            .setStartDelay(delay.toLong()).start()
                    }

                    override fun animateContentOut(delay: Int, duration: Int) {
                        content.alpha = 1f
                        content.animate().alpha(0f).setDuration(duration.toLong())
                            .setStartDelay(delay.toLong()).start()
                    }
                }
            return CustomSnackbar2(parent, content, contentViewCallback)
        }


        private fun findSuitableParent(view: View?): ViewGroup? {
            var mView = view
            var fallback: ViewGroup? = null
            do {
                if (mView is CoordinatorLayout) {
                    // We've found a CoordinatorLayout, use it
                    return mView
                } else if (mView is FrameLayout) {
                    if (mView.id == android.R.id.content) {
                        // If we've hit the decor content view, then we didn't find a CoL in the
                        // hierarchy, so use it.
                        return mView
                    } else {
                        // It's not the content view but we'll use it as our fallback
                        fallback = mView
                    }
                }

                if (mView != null) {
                    // Else, we will loop and crawl up the view hierarchy and try to find a parent
                    val parent = mView.parent
                    mView = if (parent is View) parent else null
                }
            } while (mView != null)

            // If we reach here then we didn't find a CoL or a suitable content view so we'll fallback
            return fallback
        }
    }

}