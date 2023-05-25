package com.livewallk.livewallpapersfork;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.MaxReward;
import com.applovin.mediation.MaxRewardedAdListener;
import com.applovin.sdk.AppLovinSdk;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ForK_MainActivity extends AppCompatActivity implements ForK_CategoryRVAdapter.CategoryClickInterface {


    private ProgressBar loadingForKPBar;
    private ForK_CategoryRVAdapter forKCategoryRVAdapter;
    private ImageView search_ForkIV;
    private ForK_WallpaperRVAdapter forKWallpaperRVAdapter;
    private EditText search_ForKEdt;
    private ArrayList<String> wallpaperForKArrayList;
    private ArrayList<ForK_CategoryRVModal> forKCategoryRVModals;
    private RecyclerView categoryForK_RV, wallpaperForK_RV;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livewall_home_screen);



        categoryForK_RV = findViewById(R.id.idForkRVCategories);
        wallpaperForK_RV = findViewById(R.id.idForKRVWallpapers);
        search_ForKEdt = findViewById(R.id.idForKEdtSearch);
        search_ForkIV = findViewById(R.id.idForkIVSearch);
        loadingForKPBar = findViewById(R.id.idForKPBLoading);
        wallpaperForKArrayList = new ArrayList<>();
        forKCategoryRVModals = new ArrayList<>();



        LinearLayoutManager manager1 = new LinearLayoutManager(ForK_MainActivity.this, RecyclerView.HORIZONTAL, false);


        forKWallpaperRVAdapter = new ForK_WallpaperRVAdapter(wallpaperForKArrayList, this);
        forKCategoryRVAdapter = new ForK_CategoryRVAdapter(forKCategoryRVModals, this, this);


        categoryForK_RV.setLayoutManager(manager1);
        categoryForK_RV.setAdapter(forKCategoryRVAdapter);


        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);


        wallpaperForK_RV.setLayoutManager(layoutManager);
        wallpaperForK_RV.setAdapter(forKWallpaperRVAdapter);


        getLiveWallCategories();


        getLiveWallpapers();


        search_ForkIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String searchStr = search_ForKEdt.getText().toString();
                if (searchStr.isEmpty()) {
                    Toast.makeText(ForK_MainActivity.this, "Please enter something to search", Toast.LENGTH_SHORT).show();
                } else {

                    getForKWallpapersByCategory(searchStr);
                }
            }
        });

    }

    private void getForKWallpapersByCategory(String category) {

        wallpaperForKArrayList.clear();

        loadingForKPBar.setVisibility(View.VISIBLE);

        String url = "https://api.pexels.com/v1/search?query=" + category + "&per_page=40&page=1";

        RequestQueue queue = Volley.newRequestQueue(ForK_MainActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    loadingForKPBar.setVisibility(View.GONE);

                    JSONArray photos = response.getJSONArray("photos");
                    for (int i = 0; i < photos.length(); i++) {
                        JSONObject photoObj = photos.getJSONObject(i);
                        String forkImgUrl = photoObj.getJSONObject("src").getString("portrait");

                        wallpaperForKArrayList.add(forkImgUrl);
                    }

                    forKWallpaperRVAdapter.notifyDataSetChanged();
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(ForK_MainActivity.this, "Fetching data..", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {

                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "563492ad6f91700001000001054ccb150a594af2b7df3e79e1915a59");

                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }

    private void getLiveWallpapers() {

        wallpaperForKArrayList.clear();
        loadingForKPBar.setVisibility(View.VISIBLE);
        String url = "https://api.pexels.com/v1/curated?per_page=30&page=1";
        RequestQueue queue = Volley.newRequestQueue(ForK_MainActivity.this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                loadingForKPBar.setVisibility(View.GONE);
                try {

                    JSONArray photos = response.getJSONArray("photos");
                    for (int i = 0; i < photos.length(); i++) {
                        JSONObject photoObj = photos.getJSONObject(i);
                        String forkImgUrl = photoObj.getJSONObject("src").getString("portrait");

                        wallpaperForKArrayList.add(forkImgUrl);
                    }
                    forKWallpaperRVAdapter.notifyDataSetChanged();
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(ForK_MainActivity.this, "Please Click on Categories", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {

                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "563492ad6f91700001000001054ccb150a594af2b7df3e79e1915a59");
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }

    private void getLiveWallCategories() {

        forKCategoryRVModals.add(new ForK_CategoryRVModal("Popular", "https://images.pexels.com/photos/4429547/pexels-photo-4429547.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750"));
        forKCategoryRVModals.add(new ForK_CategoryRVModal("New", "https://images.pexels.com/photos/534757/pexels-photo-534757.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750"));
        forKCategoryRVModals.add(new ForK_CategoryRVModal("Sport", "https://images.pexels.com/photos/260409/pexels-photo-260409.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750"));
        forKCategoryRVModals.add(new ForK_CategoryRVModal("Technology", "https://images.unsplash.com/photo-1526374965328-7f61d4dc18c5?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTJ8fHRlY2hub2xvZ3l8ZW58MHx8MHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        forKCategoryRVModals.add(new ForK_CategoryRVModal("Architecture", "https://images.pexels.com/photos/256150/pexels-photo-256150.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"));
        forKCategoryRVModals.add(new ForK_CategoryRVModal("Arts", "https://images.pexels.com/photos/1194420/pexels-photo-1194420.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"));
        forKCategoryRVModals.add(new ForK_CategoryRVModal("Music", "https://images.pexels.com/photos/4348093/pexels-photo-4348093.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"));
        forKCategoryRVModals.add(new ForK_CategoryRVModal("Nature", "https://images.pexels.com/photos/2387873/pexels-photo-2387873.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"));
        forKCategoryRVModals.add(new ForK_CategoryRVModal("Travel", "https://images.pexels.com/photos/672358/pexels-photo-672358.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"));
        forKCategoryRVModals.add(new ForK_CategoryRVModal("Abstract", "https://images.pexels.com/photos/2110951/pexels-photo-2110951.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"));
        forKCategoryRVModals.add(new ForK_CategoryRVModal("Cars", "https://images.pexels.com/photos/3802510/pexels-photo-3802510.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"));
        forKCategoryRVModals.add(new ForK_CategoryRVModal("Flowers", "https://images.pexels.com/photos/1086178/pexels-photo-1086178.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"));
        forKCategoryRVModals.add(new ForK_CategoryRVModal("Programming", "https://images.unsplash.com/photo-1542831371-29b0f74f9713?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8cHJvZ3JhbW1pbmd8ZW58MHx8MHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
    }

    @Override
    public void onCategoryClick(int position) {
        String category = forKCategoryRVModals.get(position).getCategory();
        getForKWallpapersByCategory(category);

    }

}