package org.example.kineskagramatika.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.example.kineskagramatika.R;
import org.example.kineskagramatika.domain.Rijec;
import java.util.List;

public class RjecnikAdapter extends RecyclerView.Adapter<RjecnikAdapter.RijecViewHolder> {
    
    private List<Rijec> rijeci;
    
    public RjecnikAdapter(List<Rijec> rijeci) {
        this.rijeci = rijeci;
    }
    
    @NonNull
    @Override
    public RijecViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_rijec, parent, false);
        return new RijecViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull RijecViewHolder holder, int position) {
        Rijec rijec = rijeci.get(position);
        holder.bind(rijec);
    }
    
    @Override
    public int getItemCount() {
        return rijeci.size();
    }
    
    public void updateData(List<Rijec> noveRijeci) {
        this.rijeci = noveRijeci;
        notifyDataSetChanged();
    }
    
    static class RijecViewHolder extends RecyclerView.ViewHolder {
        private TextView tvKineski;
        private TextView tvPinyin;
        private TextView tvPrijevod;
        private TextView tvKategorija;
        
        public RijecViewHolder(@NonNull View itemView) {
            super(itemView);
            tvKineski = itemView.findViewById(R.id.tvKineski);
            tvPinyin = itemView.findViewById(R.id.tvPinyin);
            tvPrijevod = itemView.findViewById(R.id.tvPrijevod);
            tvKategorija = itemView.findViewById(R.id.tvKategorija);
        }
        
        public void bind(Rijec rijec) {
            tvKineski.setText(rijec.getKineski());
            tvPinyin.setText(rijec.getPinyin());
            tvPrijevod.setText(rijec.getPrijevod());
            tvKategorija.setText(rijec.getKategorija());
        }
    }
}
