package org.example.kineskagramatika;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;
import org.example.kineskagramatika.adapters.DragDropAdapter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DragDropQuizActivity extends AppCompatActivity {
    
    private RecyclerView recyclerView;
    private DragDropAdapter adapter;
    private TextView tvPitanje, tvRezultat;
    private List<String> correctOrder;
    private List<String> shuffledOrder;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_drop);
        
        initViews();
        setupQuiz();
    }
    
    private void initViews() {
        recyclerView = findViewById(R.id.recyclerViewDragDrop);
        tvPitanje = findViewById(R.id.tvDragDropPitanje);
        tvRezultat = findViewById(R.id.tvDragDropRezultat);
        
        findViewById(R.id.btnProvjeri).setOnClickListener(v -> provjeriRedoslijed());
        findViewById(R.id.btnReset).setOnClickListener(v -> resetQuiz());
    }
    
    private void setupQuiz() {
        // Primjer: Složi rečenicu u pravilnom redoslijedu
        correctOrder = Arrays.asList("我", "今天", "学习", "中文", "。");
        shuffledOrder = Arrays.asList("今天", "中文", "我", "学习", "。");
        Collections.shuffle(shuffledOrder);
        
        tvPitanje.setText("Složi rečenicu u pravilnom redoslijedu:");
        
        adapter = new DragDropAdapter(shuffledOrder);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        
        // Omogući povlačenje i ispuštanje
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                return makeMovementFlags(dragFlags, 0);
            }
            
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                adapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }
            
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // Ne koristimo swiping
            }
            
            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
                if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                    viewHolder.itemView.setAlpha(0.6f);
                }
            }
            
            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                viewHolder.itemView.setAlpha(1.0f);
            }
        });
        
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
    
    private void provjeriRedoslijed() {
        List<String> currentOrder = adapter.getCurrentOrder();
        boolean isCorrect = currentOrder.equals(correctOrder);
        
        if (isCorrect) {
            tvRezultat.setText("✅ Točno! Rečenica je: 我今天学习中文。");
            tvRezultat.setTextColor(getColor(R.color.green));
            tvRezultat.startAnimation(AnimationUtils.loadAnimation(this, R.anim.bounce));
            
            // Animiraj točne odgovore
            for (int i = 0; i < recyclerView.getChildCount(); i++) {
                View child = recyclerView.getChildAt(i);
                child.startAnimation(AnimationUtils.loadAnimation(this, R.anim.correct_answer));
            }
        } else {
            tvRezultat.setText("❌ Netočno. Pokušajte ponovno!");
            tvRezultat.setTextColor(getColor(R.color.accent));
            tvRezultat.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake));
        }
    }
    
    private void resetQuiz() {
        Collections.shuffle(shuffledOrder);
        adapter.updateData(shuffledOrder);
        tvRezultat.setText("");
    }
}
