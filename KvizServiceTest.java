package org.example.kineskagramatika.test;

import org.example.kineskagramatika.services.KvizService;
import org.example.kineskagramatika.domain.Pitanje;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

public class KvizServiceTest {
    
    private KvizService kvizService;
    
    @Before
    public void setUp() {
        kvizService = new KvizService();
    }
    
    @Test
    public void testZapocniNoviKviz() {
        int brojPitanja = 3;
        
        kvizService.zapocniNoviKviz(brojPitanja);
        
        Pitanje prvoPitanje = kvizService.getTrenutnoPitanje();
        Assert.assertNotNull("Prvo pitanje ne smije biti null", prvoPitanje);
        Assert.assertNotNull("Tekst pitanja ne smije biti null", prvoPitanje.getPitanje());
        Assert.assertFalse("Lista opcija ne smije biti prazna", 
            prvoPitanje.getOpcije().isEmpty());
    }
    
    @Test
    public void testOdgovoriNaPitanje() {
        kvizService.zapocniNoviKviz(3);
        
        // Dohvati prvo pitanje da znamo točan odgovor
        Pitanje prvoPitanje = kvizService.getTrenutnoPitanje();
        int tocniOdgovor = prvoPitanje.getTocniOdgovor();
        
        // Testiraj točan odgovor
        boolean rezultatTocno = kvizService.odgovoriNaPitanje(tocniOdgovor);
        Assert.assertTrue("Točan odgovor treba vratiti true", rezultatTocno);
        
        // Testiraj netočan odgovor (uzmi netočan index)
        int netocniOdgovor = (tocniOdgovor + 1) % prvoPitanje.getOpcije().size();
        kvizService.zapocniNoviKviz(3); // Resetiraj kviz
        kvizService.getTrenutnoPitanje(); // Preskoči prvo pitanje
        
        boolean rezultatNetocno = kvizService.odgovoriNaPitanje(netocniOdgovor);
        Assert.assertFalse("Netočan odgovor treba vratiti false", rezultatNetocno);
    }
    
    @Test
    public void testJeKvizZavrsen() {
        int brojPitanja = 2;
        kvizService.zapocniNoviKviz(brojPitanja);
        
        // Kviz ne bi trebao biti završen na početku
        Assert.assertFalse("Kviz ne smije biti završen na početku", 
            kvizService.jeKvizZavrsen());
        
        // Odgovori na sva pitanja
        for (int i = 0; i < brojPitanja; i++) {
            Pitanje pitanje = kvizService.getTrenutnoPitanje();
            if (pitanje != null) {
                kvizService.odgovoriNaPitanje(pitanje.getTocniOdgovor());
            }
        }
        
        // Kviz bi sada trebao biti završen
        Assert.assertTrue("Kviz treba biti završen nakon odgovaranja na sva pitanja", 
            kvizService.jeKvizZavrsen());
    }
    
    @Test
    public void testGetRezultat() {
        int brojPitanja = 3;
        kvizService.zapocniNoviKviz(brojPitanja);
        
        // Odgovori točno na sva pitanja
        for (int i = 0; i < brojPitanja; i++) {
            Pitanje pitanje = kvizService.getTrenutnoPitanje();
            if (pitanje != null) {
                kvizService.odgovoriNaPitanje(pitanje.getTocniOdgovor());
            }
        }
        
        KvizService.RezultatKviza rezultat = kvizService.getRezultat();
        
        Assert.assertNotNull("Rezultat ne smije biti null", rezultat);
        Assert.assertEquals("Broj točnih odgovora treba biti jednak broju pitanja", 
            brojPitanja, rezultat.getTocniOdgovori());
        Assert.assertEquals("Ukupno pitanja treba biti jednako broju pitanja", 
            brojPitanja, rezultat.getUkupnoPitanja());
        Assert.assertEquals("Postotak treba biti 100%", 
            100.0, rezultat.getPostotak(), 0.01);
        
        String ocjena = rezultat.getOcjena();
        Assert.assertNotNull("Ocjena ne smije biti null", ocjena);
        Assert.assertFalse("Ocjena ne smije biti prazna", ocjena.isEmpty());
    }
}
