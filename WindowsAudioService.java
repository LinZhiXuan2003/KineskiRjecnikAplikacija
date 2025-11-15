package org.example.kineskagramatika.services;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WindowsAudioService {
    private Map<String, File> audioFiles;
    private Clip currentClip;
    
    public WindowsAudioService() {
        audioFiles = new HashMap<>();
        loadAudioFiles();
    }
    
    private void loadAudioFiles() {
        // Učitaj audio fajlove iz resources/audio folder-a
        // Ovo je placeholder - u pravoj aplikaciji bi se učitavali stvarni fajlovi
        try {
            audioFiles.put("wǒ", new File("resources/audio/wo.wav"));
            audioFiles.put("nǐ", new File("resources/audio/ni.wav"));
            audioFiles.put("shì", new File("resources/audio/shi.wav"));
        } catch (Exception e) {
            System.err.println("Error loading audio files: " + e.getMessage());
        }
    }
    
    public void playPinyin(String pinyin) {
        stopCurrentAudio();
        
        File audioFile = audioFiles.get(pinyin);
        if (audioFile != null && audioFile.exists()) {
            try {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
                currentClip = AudioSystem.getClip();
                currentClip.open(audioStream);
                currentClip.start();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                System.err.println("Error playing audio: " + e.getMessage());
            }
        } else {
            System.out.println("Audio file not found for: " + pinyin);
            // Fallback: koristi TTS ili prikaži poruku
        }
    }
    
    public void stopCurrentAudio() {
        if (currentClip != null && currentClip.isRunning()) {
            currentClip.stop();
            currentClip.close();
        }
    }
    
    public void cleanup() {
        stopCurrentAudio();
    }
}
