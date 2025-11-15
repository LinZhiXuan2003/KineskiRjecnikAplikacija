package org.example.kineskagramatika.data.room;

import androidx.room.*;
import java.util.List;

@Dao
public interface UserProgressDao {
    
    @Query("SELECT * FROM user_progress")
    List<UserProgress> getAll();
    
    @Query("SELECT * FROM user_progress WHERE lessonId = :lessonId")
    UserProgress getByLessonId(int lessonId);
    
    @Query("SELECT * FROM user_progress WHERE completed = 1")
    List<UserProgress> getCompletedLessons();
    
    @Query("SELECT COUNT(*) FROM user_progress WHERE completed = 1")
    int getCompletedCount();
    
    @Query("SELECT AVG(score) FROM user_progress WHERE completed = 1")
    double getAverageScore();
    
    @Insert
    void insert(UserProgress progress);
    
    @Update
    void update(UserProgress progress);
    
    @Delete
    void delete(UserProgress progress);
    
    @Query("DELETE FROM user_progress")
    void deleteAll();
    
    default void markLessonCompleted(int lessonId, String lessonTitle, int score, int timeSpent) {
        UserProgress progress = getByLessonId(lessonId);
        
        if (progress == null) {
            progress = new UserProgress(lessonId, lessonTitle);
        }
        
        progress.setCompleted(true);
        progress.setScore(score);
        progress.setTimeSpent(timeSpent);
        progress.setCompletedAt(new Date());
        
        if (progress.getId() == 0) {
            insert(progress);
        } else {
            update(progress);
        }
    }
}
