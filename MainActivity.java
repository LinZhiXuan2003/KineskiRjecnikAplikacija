package org.example.kineskagramatika;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import org.example.kineskagramatika.services.UcenjeService;

public class MainActivity extends AppCompatActivity {
    
    private UcenjeService ucenjeService;
    private ProgressBar progressBar;
    private TextView tvProgress;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        ucenjeService = new UcenjeService();
        initViews();
        setupClickListeners();
        updateProgress();
    }
    
    private void initViews() {
        progressBar = findViewById(R.id.progressBar);
        tvProgress = findViewById(R.id.tvProgress);
        
        Button btnLekcije = findViewById(R.id.btnLekcije);
        Button btnRjecnik = findViewById(R.id.btnRjecnik);
        Button btnKviz = findViewById(R.id.btnKviz);
        Button btnStatistika = findViewById(R.id.btnStatistika);
    }
    
    private void setupClickListeners() {
        findViewById(R.id.btnLekcije).setOnClickListener(v -> {
            startActivity(new Intent(this, LekcijeActivity.class));
        });
        
        findViewById(R.id.btnRjecnik).setOnClickListener(v -> {
            startActivity(new Intent(this, RjecnikActivity.class));
        });
        
        findViewById(R.id.btnKviz).setOnClickListener(v -> {
            startActivity(new Intent(this, KvizActivity.class));
        });
        
        findViewById(R.id.btnStatistika).setOnClickListener(v -> {
            // Otvori statistiku
        });
    }
    
    private void updateProgress() {
        double postotak = ucenjeService.getPostotakNapretka();
        progressBar.setProgress((int) postotak);
        tvProgress.setText(String.format("%.0f%%", postotak));
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        updateProgress();
    }
}
