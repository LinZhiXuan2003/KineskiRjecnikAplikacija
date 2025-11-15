package org.example.kineskagramatika.services;

import org.example.kineskagramatika.domain.Pitanje;
import java.util.*;

public class KvizTipoviService {
    
    public Pitanje kreirajPitanjeSpajanje() {
        Map<String, String> parovi = new LinkedHashMap<>();
        parovi.put("我", "wǒ");
        parovi.put("你", "nǐ"); 
        parovi.put("是", "shì");
        parovi.put("学生", "xuéshēng");
        
        List<String> lijevo = new ArrayList<>(parovi.keySet());
        List<String> desno = new ArrayList<>(parovi.values());
        Collections.shuffle(desno);
        
        StringBuilder pitanjeText = new StringBuilder("Spoji kineske karaktere s pinjinom:\n");
        for (int i = 0; i < lijevo.size(); i++) {
            pitanjeText.append(String.format("%d. %s - ?\n", i+1, lijevo.get(i)));
        }
        
        // Ovo je pojednostavljeno - u pravoj aplikaciji bi imali poseban tip pitanja
        return new Pitanje(
            pitanjeText.toString(),
            desno.toArray(new String[0]),
            0, // placeholder
            "spoji_parove"
        );
    }
    
    public Pitanje kreirajPitanjeSlaganje() {
        String[] dijelovi = {"我", "学习", "中文", "。"};
        List<String> shuffled = new ArrayList<>(Arrays.asList(dijelovi));
        Collections.shuffle(shuffled);
        
        String pitanjeText = "Složi rečenicu od sljedećih dijelova:\n" + String.join(" ", shuffled);
        
        return new Pitanje(
            pitanjeText,
            new String[]{"我学习中文。"},
            0,
            "slaganje_recenice"
        );
    }
    
    public Pitanje kreirajPitanjePrevod() {
        return new Pitanje(
            "Prevedi na kineski: 'Ja sam student'",
            new String[]{"我是学生", "我是老师", "我学生是", "学生是我"},
            0,
            "prevod"
        );
    }
    
    public Pitanje kreirajPitanjePraznaMjesta() {
        return new Pitanje(
            "Popuni prazno mjesto: 我___学生 (wǒ ___ xuéshēng)",
            new String[]{"是", "在", "有", "学习"},
            0,
            "prazna_mjesta"
        );
    }
}
