package org.example.kineskagramatika.services;

import org.example.kineskagramatika.domain.Lekcija;
import org.example.kineskagramatika.data.LekcijeRepository;
import java.util.*;

public class UcenjeService {
    private LekcijeRepository lekcijeRepository;
    private Set<Integer> zavrseneLekcije;
    
    public UcenjeService() {
        this.lekcijeRepository = new LekcijeRepository();
        this.zavrseneLekcije = new HashSet<>();
    }
    
    public List<Lekcija> getSveLekcije() {
        return lekcijeRepository.getSveLekcije();
    }
    
    public List<Lekcija> getLekcijePoTezini(int tezina) {
        return lekcijeRepository.getLekcijePoTezini(tezina);
    }
    
    public Lekcija getLekcija(int id) {
        return lekcijeRepository.getLekcijaById(id);
    }
    
    public void oznaciLekcijuKaoZavrsenu(int lekcijaId) {
        zavrseneLekcije.add(lekcijaId);
        lekcijeRepository.oznaciKaoZavrsenu(lekcijaId);
    }
    
    public boolean jeLekcijaZavrsena(int lekcijaId) {
        return zavrseneLekcije.contains(lekcijaId);
    }
    
    public int getBrojZavrsenihLekcija() {
        return zavrseneLekcije.size();
    }
    
    public int getUkupnoLekcija() {
        return lekcijeRepository.getSveLekcije().size();
    }
    
    public double getPostotakNapretka() {
        return getUkupnoLekcija() > 0 ? (getBrojZavrsenihLekcija() * 100.0) / getUkupnoLekcija() : 0;
    }
}
