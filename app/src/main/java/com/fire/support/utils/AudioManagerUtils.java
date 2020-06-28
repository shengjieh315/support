package com.fire.support.utils;

import android.content.Context;
import android.media.AudioManager;

public class AudioManagerUtils {

    private AudioManager audioManager;
    private int NOW_AUDIO_TYPE = TYPE_MUSIC;
    private int NOW_FLAG = FLAG_NOTHING;
    private int VOICE_STEP_100 = 2; //0-100的步进。

    /**
     * 封装：STREAM_类型
     */
    public final static int TYPE_MUSIC = AudioManager.STREAM_MUSIC;
    public final static int TYPE_ALARM = AudioManager.STREAM_ALARM;
    public final static int TYPE_RING = AudioManager.STREAM_RING;


    /**
     * 封装：FLAG
     */
    public final static int FLAG_SHOW_UI = AudioManager.FLAG_SHOW_UI;
    public final static int FLAG_PLAY_SOUND = AudioManager.FLAG_PLAY_SOUND;
    public final static int FLAG_NOTHING = 0;


    /**
     * 初始化，获取音量管理者
     *
     * @param context 上下文
     */
    public AudioManagerUtils(Context context) {
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    public int getSystemMaxVolume() {
        return audioManager.getStreamMaxVolume(NOW_AUDIO_TYPE);
    }

    public int getSystemCurrentVolume() {
        return audioManager.getStreamVolume(NOW_AUDIO_TYPE);
    }

    /**
     * 以0-100为范围，获取当前的音量值
     *
     * @return 获取当前的音量值
     */
    public int get100CurrentVolume() {
        return 100 * getSystemCurrentVolume() / getSystemMaxVolume();
    }

    /**
     * 修改步进值
     *
     * @param step step
     * @return this
     */
    public AudioManagerUtils setVoiceStep100(int step) {
        VOICE_STEP_100 = step;
        return this;
    }

    /**
     * 改变当前的模式，对全局API生效
     *
     * @param type
     * @return
     */
    public AudioManagerUtils setAudioType(int type) {
        NOW_AUDIO_TYPE = type;
        return this;
    }

    /**
     * 改变当前FLAG，对全局API生效
     *
     * @param flag
     * @return
     */
    public AudioManagerUtils setFlag(int flag) {
        NOW_FLAG = flag;
        return this;
    }

    public AudioManagerUtils addVoiceSystem() {
        audioManager.adjustStreamVolume(NOW_AUDIO_TYPE, AudioManager.ADJUST_RAISE, NOW_FLAG);
        return this;
    }

    public AudioManagerUtils subVoiceSystem() {
        audioManager.adjustStreamVolume(NOW_AUDIO_TYPE, AudioManager.ADJUST_LOWER, NOW_FLAG);
        return this;
    }

    /**
     * 调整音量，自定义
     *
     * @param num 0-100
     * @return 改完后的音量值
     */
    public int setVoice100(int num) {
        int a = (int) Math.ceil((num) * getSystemMaxVolume() * 0.01);
        a = a <= 0 ? 0 : a;
        a = a >= 100 ? 100 : a;
        audioManager.setStreamVolume(NOW_AUDIO_TYPE, a, 0);
        return get100CurrentVolume();
    }

    /**
     * 步进加，步进值可修改
     * 0——100
     *
     * @return 改完后的音量值
     */
    public int addVoice100() {
        int a = (int) Math.ceil((VOICE_STEP_100 + get100CurrentVolume()) * getSystemMaxVolume() * 0.01);
        a = a <= 0 ? 0 : a;
        a = a >= 100 ? 100 : a;
        audioManager.setStreamVolume(NOW_AUDIO_TYPE, a, NOW_FLAG);
        return get100CurrentVolume();
    }

    /**
     * 步进减，步进值可修改
     * 0——100
     *
     * @return 改完后的音量值
     */
    public int subVoice100() {
        int a = (int) Math.floor((get100CurrentVolume() - VOICE_STEP_100) * getSystemMaxVolume() * 0.01);
        a = a <= 0 ? 0 : a;
        a = a >= 100 ? 100 : a;
        audioManager.setStreamVolume(NOW_AUDIO_TYPE, a, NOW_FLAG);
        return get100CurrentVolume();
    }

}
