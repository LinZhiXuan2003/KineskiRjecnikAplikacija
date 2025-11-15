package org.example.kineskagramatika.data;

import org.example.kineskagramatika.domain.Pitanje;
import java.util.*;

public class KvizRepository {
    private List<Pitanje> pitanja;
    
    public KvizRepository() {
        pitanja = new ArrayList<>();
        ucitajPitanja();
    }
    
    private void ucitajPitanja() {
        // Multiple choice pitanja
        pitanja.add(new Pitanje(
            "Kako se kaže 'Ja učim kineski'?",
            new String[]{"我学习中文", "你学习中文", "他学习中文", "我们学习中文"},
            0,
            "multiple_choice"
        ));
        
        pitanja.add(new Pitanje(
            "Koji je ton u riječi 'mā' (majka)?",
            new String[]{"1. ton", "2. ton", "3. ton", "4. ton"},
            0,
            "multiple_choice"
        ));
        
        pitanja.add(new Pitanje(
            "Što znači '学生'?",
            new String[]{"učitelj", "student", "škola", "knjiga"},
            1,
            "multiple_choice"
        ));
        
        pitanja.add(new Pitanje(
            "Kako se postavlja da/ne pitanje?",
            new String[]{"koristeći 吗", "koristeći 呢", "koristeći 吧", "promjenom reda riječi"},
            0,
            "multiple_choice"
        ));
        
        pitanja.add(new Pitanje(
            "Koji je pojednostavljeni oblik od '愛'?",
            new String[]{"爱", "受", "亲", "心"},
            0,
            "multiple_choice"
        ));
    }
    
    public List<Pitanje> getSvaPitanja() {
        return new ArrayList<>(pitanja);
    }
    
    public List<Pitanje> getPitanjaZaKviz(int brojPitanja) {
        Collections.shuffle(pitanja);
        return pitanja.subList(0, Math.min(brojPitanja, pitanja.size()));
    }
    
    public Pitanje getPitanjeById(int index) {
        if (index >= 0 && index < pitanja.size()) {
            return pitanja.get(index);
        }
        return null;
    }
}
