package org.example.kineskagramatika.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.example.kineskagramatika.R;
import org.example.kineskagramatika.domain.Lekcija;
import java.util.List;

public class LekcijeAdapter extends RecyclerView.Adapter<LekcijeAdapter.LekcijaViewHolder> {
    
    private List<Lekcija> lekcije;
    private OnLekcijaClickListener listener;
    
    public interface OnLekcijaClickListener {
        void onLekcijaClick(Lekcija lekcija);
    }
    
    public LekcijeAdapter(List<Lekcija> lekcije, OnLekcijaClickListener listener) {
        this.lekcije = lekcije;
        this.listener = listener;
    }
    
    @NonNull
    @Override
    public LekcijaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_lekcija, parent, false);
        return new LekcijaViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull LekcijaViewHolder holder, int position) {
        Lekcija lekcija = lekcije.get(position);
        holder.bind(lekcija);
        
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onLekcijaClick(lekcija);
            }
        });
    }
    
    @Override
    public int getItemCount() {
        return lekcije.size();
    }
    
    static class LekcijaViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNaslov;
        private TextView tvOpis;
        private TextView tvTezina;
        private TextView tvStatus;
        
        public LekcijaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNaslov = itemView.findViewById(R.id.tvNaslov);
            tvOpis = itemView.findViewById(R.id.tvOpis);
            tvTezina = itemView.findViewById(R.id.tvTezina);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }
        
        public void bind(Lekcija lekcija) {
            tvNaslov.setText(lekcija.getNaslov());
            tvOpis.setText(lekcija.getOpis());
            
            // Postavi težinu
            String tezinaText = getTezinaText(lekcija.getTezina());
            tvTezina.setText(tezinaText);
            
            // Postavi status
            if (lekcija.isZavrsena()) {
                tvStatus.setText("✅ Završeno");
                tvStatus.setTextColor(itemView.getContext().getColor(R.color.green));
            } else {
                tvStatus.setText("📖 Uči se");
                tvStatus.setTextColor(itemView.getContext().getColor(R.color.orange));
            }
        }
        
        private String getTezinaText(int tezina) {
            switch (tezina) {
                case 1: return "🥢 Početnik";
                case 2: return "📝 Lako";
                case 3: return "🎯 Srednje";
                case 4: return "🔥 Teško";
                case 5: return "🚀 Napredno";
                default: return "📚 Osnovno";
            }
        }
    }
}
