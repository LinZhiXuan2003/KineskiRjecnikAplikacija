package org.example.kineskagramatika.test;

import org.example.kineskagramatika.services.UcenjeService;
import org.example.kineskagramatika.domain.Lekcija;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import java.util.List;

public class UcenjeServiceTest {
    
    private UcenjeService ucenjeService;
    
    @Before
    public void setUp() {
        ucenjeService = new UcenjeService();
    }
    
    @Test
    public void testGetSveLekcije() {
        List<Lekcija> lekcije = ucenjeService.getSveLekcije();
        
        Assert.assertNotNull("Lista lekcija ne smije biti null", lekcije);
        Assert.assertFalse("Lista lekcija ne smije biti prazna", lekcije.isEmpty());
        
        // Provjeri da li prva lekcija ima očekivane podatke
        Lekcija prvaLekcija = lekcije.get(0);
        Assert.assertEquals("ID prve lekcije treba biti 1", 1, prvaLekcija.getId());
        Assert.assertNotNull("Naslov ne smije biti null", prvaLekcija.getNaslov());
        Assert.assertFalse("Naslov ne smije biti prazan", prvaLekcija.getNaslov().isEmpty());
    }
    
    @Test
    public void testOznaciLekcijuKaoZavrsenu() {
        int lekcijaId = 1;
        
        // Provjeri početno stanje
        Assert.assertFalse("Lekcija ne smije biti završena na početku", 
            ucenjeService.jeLekcijaZavrsena(lekcijaId));
        
        // Oznaci kao zavrsenu
        ucenjeService.oznaciLekcijuKaoZavrsenu(lekcijaId);
        
        // Provjeri da li je sada završena
        Assert.assertTrue("Lekcija treba biti označena kao završena", 
            ucenjeService.jeLekcijaZavrsena(lekcijaId));
    }
    
    @Test
    public void testGetBrojZavrsenihLekcija() {
        int pocetniBroj = ucenjeService.getBrojZavrsenihLekcija();
        
        // Oznaci nekoliko lekcija kao zavrsene
        ucenjeService.oznaciLekcijuKaoZavrsenu(1);
        ucenjeService.oznaciLekcijuKaoZavrsenu(2);
        
        int noviBroj = ucenjeService.getBrojZavrsenihLekcija();
        Assert.assertEquals("Broj završenih lekcija treba biti veći", 
            pocetniBroj + 2, noviBroj);
    }
    
    @Test
    public void testGetPostotakNapretka() {
        int ukupnoLekcija = ucenjeService.getUkupnoLekcija();
        
        // Oznaci sve lekcije kao zavrsene
        for (int i = 1; i <= ukupnoLekcija; i++) {
            ucenjeService.oznaciLekcijuKaoZavrsenu(i);
        }
        
        double postotak = ucenjeService.getPostotakNapretka();
        Assert.assertEquals("Postotak treba biti 100% kada su sve lekcije završene", 
            100.0, postotak, 0.01);
    }
    
    @Test
    public void testGetLekcijePoTezini() {
        int tezina = 1; // Početnik
        
        List<Lekcija> lekcije = ucenjeService.getLekcijePoTezini(tezina);
        
        Assert.assertNotNull("Lista lekcija po težini ne smije biti null", lekcije);
        
        // Provjeri da li sve lekcije imaju traženu težinu
        for (Lekcija lekcija : lekcije) {
            Assert.assertEquals("Sve lekcije trebaju imati zadanu težinu", 
                tezina, lekcija.getTezina());
        }
    }
}
