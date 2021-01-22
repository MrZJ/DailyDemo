package com.zj.dailydemo.demos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerAdapter
import com.zj.dailydemo.R
import com.zj.dailydemo.databinding.ActivityClipTopPaddingBinding

/**
 * 实验一下clipToPadding属性 实现类似画廊的效果
 * @property mViewList MutableList<View>
 * @property mBinding [@androidx.annotation.NonNull] ActivityClipTopPaddingBinding
 */
class ClipToPaddingActivity : AppCompatActivity() {
    private lateinit var mViewList: MutableList<View>

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, ClipToPaddingActivity::class.java))
        }
    }

    private val mBinding by lazy {
        ActivityClipTopPaddingBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        mViewList = mutableListOf()
        mViewList.add(0, layoutInflater.inflate(R.layout.imageview, mBinding.root, false))
        mViewList.add(1, layoutInflater.inflate(R.layout.imageview, mBinding.root, false))
        mViewList.add(2, layoutInflater.inflate(R.layout.imageview, mBinding.root, false))
        mBinding.viewPager.setPadding(120, 0, 120, 0)
        mBinding.viewPager.pageMargin = 60
        mBinding.viewPager.clipToPadding = false
        mBinding.viewPager.adapter = object : PagerAdapter() {
            override fun getCount() = mViewList.size


            override fun isViewFromObject(view: View, inView: Any): Boolean {
                return view == inView
            }

            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                container.addView(mViewList[position])
                return mViewList[position]
            }

            override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
                container.removeView(mViewList[position])
            }
        }
    }
}