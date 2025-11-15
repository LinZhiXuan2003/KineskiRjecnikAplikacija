package org.example.kineskagramatika;

import android.app.Application;
import androidx.appcompat.app.AppCompatDelegate;
import com.google.firebase.FirebaseApp;
import dagger.hilt.android.HiltAndroidApp;
import org.example.kineskagramatika.analytics.AnalyticsService;
import org.example.kineskagramatika.cloud.FirebaseService;
import org.example.kineskagramatika.gamification.GamificationService;

@HiltAndroidApp
public class KineskaGramatikaApp extends Application {
    
    private static KineskaGramatikaApp instance;
    private FirebaseService firebaseService;
    private GamificationService gamificationService;
    private AnalyticsService analyticsService;
    
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        
        initializeApp();
        setupNightMode();
        initializeServices();
    }
    
    private void initializeApp() {
        // Initialize Firebase
        FirebaseApp.initializeApp(this);
        
        // Other initializations
    }
    
    private void setupNightMode() {
        // Follow system night mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    }
    
    private void initializeServices() {
        firebaseService = new FirebaseService();
        gamificationService = new GamificationService();
        analyticsService = new AnalyticsService();
    }
    
    public static KineskaGramatikaApp getInstance() {
        return instance;
    }
    
    public FirebaseService getFirebaseService() {
        return firebaseService;
    }
    
    public GamificationService getGamificationService() {
        return gamificationService;
    }
    
    public AnalyticsService getAnalyticsService() {
        return analyticsService;
    }
}
