package org.example.kineskagramatika.domain;

import java.util.*;

public class User {
    private String id;
    private String username;
    private String email;
    private int level;
    private int experience;
    private int streak;
    private Date lastLogin;
    private Map<String, Object> preferences;
    private List<Achievement> achievements;
    
    public User(String username, String email) {
        this.id = UUID.randomUUID().toString();
        this.username = username;
        this.email = email;
        this.level = 1;
        this.experience = 0;
        this.streak = 0;
        this.lastLogin = new Date();
        this.preferences = new HashMap<>();
        this.achievements = new ArrayList<>();
        setDefaultPreferences();
    }
    
    private void setDefaultPreferences() {
        preferences.put("language", "croatian");
        preferences.put("difficulty", "beginner");
        preferences.put("notifications", true);
        preferences.put("sound", true);
        preferences.put("dark_mode", false);
    }
    
    public void addExperience(int xp) {
        this.experience += xp;
        checkLevelUp();
    }
    
    private void checkLevelUp() {
        int xpRequired = level * 100;
        if (experience >= xpRequired) {
            level++;
            experience -= xpRequired;
            // Award level up bonus
            addExperience(50); // Bonus XP
        }
    }
    
    public void updateStreak() {
        Date today = new Date();
        if (isConsecutiveDay(lastLogin, today)) {
            streak++;
        } else {
            streak = 1;
        }
        lastLogin = today;
    }
    
    private boolean isConsecutiveDay(Date last, Date current) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(last);
        cal2.setTime(current);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
               cal1.get(Calendar.DAY_OF_YEAR) + 1 == cal2.get(Calendar.DAY_OF_YEAR);
    }
    
    // Getteri i setteri
    public String getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public int getLevel() { return level; }
    public int getExperience() { return experience; }
    public int getStreak() { return streak; }
    public Date getLastLogin() { return lastLogin; }
    public Map<String, Object> getPreferences() { return preferences; }
    public List<Achievement> getAchievements() { return achievements; }
    
    public void setPreference(String key, Object value) {
        preferences.put(key, value);
    }
    
    public void addAchievement(Achievement achievement) {
        achievements.add(achievement);
    }
    
    public int getExperienceToNextLevel() {
        return level * 100 - experience;
    }
    
    public double getLevelProgress() {
        return (double) experience / (level * 100);
    }
}
