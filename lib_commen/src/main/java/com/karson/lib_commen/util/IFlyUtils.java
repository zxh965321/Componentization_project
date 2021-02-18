package com.karson.lib_commen.util;

import android.content.Context;
import android.util.Log;

//import com.iflytek.cloud.ErrorCode;
//import com.iflytek.cloud.InitListener;
//import com.iflytek.cloud.SpeechConstant;
//import com.iflytek.cloud.SpeechError;
//import com.iflytek.cloud.SpeechEvent;
//import com.iflytek.cloud.SpeechSynthesizer;
//import com.iflytek.cloud.SynthesizerListener;

/**
 * @Author karson
 * @Date 2020/8/16-02:37
 * @desc
 */
public class IFlyUtils {
    private Context context;
//    private SpeechSynthesizer mTts;
    private final String TAG = "karson";

    public static IFlyUtils getInstance(Context context){
        return new IFlyUtils(context);
    }
    /**
     * 合成回调监听。
     */
//    private SynthesizerListener mTtsListener = new SynthesizerListener() {
//
//        @Override
//        public void onSpeakBegin() {
//            showTip("开始播放");
//        }
//
//        @Override
//        public void onSpeakPaused() {
//            showTip("暂停播放");
//        }
//
//        @Override
//        public void onSpeakResumed() {
//            showTip("继续播放");
//        }
//
//        @Override
//        public void onBufferProgress(int percent, int beginPos, int endPos,
//                                     String info) {
//            // 合成进度
//            Log.e("MscSpeechLog_", "percent =" + percent);
//
//        }
//
//        @Override
//        public void onSpeakProgress(int percent, int beginPos, int endPos) {
//            // 播放进度
//            Log.e("MscSpeechLog_", "percent =" + percent);
//
//        }
//
//        @Override
//        public void onCompleted(SpeechError error) {
//            System.out.println("oncompleted");
//            if (error == null) {
//                //	showTip("播放完成");
//
//
//
//            } else if (error != null) {
//                showTip(error.getPlainDescription(true));
//            }
//        }
//
//        @Override
//        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
//            //	 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
//            //	 若使用本地能力，会话id为null
//            if (SpeechEvent.EVENT_SESSION_ID == eventType) {
//                String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
//                Log.d(TAG, "session id =" + sid);
//            }
//
//            //当设置SpeechConstant.TTS_DATA_NOTIFY为1时，抛出buf数据
//            if (SpeechEvent.EVENT_TTS_BUFFER == eventType) {
//                byte[] buf = obj.getByteArray(SpeechEvent.KEY_EVENT_TTS_BUFFER);
//                Log.e("MscSpeechLog_", "bufis =" + buf.length);
//
//            }
//
//
//        }
//    };
    private IFlyUtils(Context context){
        this.context = context;
        initFly();
    }

    private void initFly(){

//        mTts = SpeechSynthesizer.createSynthesizer(context, new InitListener() {
//            @Override
//            public void onInit(int i) {
//                if (i != ErrorCode.SUCCESS){
//                    Log.i("karson","初始化错误。错误码为："+i);
//                }else{
//                    mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
//                    //支持实时音频返回，仅在synthesizeToUri条件下支持
//                    mTts.setParameter(SpeechConstant.TTS_DATA_NOTIFY, "1");
//
//                    mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "pcm");
//                    mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/tts.pcm");
//                    // 设置在线合成发音人
//                    mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");
//                    //设置合成语速
//                    mTts.setParameter(SpeechConstant.SPEED, "50");
//                    //设置合成音调
//                    mTts.setParameter(SpeechConstant.PITCH,  "50");
//                    //设置合成音量
//                    mTts.setParameter(SpeechConstant.VOLUME,  "50");
//
//                    mTts.startSpeaking("上海是个大熔炉，梗梗的硬核",mTtsListener);
//                }
//            }
//        });

    }
    private void showTip(final String str) {
        Log.i(TAG,str);
    }
}
