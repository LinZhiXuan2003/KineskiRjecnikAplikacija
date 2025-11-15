package org.example.kineskagramatika.cloud;

import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.FirestoreClient;
import com.google.cloud.firestore.*;
import org.example.kineskagramatika.domain.User;
import org.example.kineskagramatika.domain.UserProgress;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class FirebaseService {
    private Firestore firestore;
    
    public FirebaseService() {
        try {
            FirebaseApp.initializeApp();
            this.firestore = FirestoreClient.getFirestore();
        } catch (Exception e) {
            System.err.println("Firebase initialization failed: " + e.getMessage());
        }
    }
    
    public CompletableFuture<Boolean> syncUserData(User user) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        
        try {
            DocumentReference docRef = firestore.collection("users").document(user.getId());
            Map<String, Object> userData = new HashMap<>();
            userData.put("username", user.getUsername());
            userData.put("email", user.getEmail());
            userData.put("level", user.getLevel());
            userData.put("experience", user.getExperience());
            userData.put("streak", user.getStreak());
            userData.put("lastLogin", user.getLastLogin());
            userData.put("preferences", user.getPreferences());
            userData.put("lastSync", new Date());
            
            docRef.set(userData).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    future.complete(true);
                } else {
                    future.completeExceptionally(task.getException());
                }
            });
        } catch (Exception e) {
            future.completeExceptionally(e);
        }
        
        return future;
    }
    
    public CompletableFuture<User> loadUserData(String userId) {
        CompletableFuture<User> future = new CompletableFuture<>();
        
        try {
            DocumentReference docRef = firestore.collection("users").document(userId);
            docRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        User user = new User(
                            document.getString("username"),
                            document.getString("email")
                        );
                        user.setPreference("language", document.getString("language"));
                        // Load other fields...
                        future.complete(user);
                    } else {
                        future.complete(null);
                    }
                } else {
                    future.completeExceptionally(task.getException());
                }
            });
        } catch (Exception e) {
            future.completeExceptionally(e);
        }
        
        return future;
    }
    
    public CompletableFuture<Boolean> syncProgress(String userId, List<UserProgress> progressList) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        
        try {
            DocumentReference docRef = firestore.collection("userProgress").document(userId);
            Map<String, Object> progressData = new HashMap<>();
            
            for (UserProgress progress : progressList) {
                Map<String, Object> lessonProgress = new HashMap<>();
                lessonProgress.put("completed", progress.isCompleted());
                lessonProgress.put("score", progress.getScore());
                lessonProgress.put("timeSpent", progress.getTimeSpent());
                lessonProgress.put("lastUpdated", new Date());
                
                progressData.put("lesson_" + progress.getLessonId(), lessonProgress);
            }
            
            docRef.set(progressData, SetOptions.merge()).addOnCompleteListener(task -> {
                future.complete(task.isSuccessful());
            });
        } catch (Exception e) {
            future.completeExceptionally(e);
        }
        
        return future;
    }
    
    public CompletableFuture<List<UserProgress>> loadProgress(String userId) {
        CompletableFuture<List<UserProgress>> future = new CompletableFuture<>();
        
        // Implementation for loading progress from Firestore
        return future;
    }
}
