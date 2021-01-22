package com.zj.dailydemo.demos

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.transition.Explode
import androidx.transition.Fade
import androidx.transition.Slide
import androidx.transition.TransitionManager
import androidx.transition.Visibility.MODE_OUT
import com.google.android.material.color.MaterialColors
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialFade
import com.google.android.material.transition.MaterialFadeThrough
import com.google.android.material.transition.platform.MaterialSharedAxis
import com.zj.dailydemo.R
import com.zj.dailydemo.databinding.ActivityAnimationUtilsBinding
import com.zj.dailydemo.databinding.ActivityTestTransitionBinding

class AnimationUtilsActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, AnimationUtilsActivity::class.java))
        }
    }

    private val mBinding by lazy {
        ActivityAnimationUtilsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        mBinding.startTransition.setOnClickListener(this)
        mBinding.endTransition.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.startTransition -> {
//                setViewVisible(false)

//                mBinding.tv1.animation = AnimationUtils.makeOutAnimation(this, true)
//                mBinding.tv1.visibility = View.GONE

                setViewVisible2(false)
            }
            R.id.endTransition -> {
//                setViewVisible()

//                mBinding.tv1.animation = AnimationUtils.makeInChildBottomAnimation(this)
//                mBinding.tv1.visibility = View.VISIBLE

                setViewVisible2(true)
            }
        }
    }

    private fun setViewVisible2(b: Boolean) {
        val fadeThrough = MaterialFadeThrough().apply {
            duration = 1000
            addTarget(mBinding.tv1)
        }
        TransitionManager.beginDelayedTransition(mBinding.root, fadeThrough)
        mBinding.tv1.visibility = if (b) View.VISIBLE else View.GONE
    }

    private fun setViewVisible(b: Boolean = true) {
        if (!b) {
            ObjectAnimator.ofFloat(mBinding.tv1, "Alpha", 1f, 0f).apply {
                duration = 500
                addListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator?) {
                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        mBinding.tv1.visibility = View.GONE
                    }

                    override fun onAnimationCancel(animation: Animator?) {
                    }

                    override fun onAnimationRepeat(animation: Animator?) {
                    }
                })
                start()
            }
        } else {
            ObjectAnimator.ofFloat(mBinding.tv1, "Alpha", 0f, 1f).apply {
                duration = 500
                addListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator?) {
                        mBinding.tv1.visibility = View.VISIBLE
                    }

                    override fun onAnimationEnd(animation: Animator?) {
                    }

                    override fun onAnimationCancel(animation: Animator?) {
                    }

                    override fun onAnimationRepeat(animation: Animator?) {
                    }
                })
                start()
            }
        }
    }
}