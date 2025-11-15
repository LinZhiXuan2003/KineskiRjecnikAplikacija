package org.example.kineskagramatika.domain;

import java.util.List;
import java.util.Arrays;

public class Pitanje {
    private String pitanje;
    private List<String> opcije;
    private int tocniOdgovor; // index
    private String tip; // "multiple_choice", "spoji", "prevod"
    
    public Pitanje(String pitanje, String[] opcije, int tocniOdgovor, String tip) {
        this.pitanje = pitanje;
        this.opcije = Arrays.asList(opcije);
        this.tocniOdgovor = tocniOdgovor;
        this.tip = tip;
    }
    
    // Getteri
    public String getPitanje() { return pitanje; }
    public List<String> getOpcije() { return opcije; }
    public int getTocniOdgovor() { return tocniOdgovor; }
    public String getTip() { return tip; }
    
    public boolean provjeriOdgovor(int odgovor) {
        return odgovor == tocniOdgovor;
    }
    
    public String getTocniOdgovorTekst() {
        return opcije.get(tocniOdgovor);
    }
}
