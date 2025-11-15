package org.example.kineskirjecnikaplikacija.domain;

import java.util.List;
import java.util.ArrayList;

public class Lekcija {
  private int id;
  private String naslov;
  private String opis;
  private int tezina ///1-5
  private List<String> sadrzaj;
  private boolean zavrsena;

  public Lekcija(int id, String naslov, String opis, int tezina) {
    this.id = id;
    this.naslov = naslov;
    this.opis = opis;
    this.tezina = tezina;
    this.sadrzaj = new ArrayList<>();
    this.zavrsena = false;
  }


  //Getteri
   public int getId() { return id; }
   public String getNaslov() { return naslov; }
   public String getOpis() { return opis; }
   public int getTezina() { return tezina; }
   public List<String> getSadrzaj() { return sadrzaj; }
   public boolean isZavrsena() { return zavrsena; }

  //Setteri
  public void setZavrsena(boolean zavrsena) { this.zavrsena = zavrsena; }
  public void dodajSadrzaj(String linija) { sadrzaj.add(linija); }

  @Override
  public String toString() {
  return String.format("Lekcija %d: %s (%s)", id, naslov,
  tezina == 1 ? "Početnik" : tezina == 2 ? "Lako" : 
  tezina == 3 ? "Srednje" : tezina == 4 ? "Teško" : "Napredno");                     
  }
}
