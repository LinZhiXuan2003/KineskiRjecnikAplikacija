package org.example.kineskagramatika.domain;

public class Rijec {
    private String kineski;
    private String pinyin;
    private String prijevod;
    private String kategorija;
    
    public Rijec(String kineski, String pinyin, String prijevod, String kategorija) {
        this.kineski = kineski;
        this.pinyin = pinyin;
        this.prijevod = prijevod;
        this.kategorija = kategorija;
    }
    
    // Getteri
    public String getKineski() { return kineski; }
    public String getPinyin() { return pinyin; }
    public String getPrijevod() { return prijevod; }
    public String getKategorija() { return kategorija; }
    
    @Override
    public String toString() {
        return String.format("%s - %s - %s", kineski, pinyin, prijevod);
    }
}

  
}
