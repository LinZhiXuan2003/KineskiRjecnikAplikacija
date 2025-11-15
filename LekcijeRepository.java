package org.example.kineskagramatika.data;

import org.example.kineskagramatika.domain.Rijec;
import java.util.*;
import java.util.stream.Collectors;

public class RjecnikRepository {
    private List<Rijec> rijeci;
    
    public RjecnikRepository() {
        rijeci = new ArrayList<>();
        ucitajOsnovneRijeci();
    }
    
    private void ucitajOsnovneRijeci() {
        // Osnovne riječi
        rijeci.add(new Rijec("我", "wǒ", "ja", "osobne zamjenice"));
        rijeci.add(new Rijec("你", "nǐ", "ti", "osobne zamjenice"));
        rijeci.add(new Rijec("他", "tā", "on", "osobne zamjenice"));
        rijeci.add(new Rijec("是", "shì", "biti", "glagoli"));
        rijeci.add(new Rijec("在", "zài", "biti u/na", "glagoli"));
        rijeci.add(new Rijec("有", "yǒu", "imati", "glagoli"));
        rijeci.add(new Rijec("人", "rén", "čovjek", "imenice"));
        rijeci.add(new Rijec("学生", "xuéshēng", "student", "imenice"));
        rijeci.add(new Rijec("老师", "lǎoshī", "učitelj", "imenice"));
        rijeci.add(new Rijec("好", "hǎo", "dobar", "pridevi"));
        rijeci.add(new Rijec("大", "dà", "velik", "pridevi"));
        rijeci.add(new Rijec("小", "xiǎo", "mali", "pridevi"));
    }
    
    public List<Rijec> getSveRijeci() {
        return new ArrayList<>(rijeci);
    }
    
    public List<Rijec> pretraziRijeci(String pojam) {
        return rijeci.stream()
            .filter(r -> r.getKineski().contains(pojam) || 
                        r.getPinyin().contains(pojam) || 
                        r.getPrijevod().toLowerCase().contains(pojam.toLowerCase()))
            .collect(Collectors.toList());
    }
    
    public List<Rijec> getRijeciPoKategoriji(String kategorija) {
        return rijeci.stream()
            .filter(r -> r.getKategorija().equalsIgnoreCase(kategorija))
            .collect(Collectors.toList());
    }
    
    public List<String> getKategorije() {
        return rijeci.stream()
            .map(Rijec::getKategorija)
            .distinct()
            .collect(Collectors.toList());
    }
}
