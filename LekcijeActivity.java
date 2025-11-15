package org.example.kineskagramatika;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.Toast;
import org.example.kineskagramatika.adapters.LekcijeAdapter;
import org.example.kineskagramatika.domain.Lekcija;
import org.example.kineskagramatika.services.UcenjeService;
import java.util.List;

public class LekcijeActivity extends AppCompatActivity {
    
    private RecyclerView recyclerView;
    private LekcijeAdapter adapter;
    private UcenjeService ucenjeService;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lekcije);
        
        ucenjeService = new UcenjeService();
        initViews();
        loadLekcije();
    }
    
    private void initViews() {
        recyclerView = findViewById(R.id.recyclerViewLekcije);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }
    
    private void loadLekcije() {
        List<Lekcija> lekcije = ucenjeService.getSveLekcije();
        
        adapter = new LekcijeAdapter(lekcije, this::onLekcijaClicked);
        recyclerView.setAdapter(adapter);
    }
    
    private void onLekcijaClicked(Lekcija lekcija) {
        // Ovdje otvori detalje lekcije
        Toast.makeText(this, 
            "Otvara se: " + lekcija.getNaslov(), 
            Toast.LENGTH_SHORT).show();
        
        // Oznaci kao zavrsenu za demo
        ucenjeService.oznaciLekcijuKaoZavrsenu(lekcija.getId());
        adapter.notifyDataSetChanged();
    }
}
