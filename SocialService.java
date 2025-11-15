package org.example.kineskagramatika.social;

import org.example.kineskagramatika.domain.User;
import java.util.*;

public class SocialService {
    private LeaderboardManager leaderboardManager;
    private FriendSystem friendSystem;
    private ChallengeSystem challengeSystem;
    
    public SocialService() {
        leaderboardManager = new LeaderboardManager();
        friendSystem = new FriendSystem();
        challengeSystem = new ChallengeSystem();
    }
    
    public Leaderboard getWeeklyLeaderboard() {
        return leaderboardManager.getWeeklyLeaderboard();
    }
    
    public Leaderboard getMonthlyLeaderboard() {
        return leaderboardManager.getMonthlyLeaderboard();
    }
    
    public Leaderboard getFriendLeaderboard(User user) {
        return leaderboardManager.getFriendLeaderboard(user.getId());
    }
    
    public void addFriend(User user, String friendId) {
        friendSystem.addFriend(user.getId(), friendId);
    }
    
    public void removeFriend(User user, String friendId) {
        friendSystem.removeFriend(user.getId(), friendId);
    }
    
    public List<User> getFriends(User user) {
        return friendSystem.getFriends(user.getId());
    }
    
    public Challenge createChallenge(User challenger, String challengeType, int targetScore, List<String> friendIds) {
        return challengeSystem.createChallenge(challenger.getId(), challengeType, targetScore, friendIds);
    }
    
    public void acceptChallenge(User user, String challengeId) {
        challengeSystem.acceptChallenge(user.getId(), challengeId);
    }
    
    public void submitChallengeResult(User user, String challengeId, int score) {
        challengeSystem.submitResult(user.getId(), challengeId, score);
    }
    
    public List<Challenge> getActiveChallenges(User user) {
        return challengeSystem.getActiveChallenges(user.getId());
    }
    
    public List<Challenge> getCompletedChallenges(User user) {
        return challengeSystem.getCompletedChallenges(user.getId());
    }
    
    public static class Leaderboard {
        private String period;
        private List<LeaderboardEntry> entries;
        
        public Leaderboard(String period) {
            this.period = period;
            this.entries = new ArrayList<>();
        }
        
        public void addEntry(LeaderboardEntry entry) {
            entries.add(entry);
            // Sort by score descending
            entries.sort((a, b) -> Integer.compare(b.getScore(), a.getScore()));
        }
        
        // Getteri
        public String getPeriod() { return period; }
        public List<LeaderboardEntry> getEntries() { return entries; }
    }
    
    public static class LeaderboardEntry {
        private String userId;
        private String username;
        private int score;
        private int rank;
        
        public LeaderboardEntry(String userId, String username, int score) {
            this.userId = userId;
            this.username = username;
            this.score = score;
        }
        
        // Getteri i setteri
        public String getUserId() { return userId; }
        public String getUsername() { return username; }
        public int getScore() { return score; }
        public int getRank() { return rank; }
        public void setRank(int rank) { this.rank = rank; }
    }
    
    public static class Challenge {
        private String id;
        private String challengerId;
        private String type;
        private int targetScore;
        private List<String> participants;
        private Map<String, Integer> results;
        private Date createdAt;
        private Date expiresAt;
        private ChallengeStatus status;
        
        public Challenge(String id, String challengerId, String type, int targetScore, List<String> participants) {
            this.id = id;
            this.challengerId = challengerId;
            this.type = type;
            this.targetScore = targetScore;
            this.participants = new ArrayList<>(participants);
            this.results = new HashMap<>();
            this.createdAt = new Date();
            this.expiresAt = new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000); // 7 days
            this.status = ChallengeStatus.ACTIVE;
        }
        
        public void addResult(String userId, int score) {
            results.put(userId, score);
        }
        
        public boolean isCompleted() {
            return results.size() == participants.size();
        }
        
        public String getWinner() {
            return results.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
        }
        
        // Getteri
        public String getId() { return id; }
        public String getChallengerId() { return challengerId; }
        public String getType() { return type; }
        public int getTargetScore() { return targetScore; }
        public List<String> getParticipants() { return participants; }
        public Map<String, Integer> getResults() { return results; }
        public Date getCreatedAt() { return createdAt; }
        public Date getExpiresAt() { return expiresAt; }
        public ChallengeStatus getStatus() { return status; }
        public void setStatus(ChallengeStatus status) { this.status = status; }
    }
    
    public enum ChallengeStatus {
        ACTIVE, COMPLETED, EXPIRED
    }
}
