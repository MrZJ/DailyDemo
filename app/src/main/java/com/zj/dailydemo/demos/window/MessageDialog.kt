package com.zj.dailydemo.demos.window

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.*
import android.view.WindowManager.LayoutParams.*
import android.widget.TextView
import com.zj.dailydemo.R
import java.lang.ref.WeakReference

class MessageDialog private constructor(private val mContext: WeakReference<Activity>) {
    private var mRootView: View? = null
    private var mTextView: TextView? = null
    private var userNameTv: TextView? = null
    private val mHandler: Handler = Handler(Looper.getMainLooper())
    private var isShowing: Boolean = false
    private val mRunnable: Runnable

    private val mViewHeight: Float by lazy {
        mContext.get()?.resources?.getDimension(R.dimen.message_height) ?: 270f
    }
    private val mWM by lazy {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            mContext.get()?.applicationContext?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        } else {
            mContext.get()?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        }
    }

    private fun getLayoutParams(): WindowManager.LayoutParams {
        return WindowManager.LayoutParams().apply {
            gravity = Gravity.TOP
            type = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                TYPE_APPLICATION_OVERLAY
            } else {
//                TYPE_PHONE
//                TYPE_APPLICATION_PANEL activityContext
                TYPE_APPLICATION_SUB_PANEL
                //TYPE_APPLICATION_PANEL、TYPE_APPLICATION_SUB_PANEL
            }
            windowAnimations = R.style.pop_translation_anim_style
            width = MATCH_PARENT
            height = mViewHeight.toInt()
            x = 0
            y = 0
            flags = FLAG_NOT_TOUCH_MODAL
            packageName = mContext.get()?.packageName
        }
    }

    @SuppressLint("ClickableViewAccessibility", "SetTextI18n")
    fun showMsg(
        driverUuid: String?,
        driverName: String?,
        content: String?
    ) {
        if (mContext.get() == null) return
        mContext.get()?.let { context ->
            mHandler.removeCallbacks(mRunnable)
            if (isShowing) {
                isShowing = true
//                userNameTv?.text = "$driverName："
//                mTextView?.text = content
            } else {
                mRootView =
                    LayoutInflater.from(context).inflate(R.layout.message_dialog_layout, null)
                mRootView?.let {
                    mTextView = it.findViewById(R.id.messageTv)
                    userNameTv = it.findViewById(R.id.userNameTv)
                    it.setOnClickListener {
                        dismiss()
                    }
                }
//                userNameTv?.text = "$driverName："
//                mTextView?.text = content
                var mStartY = 0
                var mDiffY = 0
                mRootView?.setOnTouchListener(View.OnTouchListener { _, event ->
                    event?.run {
                        when (action) {
                            MotionEvent.ACTION_DOWN -> {
                                mStartY = rawY.toInt()
                            }
                            MotionEvent.ACTION_MOVE -> {
                                mDiffY = mStartY - rawY.toInt()
                                if (mDiffY > 10) {
                                    mRootView?.apply {
                                        val lp = layoutParams
                                        lp.height = ((mViewHeight - mDiffY).toInt())
                                        layoutParams = lp
                                    }
                                }
                                return@OnTouchListener true
                            }
                            MotionEvent.ACTION_UP -> {
                                if (mDiffY > 10) {
                                    dismiss()
                                    return@OnTouchListener true
                                }
                            }
                        }
                    }
                    return@OnTouchListener false
                })
                isShowing = true
                mWM.addView(mRootView, getLayoutParams())
            }
            mHandler.postDelayed(mRunnable, DEFAULT_SHOW_TIME.toLong())
        }
    }

    private fun dismiss() {
        isShowing = false
        if (mRootView != null) {
            mWM.removeViewImmediate(mRootView)
            mRootView = null
            userNameTv = null
            mTextView = null
        }
    }

    companion object {
        private var INSTANCE: MessageDialog? = null
        private const val DEFAULT_SHOW_TIME = 5000
        fun getInstance(activity: Activity): MessageDialog {
            if (activity != INSTANCE?.mContext?.get()) {
                INSTANCE = MessageDialog(WeakReference(activity))
            }
            return INSTANCE!!
        }
    }

    init {
        mRunnable = Runnable {
            dismiss()
        }
    }
}