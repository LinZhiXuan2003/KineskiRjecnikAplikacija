package org.example.kineskagramatika.services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseService {
    private static final String DB_URL = "jdbc:sqlite:kineska_gramatika.db";
    private Connection connection;
    
    public DatabaseService() {
        connect();
        createTables();
    }
    
    private void connect() {
        try {
            connection = DriverManager.getConnection(DB_URL);
            System.out.println("Connected to SQLite database");
        } catch (SQLException e) {
            System.err.println("Error connecting to database: " + e.getMessage());
        }
    }
    
    private void createTables() {
        String createUserProgressTable = """
            CREATE TABLE IF NOT EXISTS user_progress (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                lekcija_id INTEGER NOT NULL,
                completed BOOLEAN DEFAULT FALSE,
                completed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                score INTEGER DEFAULT 0
            )
            """;
            
        String createQuizResultsTable = """
            CREATE TABLE IF NOT EXISTS quiz_results (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                total_questions INTEGER,
                correct_answers INTEGER,
                score_percentage REAL
            )
            """;
        
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createUserProgressTable);
            stmt.execute(createQuizResultsTable);
        } catch (SQLException e) {
            System.err.println("Error creating tables: " + e.getMessage());
        }
    }
    
    public void saveQuizResult(int totalQuestions, int correctAnswers, double scorePercentage) {
        String sql = "INSERT INTO quiz_results(total_questions, correct_answers, score_percentage) VALUES(?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, totalQuestions);
            pstmt.setInt(2, correctAnswers);
            pstmt.setDouble(3, scorePercentage);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error saving quiz result: " + e.getMessage());
        }
    }
    
    public List<QuizStat> getQuizStatistics() {
        List<QuizStat> stats = new ArrayList<>();
        String sql = "SELECT * FROM quiz_results ORDER BY date DESC LIMIT 10";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                stats.add(new QuizStat(
                    rs.getTimestamp("date"),
                    rs.getInt("total_questions"),
                    rs.getInt("correct_answers"),
                    rs.getDouble("score_percentage")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error getting quiz statistics: " + e.getMessage());
        }
        
        return stats;
    }
    
    public static class QuizStat {
        private Timestamp date;
        private int totalQuestions;
        private int correctAnswers;
        private double scorePercentage;
        
        public QuizStat(Timestamp date, int totalQuestions, int correctAnswers, double scorePercentage) {
            this.date = date;
            this.totalQuestions = totalQuestions;
            this.correctAnswers = correctAnswers;
            this.scorePercentage = scorePercentage;
        }
        
        // Getteri
        public Timestamp getDate() { return date; }
        public int getTotalQuestions() { return totalQuestions; }
        public int getCorrectAnswers() { return correctAnswers; }
        public double getScorePercentage() { return scorePercentage; }
    }
    
    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing database: " + e.getMessage());
        }
    }
}
