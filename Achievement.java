package org.example.kineskagramatika.domain;

import java.util.Date;

public class Achievement {
    private String id;
    private String title;
    private String description;
    private String icon;
    private int points;
    private boolean unlocked;
    private Date unlockedAt;
    private AchievementType type;
    
    public Achievement(String title, String description, String icon, int points, AchievementType type) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.icon = icon;
        this.points = points;
        this.unlocked = false;
        this.type = type;
    }
    
    public void unlock() {
        this.unlocked = true;
        this.unlockedAt = new Date();
    }
    
    // Getteri
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getIcon() { return icon; }
    public int getPoints() { return points; }
    public boolean isUnlocked() { return unlocked; }
    public Date getUnlockedAt() { return unlockedAt; }
    public AchievementType getType() { return type; }
    
    public enum AchievementType {
        LESSON_COMPLETION, STREAK, QUIZ_PERFECT, SPEED, MASTERY, SOCIAL
    }
}
