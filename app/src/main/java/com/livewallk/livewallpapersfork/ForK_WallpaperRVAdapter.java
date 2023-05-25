package com.livewallk.livewallpapersfork;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class ForK_WallpaperRVAdapter extends RecyclerView.Adapter<ForK_WallpaperRVAdapter.ViewHolder> {
    private ArrayList<String> forKWallPaperList;
    private Context context;


    public ForK_WallpaperRVAdapter(ArrayList<String> wallPaperList, Context context) {
        this.forKWallPaperList = wallPaperList;
        this.context = context;
    }

    @NonNull
    @Override
    public ForK_WallpaperRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.livewall_wallpaper_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForK_WallpaperRVAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Glide.with(context).load(forKWallPaperList.get(position)).into(holder.liveWallpaperIV);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, ForK_WallpaperActivity.class);
                i.putExtra("forkImgUrl", forKWallPaperList.get(position));
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return forKWallPaperList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView liveWallpaperIV;
        private CardView imageForkCV;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageForkCV = itemView.findViewById(R.id.idForKCVWallpaper);
            liveWallpaperIV = itemView.findViewById(R.id.idForKIVWallpaper);

        }
    }
}