package org.example.kineskagramatika.gamification;

import org.example.kineskagramatika.domain.*;
import java.util.*;

public class GamificationService {
    private List<Achievement> achievements;
    private Map<String, Integer> xpRewards;
    
    public GamificationService() {
        initializeAchievements();
        initializeXPRewards();
    }
    
    private void initializeAchievements() {
        achievements = new ArrayList<>();
        
        // Lesson achievements
        achievements.add(new Achievement(
            "Prvi koraci", "Završi prvu lekciju", "🎯", 50,
            Achievement.AchievementType.LESSON_COMPLETION
        ));
        
        achievements.add(new Achievement(
            "Maratonac", "Završi 10 lekcija zaredom", "🏃", 100,
            Achievement.AchievementType.LESSON_COMPLETION
        ));
        
        // Streak achievements
        achievements.add(new Achievement(
            "Posvećenost", "Održavi 7-dnevni niz", "🔥", 75,
            Achievement.AchievementType.STREAK
        ));
        
        achievements.add(new Achievement(
            "Legenda", "Održavi 30-dnevni niz", "⭐", 200,
            Achievement.AchievementType.STREAK
        ));
        
        // Quiz achievements
        achievements.add(new Achievement(
            "Savršenstvo", "Postigni 100% na kvizu", "💯", 150,
            Achievement.AchievementType.QUIZ_PERFECT
        ));
        
        achievements.add(new Achievement(
            "Brzoumnost", "Završi kviz za manje od 60 sekundi", "⚡", 100,
            Achievement.AchievementType.SPEED
        ));
        
        // Mastery achievements
        achievements.add(new Achievement(
            "Gramatičar", "Savladaj sve gramatičke lekcije", "📚", 300,
            Achievement.AchievementType.MASTERY
        ));
        
        achievements.add(new Achievement(
            "Rječnički car", "Nauči 100 riječi", "👑", 250,
            Achievement.AchievementType.MASTERY
        ));
    }
    
    private void initializeXPRewards() {
        xpRewards = new HashMap<>();
        xpRewards.put("lesson_complete", 25);
        xpRewards.put("quiz_perfect", 50);
        xpRewards.put("quiz_good", 30);
        xpRewards.put("quiz_pass", 15);
        xpRewards.put("daily_login", 10);
        xpRewards.put("streak_bonus", 5);
        xpRewards.put("achievement_unlock", 20);
    }
    
    public GameResult processLessonCompletion(User user, Lesson lesson, int score, int timeSpent) {
        GameResult result = new GameResult();
        
        // Base XP for completing lesson
        int baseXP = xpRewards.get("lesson_complete");
        result.setXpEarned(baseXP);
        
        // Bonus XP for high score
        if (score >= 90) {
            result.setXpEarned(result.getXpEarned() + 20);
        } else if (score >= 70) {
            result.setXpEarned(result.getXpEarned() + 10);
        }
        
        // Check for achievements
        checkLessonAchievements(user, lesson, result);
        
        // Update user
        user.addExperience(result.getXpEarned());
        user.updateStreak();
        
        return result;
    }
    
    public GameResult processQuizCompletion(User user, QuizResult quizResult) {
        GameResult result = new GameResult();
        
        // Calculate XP based on performance
        double percentage = quizResult.getScorePercentage();
        int xpEarned;
        
        if (percentage == 100) {
            xpEarned = xpRewards.get("quiz_perfect");
        } else if (percentage >= 80) {
            xpEarned = xpRewards.get("quiz_good");
        } else {
            xpEarned = xpRewards.get("quiz_pass");
        }
        
        // Time bonus
        if (quizResult.getTimeSpent() < 60) { // Under 60 seconds
            xpEarned += 15;
        }
        
        result.setXpEarned(xpEarned);
        
        // Check quiz achievements
        checkQuizAchievements(user, quizResult, result);
        
        user.addExperience(result.getXpEarned());
        
        return result;
    }
    
    private void checkLessonAchievements(User user, Lesson lesson, GameResult result) {
        int completedLessons = user.getAchievements().stream()
            .filter(a -> a.getType() == Achievement.AchievementType.LESSON_COMPLETION)
            .mapToInt(a -> 1)
            .sum() + 1; // +1 for current lesson
            
        // First lesson achievement
        if (completedLessons == 1) {
            unlockAchievement(user, "Prvi koraci", result);
        }
        
        // 10 lessons achievement
        if (completedLessons == 10) {
            unlockAchievement(user, "Maratonac", result);
        }
    }
    
    private void checkQuizAchievements(User user, QuizResult quizResult, GameResult result) {
        // Perfect score achievement
        if (quizResult.getScorePercentage() == 100) {
            unlockAchievement(user, "Savršenstvo", result);
        }
        
        // Speed achievement
        if (quizResult.getTimeSpent() < 60) {
            unlockAchievement(user, "Brzoumnost", result);
        }
    }
    
    private void unlockAchievement(User user, String achievementTitle, GameResult result) {
        achievements.stream()
            .filter(a -> a.getTitle().equals(achievementTitle) && !a.isUnlocked())
            .findFirst()
            .ifPresent(achievement -> {
                achievement.unlock();
                user.addAchievement(achievement);
                result.getUnlockedAchievements().add(achievement);
                result.setXpEarned(result.getXpEarned() + xpRewards.get("achievement_unlock"));
            });
    }
    
    public List<Achievement> getAvailableAchievements() {
        return new ArrayList<>(achievements);
    }
    
    public static class GameResult {
        private int xpEarned;
        private List<Achievement> unlockedAchievements;
        private int coinsEarned;
        
        public GameResult() {
            this.unlockedAchievements = new ArrayList<>();
        }
        
        // Getteri i setteri
        public int getXpEarned() { return xpEarned; }
        public void setXpEarned(int xpEarned) { this.xpEarned = xpEarned; }
        public List<Achievement> getUnlockedAchievements() { return unlockedAchievements; }
        public int getCoinsEarned() { return coinsEarned; }
        public void setCoinsEarned(int coinsEarned) { this.coinsEarned = coinsEarned; }
    }
}
