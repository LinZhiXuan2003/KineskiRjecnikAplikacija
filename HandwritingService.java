package org.example.kineskagramatika.handwriting;

import android.graphics.*;
import android.view.MotionEvent;
import java.util.*;

public class HandwritingService {
    private Bitmap drawingBitmap;
    private Canvas drawingCanvas;
    private Path currentPath;
    private Paint paint;
    private List<Stroke> strokes;
    private CharacterRecognizer characterRecognizer;
    private StrokeOrderValidator strokeOrderValidator;
    
    public HandwritingService(int width, int height) {
        initializeCanvas(width, height);
        characterRecognizer = new CharacterRecognizer();
        strokeOrderValidator = new StrokeOrderValidator();
        strokes = new ArrayList<>();
    }
    
    private void initializeCanvas(int width, int height) {
        drawingBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        drawingCanvas = new Canvas(drawingBitmap);
        
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(8);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setAntiAlias(true);
        
        clearCanvas();
    }
    
    public void handleTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startNewStroke(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                continueStroke(x, y);
                break;
            case MotionEvent.ACTION_UP:
                finishStroke();
                break;
        }
    }
    
    private void startNewStroke(float x, float y) {
        currentPath = new Path();
        currentPath.moveTo(x, y);
        
        Stroke newStroke = new Stroke();
        newStroke.addPoint(x, y);
        strokes.add(newStroke);
    }
    
    private void continueStroke(float x, float y) {
        if (currentPath != null) {
            currentPath.lineTo(x, y);
            drawingCanvas.drawPath(currentPath, paint);
            
            // Add point to current stroke
            if (!strokes.isEmpty()) {
                strokes.get(strokes.size() - 1).addPoint(x, y);
            }
        }
    }
    
    private void finishStroke() {
        if (!strokes.isEmpty()) {
            strokes.get(strokes.size() - 1).finish();
        }
        currentPath = null;
    }
    
    public RecognitionResult recognizeCharacter() {
        RecognitionResult result = new RecognitionResult();
        
        if (strokes.isEmpty()) {
            result.setRecognizedCharacter("");
            result.setConfidence(0);
            result.setFeedback("Nema pisanja za prepoznavanje");
            return result;
        }
        
        // Recognize character
        CharacterRecognition recognition = characterRecognizer.recognize(strokes);
        result.setRecognizedCharacter(recognition.getCharacter());
        result.setConfidence(recognition.getConfidence());
        
        // Validate stroke order
        StrokeOrderValidation validation = strokeOrderValidator.validate(strokes, recognition.getCharacter());
        result.setStrokeOrderScore(validation.getScore());
        result.setStrokeOrderFeedback(validation.getFeedback());
        
        // Calculate overall score
        result.setOverallScore(calculateOverallScore(recognition.getConfidence(), validation.getScore()));
        
        return result;
    }
    
    private double calculateOverallScore(double recognitionConfidence, double strokeOrderScore) {
        return (recognitionConfidence * 0.7) + (strokeOrderScore * 0.3);
    }
    
    public void clearCanvas() {
        drawingCanvas.drawColor(Color.WHITE);
        strokes.clear();
        currentPath = null;
    }
    
    public Bitmap getDrawingBitmap() {
        return drawingBitmap;
    }
    
    public List<Stroke> getStrokes() {
        return new ArrayList<>(strokes);
    }
    
    public static class Stroke {
        private List<Point> points;
        private boolean finished;
        
        public Stroke() {
            points = new ArrayList<>();
            finished = false;
        }
        
        public void addPoint(float x, float y) {
            points.add(new Point(x, y));
        }
        
        public void finish() {
            finished = true;
        }
        
        public List<Point> getPoints() { return points; }
        public boolean isFinished() { return finished; }
        
        public static class Point {
            public float x, y;
            public Point(float x, float y) {
                this.x = x;
                this.y = y;
            }
        }
    }
    
    public static class RecognitionResult {
        private String recognizedCharacter;
        private double confidence;
        private double strokeOrderScore;
        private String strokeOrderFeedback;
        private double overallScore;
        private String feedback;
        
        // Getteri i setteri
        public String getRecognizedCharacter() { return recognizedCharacter; }
        public void setRecognizedCharacter(String recognizedCharacter) { this.recognizedCharacter = recognizedCharacter; }
        public double getConfidence() { return confidence; }
        public void setConfidence(double confidence) { this.confidence = confidence; }
        public double getStrokeOrderScore() { return strokeOrderScore; }
        public void setStrokeOrderScore(double strokeOrderScore) { this.strokeOrderScore = strokeOrderScore; }
        public String getStrokeOrderFeedback() { return strokeOrderFeedback; }
        public void setStrokeOrderFeedback(String strokeOrderFeedback) { this.strokeOrderFeedback = strokeOrderFeedback; }
        public double getOverallScore() { return overallScore; }
        public void setOverallScore(double overallScore) { this.overallScore = overallScore; }
        public String getFeedback() { return feedback; }
        public void setFeedback(String feedback) { this.feedback = feedback; }
    }
}
