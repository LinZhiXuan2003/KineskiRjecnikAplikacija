package org.example.kineskagramatika.services;

import org.example.kineskagramatika.domain.Pitanje;
import org.example.kineskagramatika.data.KvizRepository;
import java.util.*;

public class KvizService {
    private KvizRepository kvizRepository;
    private List<Pitanje> trenutniKviz;
    private int trenutnoPitanjeIndex;
    private int brojTocnih;
    
    public KvizService() {
        this.kvizRepository = new KvizRepository();
        this.trenutniKviz = new ArrayList<>();
        this.trenutnoPitanjeIndex = 0;
        this.brojTocnih = 0;
    }
    
    public void zapocniNoviKviz(int brojPitanja) {
        trenutniKviz = kvizRepository.getPitanjaZaKviz(brojPitanja);
        trenutnoPitanjeIndex = 0;
        brojTocnih = 0;
    }
    
    public Pitanje getTrenutnoPitanje() {
        if (trenutnoPitanjeIndex < trenutniKviz.size()) {
            return trenutniKviz.get(trenutnoPitanjeIndex);
        }
        return null;
    }
    
    public boolean odgovoriNaPitanje(int odabraniOdgovor) {
        Pitanje trenutno = getTrenutnoPitanje();
        if (trenutno != null) {
            boolean tocno = trenutno.provjeriOdgovor(odabraniOdgovor);
            if (tocno) {
                brojTocnih++;
            }
            trenutnoPitanjeIndex++;
            return tocno;
        }
        return false;
    }
    
    public boolean jeKvizZavrsen() {
        return trenutnoPitanjeIndex >= trenutniKviz.size();
    }
    
    public RezultatKviza getRezultat() {
        return new RezultatKviza(brojTocnih, trenutniKviz.size());
    }
    
    public static class RezultatKviza {
        private int tocniOdgovori;
        private int ukupnoPitanja;
        
        public RezultatKviza(int tocniOdgovori, int ukupnoPitanja) {
            this.tocniOdgovori = tocniOdgovori;
            this.ukupnoPitanja = ukupnoPitanja;
        }
        
        public int getTocniOdgovori() { return tocniOdgovori; }
        public int getUkupnoPitanja() { return ukupnoPitanja; }
        public double getPostotak() { 
            return ukupnoPitanja > 0 ? (tocniOdgovori * 100.0) / ukupnoPitanja : 0; 
        }
        
        public String getOcjena() {
            double postotak = getPostotak();
            if (postotak >= 90) return "Odlično! 🌟";
            if (postotak >= 70) return "Vrlo dobro! 👍";
            if (postotak >= 50) return "Dovoljno! ✅";
            return "Treba vježbati! 📚";
        }
    }
}
