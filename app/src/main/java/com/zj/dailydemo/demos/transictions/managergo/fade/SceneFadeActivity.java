package com.zj.dailydemo.demos.transictions.managergo.fade;

import android.content.Context;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.zj.dailydemo.R;


public class SceneFadeActivity extends AppCompatActivity {

	private Context mContext;
	private ViewGroup mSceneRootView;
	private Scene mSceneStart;
	private Scene mSceneEnd;
	private boolean   mStartSceneState;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scene_effect);
		mContext = this;
		initView();
		initEvent();
		initData();
	}

	private void initView() {
		mSceneRootView = findViewById(R.id.layout_scene_transition_root);
	}

	private void initEvent() {
		findViewById(R.id.button_scene_transition_toggle).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				toggleScene();
			}
		});
	}

	private void initData() {
		mSceneStart = Scene.getSceneForLayout(mSceneRootView, R.layout.scene_fade_start, mContext);
		mSceneEnd = Scene.getSceneForLayout(mSceneRootView, R.layout.scene_fade_end, mContext);
		/**
		 * 切换到开始场景状态
		 */
		TransitionManager.go(mSceneStart);
		mStartSceneState = true;
	}

	/**
	 * 两个场景之间相互切换
	 */
	private void toggleScene() {
		TransitionManager.go(mStartSceneState ? mSceneEnd : mSceneStart, new Fade());
		mStartSceneState = !mStartSceneState;
	}
}
