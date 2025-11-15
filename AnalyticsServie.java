package org.example.kineskagramatika.analytics;

import org.example.kineskagramatika.domain.*;
import java.util.*;

public class AnalyticsService {
    private LearningPatternAnalyzer patternAnalyzer;
    private ProgressPredictor progressPredictor;
    private WeaknessDetector weaknessDetector;
    
    public AnalyticsService() {
        patternAnalyzer = new LearningPatternAnalyzer();
        progressPredictor = new ProgressPredictor();
        weaknessDetector = new WeaknessDetector();
    }
    
    public LearningAnalysis analyzeLearningPatterns(User user, List<UserProgress> progressHistory) {
        return patternAnalyzer.analyze(user, progressHistory);
    }
    
    public ProgressPrediction predictProgress(User user, int futureDays) {
        return progressPredictor.predict(user, futureDays);
    }
    
    public WeaknessAnalysis detectWeaknesses(User user) {
        return weaknessDetector.analyze(user);
    }
    
    public PersonalizedRecommendations generateRecommendations(User user) {
        LearningAnalysis analysis = analyzeLearningPatterns(user, getUserProgressHistory(user));
        WeaknessAnalysis weaknesses = detectWeaknesses(user);
        
        return new PersonalizedRecommendations(user, analysis, weaknesses);
    }
    
    private List<UserProgress> getUserProgressHistory(User user) {
        // Implement getting user progress history
        return new ArrayList<>();
    }
    
    public static class LearningAnalysis {
        private double averageStudyTime;
        private double consistencyScore;
        private String bestStudyTime;
        private double retentionRate;
        private List<String> learningPatterns;
        
        // Getteri i setteri
        public double getAverageStudyTime() { return averageStudyTime; }
        public void setAverageStudyTime(double averageStudyTime) { this.averageStudyTime = averageStudyTime; }
        public double getConsistencyScore() { return consistencyScore; }
        public void setConsistencyScore(double consistencyScore) { this.consistencyScore = consistencyScore; }
        public String getBestStudyTime() { return bestStudyTime; }
        public void setBestStudyTime(String bestStudyTime) { this.bestStudyTime = bestStudyTime; }
        public double getRetentionRate() { return retentionRate; }
        public void setRetentionRate(double retentionRate) { this.retentionRate = retentionRate; }
        public List<String> getLearningPatterns() { return learningPatterns; }
        public void set
