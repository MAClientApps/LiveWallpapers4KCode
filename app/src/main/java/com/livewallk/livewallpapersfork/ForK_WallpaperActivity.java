package com.livewallk.livewallpapersfork;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;


import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.IOException;

public class ForK_WallpaperActivity extends AppCompatActivity {

    private ProgressBar loadingForkPB;
    WallpaperManager wallpaperForKManager;
    String forkImgUrl;
    ImageView forkImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livewall_wallpaper);

        forkImgUrl = getIntent().getStringExtra("forkImgUrl");
        forkImage = findViewById(R.id.image_Fork);
        loadingForkPB = findViewById(R.id.idForKPBLoading);

        Glide.with(this).load(forkImgUrl).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, com.bumptech.glide.request.target.Target<Drawable> target, boolean isFirstResource) {
                loadingForkPB.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, com.bumptech.glide.request.target.Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                loadingForkPB.setVisibility(View.GONE);
                return false;
            }
        }).into(forkImage);



        wallpaperForKManager = WallpaperManager.getInstance(getApplicationContext());
        Button setLiveWallpaper = findViewById(R.id.idBtnSetForKWallpaper);

        setLiveWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Glide.with(ForK_WallpaperActivity.this)
                        .asBitmap().load(forkImgUrl)
                        .listener(new RequestListener<Bitmap>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, com.bumptech.glide.request.target.Target<Bitmap> target, boolean isFirstResource) {
                                Toast.makeText(ForK_WallpaperActivity.this, "Fail to load Image..", Toast.LENGTH_SHORT).show();
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Bitmap resource, Object model, com.bumptech.glide.request.target.Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {

                                try {
                                    wallpaperForKManager.setBitmap(resource);
                                    Toast.makeText(ForK_WallpaperActivity.this, "Wallpaper Set to Home screen.", Toast.LENGTH_SHORT).show();
                                } catch (IOException e) {

                                    Toast.makeText(ForK_WallpaperActivity.this, "Fail to set wallpaper", Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }

                                return false;
                            }
                        }).submit();

                FancyToast.makeText(ForK_WallpaperActivity.this, "Wallpaper Set to Home Screen", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
            }
        });
    }
}