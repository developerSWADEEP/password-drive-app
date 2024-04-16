package com.errorsmasher.passworddrive

import android.animation.Animator
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewTreeObserver

class AnimationState : AppCompatActivity() {

    private var background: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.do_not_move, R.anim.do_not_move)

        setContentView(R.layout.activity_animation_state)

        background = findViewById(R.id.background)

        if (savedInstanceState == null) {
            background?.setVisibility(View.INVISIBLE)
            val viewTreeObserver = background?.getViewTreeObserver()
            if (viewTreeObserver!!.isAlive) {
                viewTreeObserver?.addOnGlobalLayoutListener(object :
                    ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        circularRevealActivity()
                        background?.getViewTreeObserver()?.removeOnGlobalLayoutListener(this)
                    }
                })
            }
        }


    }



    private fun circularRevealActivity() {
        val cx = background!!.right - getDips(44)
        val cy = background!!.bottom - getDips(44)
        val finalRadius = Math.max(background!!.width, background!!.height).toFloat()
        val circularReveal = ViewAnimationUtils.createCircularReveal(
            background,
            cx,
            cy, 0f,
            finalRadius
        )
        circularReveal.duration = 3000
        background!!.visibility = View.VISIBLE
        circularReveal.start()
    }

    private fun getDips(dps: Int): Int {
        val resources = resources
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dps.toFloat(),
            resources.displayMetrics
        ).toInt()
    }

    override fun onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val cx = background!!.width - getDips(44)
            val cy = background!!.bottom - getDips(44)
            val finalRadius = Math.max(background!!.width, background!!.height).toFloat()
            val circularReveal =
                ViewAnimationUtils.createCircularReveal(background, cx, cy, finalRadius, 0f)
            circularReveal.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animator: Animator) {}
                override fun onAnimationEnd(animator: Animator) {
                    background!!.visibility = View.INVISIBLE
                    finish()
                }

                override fun onAnimationCancel(animator: Animator) {}
                override fun onAnimationRepeat(animator: Animator) {}
            })
            circularReveal.duration = 3000
            circularReveal.start()
        } else {
            super.onBackPressed()
        }
    }
}