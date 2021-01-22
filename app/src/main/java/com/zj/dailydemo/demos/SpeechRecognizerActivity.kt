package com.zj.dailydemo.demos

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.provider.Settings
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.zj.dailydemo.R
import com.zj.dailydemo.databinding.ActivitySpeechRecognizerBinding
import timber.log.Timber
import java.util.*


class SpeechRecognizerActivity : AppCompatActivity(), View.OnClickListener, RecognitionListener {
    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, SpeechRecognizerActivity::class.java))
        }
    }

    private val mBinding: ActivitySpeechRecognizerBinding by lazy {
        ActivitySpeechRecognizerBinding.inflate(layoutInflater)
    }
    private val mSpeechRecognizer: SpeechRecognizer by lazy {

        val serviceComponent: String = Settings.Secure.getString(
            contentResolver,
            "voice_recognition_service"
        )
        val component: ComponentName? = ComponentName.unflattenFromString(serviceComponent)
        if (component != null) {
            SpeechRecognizer.createSpeechRecognizer(this, component)
        } else {
            SpeechRecognizer.createSpeechRecognizer(this)
        }
    }
    private var mRecognitionIntent: Intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)

    init {
        mRecognitionIntent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        );
        mRecognitionIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
        mRecognitionIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);
        mRecognitionIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.CHINA)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        requestPermissions(
            arrayOf(
                Manifest.permission.RECORD_AUDIO
            ), 101
        )
        mBinding.startBtn.setOnClickListener(this)
        mBinding.stopBtn.setOnClickListener(this)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 101) {
            if (grantResults[0] != PERMISSION_GRANTED) {
                Toast.makeText(this, "请授权", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.startBtn -> {
                // 开始语音识别 结果在 mSpeechRecognizer.setRecognitionListener(this)回调中
                val can = SpeechRecognizer.isRecognitionAvailable(this)
                Timber.i("can listener $can")
//                if (can) {
                mSpeechRecognizer.setRecognitionListener(this)
                mSpeechRecognizer.startListening(mRecognitionIntent)
//                }


            }
            R.id.stopBtn -> {
                // 停止监听
                mSpeechRecognizer.stopListening()
                // 取消服务
                mSpeechRecognizer.cancel()
            }
        }
    }

    override fun onReadyForSpeech(params: Bundle?) {
        Timber.i("onReadyForSpeech")
    }

    override fun onBeginningOfSpeech() {
        Timber.i("onBeginningOfSpeech")
    }

    override fun onRmsChanged(rmsdB: Float) {
        Timber.i("onRmsChanged")
    }

    override fun onBufferReceived(buffer: ByteArray?) {
        Timber.i("onBufferReceived")
    }

    override fun onEndOfSpeech() {
        Timber.i("onEndOfSpeech")
    }

    override fun onError(error: Int) {
        Timber.i("onError")
    }

    override fun onResults(results: Bundle?) {
        Timber.i("onResults")
    }

    override fun onPartialResults(partialResults: Bundle?) {
        Timber.i("onPartialResults")
    }

    override fun onEvent(eventType: Int, params: Bundle?) {
        Timber.i("onEvent")
    }
//
//    private fun getCName(): ComponentName? {
//
//// 内置语音识别服务是否可用
//        var isRecognizerServiceValid = false
//        var currentRecognitionCmp: ComponentName? = null
//// 查找得到的 "可用的" 语音识别服务
//        val list: List<ResolveInfo> = packageManager
//            .queryIntentServices(Intent(RecognitionService.SERVICE_INTERFACE), MATCH_ALL)
//
//        if (list.isNotEmpty()) {
//            for (info in list) {
//                Timber.i("${info.loadLabel(packageManager)} " + info.serviceInfo.packageName + "/" + info.serviceInfo.name)
//                // 这里拿系统使用的语音识别服务和内置的语音识别比较，如果相同，OK我们直接直接使用
//                // 如果相同就可以直接使用mSpeechRecognizer = 			   SpeechRecognizer.createSpeechRecognizer(context);来创建实例，因为内置的可以使用
//                if (info.serviceInfo.packageName == component.getPackageName()) {
//                    isRecognizerServiceValid = true
//                    break
//                } else {
//                    // 如果服务不同，说明 内置服务 和 系统使用 不是同一个，那么我们需要使用系统使用的
//                    // 因为内置的系统不用，我们用了也没有用
//                    currentRecognitionCmp =
//                        ComponentName(info.serviceInfo.packageName, info.serviceInfo.name)
//                }
//            }
//        } else {
//            // 这里既是查不到可用的语音识别服务，可以歇菜了
//            Timber.i("No recognition services installed")
//            return currentRecognitionCmp
//        }
//    }
}