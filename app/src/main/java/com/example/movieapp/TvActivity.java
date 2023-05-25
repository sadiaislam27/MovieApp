package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


import android.os.Bundle;
import android.view.View;

import android.widget.Toolbar;
import com.example.movieapp.adapter.MainRecyclerAdapter;
import com.example.movieapp.model.api.GetData;
import com.example.movieapp.api.MovieFetchedListener;
import com.example.movieapp.model.api.GetDataTvShows;
import com.example.movieapp.api.MovieFetchedListener;
import com.example.movieapp.model.AllCategory;
import com.example.movieapp.model.CategoryItem;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class TvActivity extends AppCompatActivity {

    TabLayout indicatorTab,categoryTab;
    ViewPager bannerMoviesviewPager;
    NestedScrollView nestedScrollView;
    AppBarLayout appBarLayout;

    MainRecyclerAdapter mainRecyclerAdapter;
    RecyclerView mainRecycler;
    private List<AllCategory> allCategoryList = new ArrayList<>();
    Toolbar toolbar;

    // vuleu change korbi na sure na thakle
    private final String POPULAR_URL = "https://api.themoviedb.org/3/tv/popular?api_key=fd4d797928f14c7441729c83d6f83a27";
    private final String TOP_RATED_URL = "https://api.themoviedb.org/3/tv/top_rated?api_key=fd4d797928f14c7441729c83d6f83a27";
    private final String ON_THE_AIR_URL = "https://api.themoviedb.org/3/tv/on_the_air?api_key=fd4d797928f14c7441729c83d6f83a27";
    private final String AIRING_TODAY_URL = "https://api.themoviedb.org/3/tv/airing_today?api_key=fd4d797928f14c7441729c83d6f83a27";
    private final String LATEST_URL= "https://api.themoviedb.org/3/tv/latest?api_key=fd4d797928f14c7441729c83d6f83a27";



    private int idCount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv);

        nestedScrollView=findViewById(R.id.nested_scroll);
        appBarLayout=findViewById(R.id.appbar);
        bannerMoviesviewPager = findViewById(R.id.banner_viewpager);
        mainRecycler = findViewById(R.id.main_recycler);

        setMainRecycler();
    }
    public void setMainRecycler(){
        mainRecycler.setLayoutManager(new LinearLayoutManager(TvActivity.this,LinearLayoutManager.VERTICAL,false));
        mainRecyclerAdapter=new MainRecyclerAdapter(TvActivity.this, allCategoryList);
        mainRecycler.setAdapter(mainRecyclerAdapter);
        loadDataFromApi();
    }

    private void loadDataFromApi(){

        //POPULAR
        new GetDataTvShows(POPULAR_URL, new MovieFetchedListener() {
            @Override
            public void onMovieFetched(List<CategoryItem> items) {

                allCategoryList.add(new AllCategory(idCount,"Popular Tv Shows",items));
                mainRecyclerAdapter.notifyItemInserted(allCategoryList.size());
                idCount++;

            }
        }).execute();


        // TOP RATED
        new GetDataTvShows(TOP_RATED_URL, new MovieFetchedListener() {
            @Override
            public void onMovieFetched(List<CategoryItem> items) {

                allCategoryList.add(new AllCategory(idCount,"Top Rated Tv Shows",items));
                mainRecyclerAdapter.notifyItemInserted(allCategoryList.size());
                idCount++;

            }
        }).execute();

        new GetDataTvShows(ON_THE_AIR_URL, new MovieFetchedListener() {
            @Override
            public void onMovieFetched(List<CategoryItem> items) {

                allCategoryList.add(new AllCategory(idCount,"Tv Shows On The Air",items));
                mainRecyclerAdapter.notifyItemInserted(allCategoryList.size());
                idCount++;

            }
        }).execute();
        new GetDataTvShows(AIRING_TODAY_URL, new MovieFetchedListener() {
            @Override
            public void onMovieFetched(List<CategoryItem> items) {

                allCategoryList.add(new AllCategory(idCount,"Tv Shows Airing Today",items));
                mainRecyclerAdapter.notifyItemInserted(allCategoryList.size());
                idCount++;

            }
        }).execute();

        new GetDataTvShows(LATEST_URL, new MovieFetchedListener() {
            @Override
            public void onMovieFetched(List<CategoryItem> items) {

                allCategoryList.add(new AllCategory(idCount,"Latest Tv Shows",items));
                mainRecyclerAdapter.notifyItemInserted(allCategoryList.size());
                idCount++;

            }
        }).execute();

    }


    private void logOut(){
        FirebaseAuth.getInstance().signOut();
    }

    private void setScrollDefaultState()
    {
        nestedScrollView.fullScroll(View.FOCUS_UP);
        nestedScrollView.scrollTo(0,0);
        appBarLayout.setExpanded(true);
    }

}