package org.example.kineskagramatika.data.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.Date;

@Entity(tableName = "user_progress")
public class UserProgress {
    @PrimaryKey(autoGenerate = true)
    public int id;
    
    public int lessonId;
    public String lessonTitle;
    public boolean completed;
    public Date completedAt;
    public int score; // 0-100
    public int timeSpent; // u minutama
    
    // Konstruktor
    public UserProgress(int lessonId, String lessonTitle) {
        this.lessonId = lessonId;
        this.lessonTitle = lessonTitle;
        this.completed = false;
        this.score = 0;
        this.timeSpent = 0;
        this.completedAt = new Date();
    }
    
    // Getteri
    public int getId() { return id; }
    public int getLessonId() { return lessonId; }
    public String getLessonTitle() { return lessonTitle; }
    public boolean isCompleted() { return completed; }
    public Date getCompletedAt() { return completedAt; }
    public int getScore() { return score; }
    public int getTimeSpent() { return timeSpent; }
    
    // Setteri
    public void setCompleted(boolean completed) { this.completed = completed; }
    public void setCompletedAt(Date completedAt) { this.completedAt = completedAt; }
    public void setScore(int score) { this.score = score; }
    public void setTimeSpent(int timeSpent) { this.timeSpent = timeSpent; }
}
