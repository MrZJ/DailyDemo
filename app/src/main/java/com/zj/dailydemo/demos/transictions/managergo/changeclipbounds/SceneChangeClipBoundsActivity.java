package com.zj.dailydemo.demos.transictions.managergo.changeclipbounds;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.transition.ChangeClipBounds;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.zj.dailydemo.R;


public class SceneChangeClipBoundsActivity extends AppCompatActivity {

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
		View startView = LayoutInflater.from(mContext).inflate(R.layout.scene_change_clip_bounds_start, mSceneRootView, false);
		startView.findViewById(R.id.image_change_clip_bounds).setClipBounds(new Rect(0, 0, 300, 300));
		View endView = LayoutInflater.from(mContext).inflate(R.layout.scene_change_clip_bounds_end, mSceneRootView, false);
		endView.findViewById(R.id.image_change_clip_bounds).setClipBounds(new Rect(300, 300, 600, 600));
		mSceneStart = new Scene(mSceneRootView, startView);
		mSceneEnd = new Scene(mSceneRootView, endView);
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
		/**
		 * 不指定默认就是AutoTransition
		 */
		TransitionManager.go(mStartSceneState ? mSceneEnd : mSceneStart, new ChangeClipBounds());
		mStartSceneState = !mStartSceneState;
	}
}
