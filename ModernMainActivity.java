package org.example.kineskagramatika.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import android.animation.ValueAnimator;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.*;
import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.card.MaterialCardView;
import org.example.kineskagramatika.R;
import org.example.kineskagramatika.domain.User;

public class ModernMainActivity extends AppCompatActivity {
    
    private LottieAnimationView lottieAnimation;
    private ProgressBar levelProgressBar;
    private TextView tvLevel, tvUsername, tvStreak, tvXpToNext;
    private MaterialCardView cardLessons, cardDictionary, cardQuiz, cardSocial;
    private User currentUser;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Enable edge-to-edge
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        
        setContentView(R.layout.activity_modern_main);
        
        initializeViews();
        setupAnimations();
        loadUserData();
        setupClickListeners();
    }
    
    private void initializeViews() {
        lottieAnimation = findViewById(R.id.lottieAnimation);
        levelProgressBar = findViewById(R.id.levelProgressBar);
        tvLevel = findViewById(R.id.tvLevel);
        tvUsername = findViewById(R.id.tvUsername);
        tvStreak = findViewById(R.id.tvStreak);
        tvXpToNext = findViewById(R.id.tvXpToNext);
        
        cardLessons = findViewById(R.id.cardLessons);
        cardDictionary = findViewById(R.id.cardDictionary);
        cardQuiz = findViewById(R.id.cardQuiz);
        cardSocial = findViewById(R.id.cardSocial);
        
        // Apply dynamic corner radii based on screen size
        applyDynamicCorners();
    }
    
    private void applyDynamicCorners() {
        float cornerRadius = getResources().getDimension(R.dimen.card_corner_radius);
        
        int[] cardIds = {R.id.cardLessons, R.id.cardDictionary, R.id.cardQuiz, R.id.cardSocial};
        for (int cardId : cardIds) {
            MaterialCardView card = findViewById(cardId);
            card.setRadius(cornerRadius);
        }
    }
    
    private void setupAnimations() {
        // Start Lottie animation
        lottieAnimation.playAnimation();
        
        // Setup floating animation for cards
        setupCardAnimations();
    }
    
    private void setupCardAnimations() {
        new Handler().postDelayed(() -> {
            animateCardEntrance(cardLessons, 0);
            animateCardEntrance(cardDictionary, 100);
            animateCardEntrance(cardQuiz, 200);
            animateCardEntrance(cardSocial, 300);
        }, 500);
    }
    
    private void animateCardEntrance(MaterialCardView card, long delay) {
        card.setAlpha(0f);
        card.setTranslationY(100f);
        
        card.animate()
            .alpha(1f)
            .translationY(0f)
            .setStartDelay(delay)
            .setDuration(600)
            .setInterpolator(new AccelerateDecelerateInterpolator())
            .start();
        
        // Add hover effect
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            card.setOnClickListener(v -> {
                v.animate()
                    .scaleX(0.95f)
                    .scaleY(0.95f)
                    .setDuration(150)
                    .withEndAction(() -> v.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(150)
                        .start())
                    .start();
            });
        }
    }
    
    private void loadUserData() {
        // Load user data from database/cloud
        currentUser = new User("Korisnik", "email@example.com");
        currentUser.addExperience(750); // Demo data
        
        updateUI();
        animateProgressBar();
    }
    
    private void updateUI() {
        tvUsername.setText(currentUser.getUsername());
        tvLevel.setText(String.valueOf(currentUser.getLevel()));
        tvStreak.setText(currentUser.getStreak() + " 🔥");
        tvXpToNext.setText(currentUser.getExperienceToNextLevel() + " XP do sljedećeg nivoa");
        
        // Update progress bar
        levelProgressBar.setMax(currentUser.getLevel() * 100);
        levelProgressBar.setProgress(currentUser.getExperience());
    }
    
    private void animateProgressBar() {
        ValueAnimator animator = ValueAnimator.ofInt(0, currentUser.getExperience());
        animator.setDuration(1500);
        animator.addUpdateListener(animation -> {
            int value = (int) animation.getAnimatedValue();
            levelProgressBar.setProgress(value);
        });
        animator.start();
    }
    
    private void setupClickListeners() {
        cardLessons.setOnClickListener(v -> {
            navigateWithTransition(LessonsActivity.class, cardLessons);
        });
        
        cardDictionary.setOnClickListener(v -> {
            navigateWithTransition(DictionaryActivity.class, cardDictionary);
        });
        
        cardQuiz.setOnClickListener(v -> {
            navigateWithTransition(QuizActivity.class, cardQuiz);
        });
        
        cardSocial.setOnClickListener(v -> {
            navigateWithTransition(SocialActivity.class, cardSocial);
        });
    }
    
    private void navigateWithTransition(Class<?> destination, View sharedView) {
        // Implement shared element transition
        startActivity(new Intent(this, destination));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        // Refresh data when returning to main activity
        loadUserData();
    }
}
