package com.livewallk.livewallpapersfork;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;



import java.util.ArrayList;

public class ForK_CategoryRVAdapter extends RecyclerView.Adapter<ForK_CategoryRVAdapter.ViewHolder> {
    
    private ArrayList<ForK_CategoryRVModal> forKCategoryRVModals;
    private Context context;
    private CategoryClickInterface categoryClickInterface;

    
    public ForK_CategoryRVAdapter(ArrayList<ForK_CategoryRVModal> forKCategoryRVModals, Context context, CategoryClickInterface categoryClickInterface) {
        this.forKCategoryRVModals = forKCategoryRVModals;
        this.context = context;
        this.categoryClickInterface = categoryClickInterface;
    }

    @NonNull
    @Override
    public ForK_CategoryRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.livewall_category_rv_item, parent, false);
        return new ForK_CategoryRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForK_CategoryRVAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
       
        ForK_CategoryRVModal modal = forKCategoryRVModals.get(position);
        holder.categoryTXV.setText(modal.getCategory());
        if (!modal.getforkImgUrl().isEmpty()) {
            Glide.with(context).load(modal.getforkImgUrl()).into(holder.categoryIMGV);

        } else {
            holder.categoryIMGV.setImageResource(R.drawable.ic_launcher_background);
        }
       
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               
                categoryClickInterface.onCategoryClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return forKCategoryRVModals.size();
    }

   
    public interface CategoryClickInterface {
        void onCategoryClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
       
        private TextView categoryTXV;
        private ImageView categoryIMGV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           
            categoryIMGV = itemView.findViewById(R.id.idIMgCategory);
            categoryTXV = itemView.findViewById(R.id.idTXTVCategory);
        }
    }
}