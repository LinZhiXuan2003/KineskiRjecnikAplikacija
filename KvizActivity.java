package org.example.kineskagramatika;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import org.example.kineskagramatika.R;
import org.example.kineskagramatika.domain.Pitanje;
import org.example.kineskagramatika.services.KvizService;

public class KvizActivity extends AppCompatActivity {
    
    private TextView tvPitanje, tvBrojPitanja;
    private RadioGroup radioGroup;
    private RadioButton[] radioButtons;
    private Button btnSljedece;
    
    private KvizService kvizService;
    private int trenutniBrojPitanja = 1;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kviz);
        
        kvizService = new KvizService();
        initViews();
        zapocniKviz();
    }
    
    private void initViews() {
        tvPitanje = findViewById(R.id.tvPitanje);
        tvBrojPitanja = findViewById(R.id.tvBrojPitanja);
        radioGroup = findViewById(R.id.radioGroup);
        btnSljedece = findViewById(R.id.btnSljedece);
        
        radioButtons = new RadioButton[] {
            findViewById(R.id.radioOption1),
            findViewById(R.id.radioOption2),
            findViewById(R.id.radioOption3),
            findViewById(R.id.radioOption4)
        };
        
        btnSljedece.setOnClickListener(v -> obradiOdgovor());
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }
    
    private void zapocniKviz() {
        kvizService.zapocniNoviKviz(5); // 5 pitanja
        prikaziTrenutnoPitanje();
    }
    
    private void prikaziTrenutnoPitanje() {
        Pitanje pitanje = kvizService.getTrenutnoPitanje();
        
        if (pitanje == null) {
            zavrsiKviz();
            return;
        }
        
        tvPitanje.setText(pitanje.getPitanje());
        tvBrojPitanja.setText(String.format("Pitanje %d/5", trenutniBrojPitanja));
        
        List<String> opcije = pitanje.getOpcije();
        for (int i = 0; i < radioButtons.length; i++) {
            if (i < opcije.size()) {
                radioButtons[i].setText(opcije.get(i));
                radioButtons[i].setVisibility(View.VISIBLE);
            } else {
                radioButtons[i].setVisibility(View.GONE);
            }
        }
        
        radioGroup.clearCheck();
        btnSljedece.setEnabled(false);
        
        // Omoguci dugme kada se odabere opcija
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            btnSljedece.setEnabled(true);
        });
    }
    
    private void obradiOdgovor() {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        if (selectedId == -1) {
            Toast.makeText(this, "Odaberite odgovor!", Toast.LENGTH_SHORT).show();
            return;
        }
        
        int selectedIndex = -1;
        for (int i = 0; i < radioButtons.length; i++) {
            if (radioButtons[i].getId() == selectedId) {
                selectedIndex = i;
                break;
            }
        }
        
        boolean tocno = kvizService.odgovoriNaPitanje(selectedIndex);
        
        if (tocno) {
            Toast.makeText(this, "✅ Točno!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "❌ Netočno!", Toast.LENGTH_SHORT).show();
        }
        
        trenutniBrojPitanja++;
        
        if (kvizService.jeKvizZavrsen()) {
            zavrsiKviz();
        } else {
            prikaziTrenutnoPitanje();
        }
    }
    
    private void zavrsiKviz() {
        KvizService.RezultatKviza rezultat = kvizService.getRezultat();
        
        String poruka = String.format(
            "Kviz završen!\nTočnih odgovora: %d/%d\n%s",
            rezultat.getTocniOdgovori(),
            rezultat.getUkupnoPitanja(),
            rezultat.getOcjena()
        );
        
        new AlertDialog.Builder(this)
            .setTitle("Rezultat kviza")
            .setMessage(poruka)
            .setPositiveButton("OK", (dialog, which) -> finish())
            .setCancelable(false)
            .show();
    }
}
