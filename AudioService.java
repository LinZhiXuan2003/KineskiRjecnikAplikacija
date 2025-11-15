package org.example.kineskagramatika.services;

import android.content.Context;
import android.media.MediaPlayer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import java.util.Locale;

public class AudioService implements TextToSpeech.OnInitListener {
    private TextToSpeech textToSpeech;
    private MediaPlayer mediaPlayer;
    private Context context;
    private boolean ttsReady = false;
    
    public AudioService(Context context) {
        this.context = context;
        initTextToSpeech();
    }
    
    private void initTextToSpeech() {
        textToSpeech = new TextToSpeech(context, this);
        textToSpeech.setLanguage(Locale.CHINESE);
    }
    
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            ttsReady = true;
            Log.d("AudioService", "TTS initialized successfully");
        } else {
            Log.e("AudioService", "TTS initialization failed");
        }
    }
    
    public void speakChinese(String text) {
        if (ttsReady && textToSpeech != null) {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }
    
    public void playPinyinAudio(String pinyin) {
        // Ovdje bi se reproducirali prethodno snimljeni audio fajlovi
        // Za sada koristimo TTS za demo
        speakChinese(convertPinyinToChinese(pinyin));
    }
    
    private String convertPinyinToChinese(String pinyin) {
        // Pojednostavljena konverzija za demo
        Map<String, String> pinyinMap = new HashMap<>();
        pinyinMap.put("wǒ", "我");
        pinyinMap.put("nǐ", "你");
        pinyinMap.put("tā", "他");
        pinyinMap.put("shì", "是");
        pinyinMap.put("xuéshēng", "学生");
        
        return pinyinMap.getOrDefault(pinyin, pinyin);
    }
    
    public void stop() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
    
    public boolean isTtsReady() {
        return ttsReady;
    }
}
