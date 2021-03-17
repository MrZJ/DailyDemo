package com.zj.dailydemo

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_SHORT
import com.google.android.material.snackbar.Snackbar
import com.zj.dailydemo.databinding.ActivityScrollingBinding
import com.zj.dailydemo.demos.*
import com.zj.dailydemo.demos.material.ShapeAbleImageActivity
import com.zj.dailydemo.demos.transictions.ActivityTransitionMainActivity
import com.zj.dailydemo.demos.transictions.SceneTransitionMainActivity
import com.zj.dailydemo.demos.transictions.shareelementtransition.ShareElementTransitionMainActivity
import com.zj.dailydemo.demos.window.MessageDialog

class ScrollingActivity : AppCompatActivity(), View.OnClickListener {
    private val mBinding by lazy {
        ActivityScrollingBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        setSupportActionBar(findViewById(R.id.toolbar))
        mBinding.fab.setOnClickListener(this)
        mBinding.clipToPadding.setOnClickListener(this)
        mBinding.clipChildren.setOnClickListener(this)
        mBinding.clickEffect.setOnClickListener(this)
        mBinding.testTransition.setOnClickListener(this)
        mBinding.layoutSceneTransition.setOnClickListener(this)
        mBinding.layoutActivityTransition.setOnClickListener(this)
        mBinding.layoutShareElementTransition.setOnClickListener(this)
//        mBinding.testRecognizer.setOnClickListener(this)
        mBinding.testAnimationUtils.setOnClickListener(this)
        mBinding.testShapeAbleImage.setOnClickListener(this)
        mBinding.testWindow.setOnClickListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.clipToPadding -> {
                ClipToPaddingActivity.start(this)
            }
            R.id.fab -> {
                CustomViewDisplayActivity.start(this)
                Snackbar.make(mBinding.fab, "snack bar is clicked", LENGTH_SHORT).show()
            }
            R.id.clipChildren -> {
                ClipChildrenActivity.start(this)
            }
            R.id.clickEffect -> {
                ClickEffectActivity.start(this)
            }
            R.id.testTransition -> {
                TestTransitionActivity.start(this)
            }
            R.id.layout_scene_transition -> {
                /**
                 * 这里使用了我们常规的启动方式。API 21之前不带Transition的启动方式
                 */
                startActivity(Intent(this, SceneTransitionMainActivity::class.java))
            }
            R.id.layout_activity_transition -> {
                /**
                 * 这里使用了我们常规的启动方式。API 21之前不带Transition的启动方式
                 */
                startActivity(Intent(this, ActivityTransitionMainActivity::class.java))
            }
            R.id.layout_share_element_transition -> {
                /**
                 * 这里使用了我们常规的启动方式。API 21之前不带Transition的启动方式
                 */
                startActivity(Intent(this, ShareElementTransitionMainActivity::class.java))
            }
//            R.id.testRecognizer -> {
//                SpeechRecognizerActivity.start(this)
//            }
            R.id.testAnimationUtils -> {
                AnimationUtilsActivity.start(this)
            }
            R.id.testShapeAbleImage -> {
                ShapeAbleImageActivity.start(this)
            }
            R.id.testWindow -> {
                startActivity(Intent(this, ShareElementTransitionMainActivity::class.java))
                mBinding.root.postDelayed({
                    Application.ACLifeCycleManager.getCurrentActivity()?.let {
                        MessageDialog.getInstance(it)
                            .showMsg("", "", "")
                    }
                }, 3000)
            }
        }
    }
}