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
        public void setLearningPatterns(List<String> learningPatterns) { this.learningPatterns = learningPatterns; }
    }
    
    public static class ProgressPrediction {
        private int predictedLevel;
        private Date predictedDate;
        private double confidence;
        private List<Milestone> upcomingMilestones;
        
        // Getteri i setteri
        public int getPredictedLevel() { return predictedLevel; }
        public void setPredictedLevel(int predictedLevel) { this.predictedLevel = predictedLevel; }
        public Date getPredictedDate() { return predictedDate; }
        public void setPredictedDate(Date predictedDate) { this.predictedDate = predictedDate; }
        public double getConfidence() { return confidence; }
        public void setConfidence(double confidence) { this.confidence = confidence; }
        public List<Milestone> getUpcomingMilestones() { return upcomingMilestones; }
        public void setUpcomingMilestones(List<Milestone> upcomingMilestones) { this.upcomingMilestones = upcomingMilestones; }
    }
    
    public static class WeaknessAnalysis {
        private Map<String, Double> grammarWeaknesses;
        private Map<String, Double> vocabularyWeaknesses;
        private Map<String, Double> pronunciationWeaknesses;
        private String primaryWeakness;
        private List<String> improvementSuggestions;
        
        // Getteri i setteri
        public Map<String, Double> getGrammarWeaknesses() { return grammarWeaknesses; }
        public void setGrammarWeaknesses(Map<String, Double> grammarWeaknesses) { this.grammarWeaknesses = grammarWeaknesses; }
        public Map<String, Double> getVocabularyWeaknesses() { return vocabularyWeaknesses; }
        public void setVocabularyWeaknesses(Map<String, Double> vocabularyWeaknesses) { this.vocabularyWeaknesses = vocabularyWeaknesses; }
        public Map<String, Double> getPronunciationWeaknesses() { return pronunciationWeaknesses; }
        public void setPronunciationWeaknesses(Map<String, Double> pronunciationWeaknesses) { this.pronunciationWeaknesses = pronunciationWeaknesses; }
        public String getPrimaryWeakness() { return primaryWeakness; }
        public void setPrimaryWeakness(String primaryWeakness) { this.primaryWeakness = primaryWeakness; }
        public List<String> getImprovementSuggestions() { return improvementSuggestions; }
        public void setImprovementSuggestions(List<String> improvementSuggestions) { this.improvementSuggestions = improvementSuggestions; }
    }
    
    public static class PersonalizedRecommendations {
        private User user;
        private List<Recommendation> dailyRecommendations;
        private List<Recommendation> weeklyRecommendations;
        private List<Recommendation> longTermRecommendations;
        
        public PersonalizedRecommendations(User user, LearningAnalysis analysis, WeaknessAnalysis weaknesses) {
            this.user = user;
            this.dailyRecommendations = generateDailyRecommendations(analysis, weaknesses);
            this.weeklyRecommendations = generateWeeklyRecommendations(analysis, weaknesses);
            this.longTermRecommendations = generateLongTermRecommendations(analysis, weaknesses);
        }
        
        private List<Recommendation> generateDailyRecommendations(LearningAnalysis analysis, WeaknessAnalysis weaknesses) {
            List<Recommendation> recommendations = new ArrayList<>();
            
            // Based on analysis, generate personalized recommendations
            if (analysis.getConsistencyScore() < 70) {
                recommendations.add(new Recommendation(
                    "Poboljšajte konzistentnost",
                    "Pokušajte učiti svaki dan u isto vrijeme",
                    "consistency",
                    Priority.HIGH
                ));
            }
            
            if (weaknesses.getPrimaryWeakness() != null) {
                recommendations.add(new Recommendation(
                    "Fokus na " + weaknesses.getPrimaryWeakness(),
                    "Posvetite više vremena ovoj temi",
                    "focus_area",
                    Priority.MEDIUM
                ));
            }
            
            return recommendations;
        }
        
        private List<Recommendation> generateWeeklyRecommendations(LearningAnalysis analysis, WeaknessAnalysis weaknesses) {
            // Similar logic for weekly recommendations
            return new ArrayList<>();
        }
        
        private List<Recommendation> generateLongTermRecommendations(LearningAnalysis analysis, WeaknessAnalysis weaknesses) {
            // Similar logic for long-term recommendations
            return new ArrayList<>();
        }
        
        // Getteri
        public List<Recommendation> getDailyRecommendations() { return dailyRecommendations; }
        public List<Recommendation> getWeeklyRecommendations() { return weeklyRecommendations; }
        public List<Recommendation> getLongTermRecommendations() { return longTermRecommendations; }
    }
    
    public static class Recommendation {
        private String title;
        private String description;
        private String type;
        private Priority priority;
        
        public Recommendation(String title, String description, String type, Priority priority) {
            this.title = title;
            this.description = description;
            this.type = type;
            this.priority = priority;
        }
        
        // Getteri
        public String getTitle() { return title; }
        public String getDescription() { return description; }
        public String getType() { return type; }
        public Priority getPriority() { return priority; }
    }
    
    public enum Priority {
        HIGH, MEDIUM, LOW
    }
}
