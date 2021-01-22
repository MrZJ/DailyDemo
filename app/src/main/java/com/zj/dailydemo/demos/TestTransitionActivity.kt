package com.zj.dailydemo.demos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.transition.Explode
import androidx.transition.Fade
import androidx.transition.Slide
import androidx.transition.TransitionManager
import androidx.transition.Visibility.MODE_OUT
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialFade
import com.google.android.material.transition.MaterialFadeThrough
import com.google.android.material.transition.platform.MaterialSharedAxis
import com.zj.dailydemo.R
import com.zj.dailydemo.databinding.ActivityTestTransitionBinding

class TestTransitionActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, TestTransitionActivity::class.java))
        }
    }

    private val mBinding by lazy {
        ActivityTestTransitionBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        mBinding.startTransition.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.startTransition -> {
                val transition1 = MaterialFadeThrough().apply {
                    duration = 3000
                    interpolator = AccelerateInterpolator()
                    addTarget(mBinding.tv1)
                }
                val transition2 = MaterialFade().apply {
                    duration = 3000
                    interpolator = LinearInterpolator()
                    addTarget(mBinding.tv2)
                }
                val transition3 = MaterialElevationScale(false).apply {
                    duration = 3000
                    interpolator = LinearInterpolator()
                    mode = MODE_OUT
                    addTarget(mBinding.tv4)
                }
//                val transition3 = Fade().apply {
//                    duration = 3000
//                    interpolator = LinearInterpolator()
//                    addTarget(mBinding.tv4)
//                    mode = Fade.MODE_IN
//                }
//                val transition3 = Explode().apply {
//                    duration = 3000
//                    interpolator = LinearInterpolator()
//                    addTarget(mBinding.tv4)
//                    mode = Fade.MODE_IN
//                }
                val transition4 = MaterialSharedAxis(MaterialSharedAxis.Y, true).apply {
                    duration = 3000
                    interpolator = LinearInterpolator()
                    addTarget(mBinding.tv5)
                }
                TransitionManager.beginDelayedTransition(mBinding.root, transition1)
                TransitionManager.beginDelayedTransition(mBinding.root, transition2)
                TransitionManager.beginDelayedTransition(mBinding.root, transition3)
                android.transition.TransitionManager.beginDelayedTransition(
                    mBinding.root,
                    transition4
                )
                mBinding.tv1.visibility = View.GONE
                mBinding.tv2.visibility = View.GONE
                mBinding.tv4.visibility = View.GONE
                mBinding.tv5.visibility = View.GONE
            }
        }
    }
}