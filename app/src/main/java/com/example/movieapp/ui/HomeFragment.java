package com.example.movieapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.movieapp.HomeActivity;
import com.example.movieapp.MoviesActivity;
import com.example.movieapp.R;
import com.example.movieapp.SearchActivity;
import com.example.movieapp.TvActivity;
import com.example.movieapp.adapter.BannerMoviesPagerAdapter;
import com.example.movieapp.adapter.ItemRecyclerAdapter;
import com.example.movieapp.adapter.MainRecyclerAdapter;
import com.example.movieapp.model.api.GetData;
import com.example.movieapp.api.MovieFetchedListener;
//import com.example.movieapp.api.MovieFetchedListener;
import com.example.movieapp.model.AllCategory;
import com.example.movieapp.model.CategoryItem;
import com.example.movieapp.model.api.GetDataTvShows;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class HomeFragment extends Fragment {

    BannerMoviesPagerAdapter bannerMoviesPagerAdapter;
    TabLayout indicatorTab,categoryTab;
    ViewPager bannerMoviesviewPager;
    List<CategoryItem> homeBannerList;
    List<CategoryItem> tvShowBannerList;
    List<CategoryItem> movieBannerList;
    List<CategoryItem> kidsBannerList;
    NestedScrollView nestedScrollView;
    AppBarLayout appBarLayout;
    private ImageView ivSearch;
    private ImageView login;


    MainRecyclerAdapter mainRecyclerAdapter;
    RecyclerView mainRecycler;
    private List<AllCategory> allCategoryList = new ArrayList<>();
    Toolbar toolbar;

    // vuleu change korbi na sure na thakle
    private final String TOP_RATED_URL = "https://api.themoviedb.org/3/movie/top_rated?api_key=fd4d797928f14c7441729c83d6f83a27";
    private final String TOP_RATED_TV_URL = "https://api.themoviedb.org/3/tv/top_rated?api_key=fd4d797928f14c7441729c83d6f83a27";
//    private final String UPCOMING_URL = "https://api.themoviedb.org/3/movie/upcoming?api_key=fd4d797928f14c7441729c83d6f83a27";
//    private final String POPULAR_URL = "https://api.themoviedb.org/3/movie/popular?api_key=fd4d797928f14c7441729c83d6f83a27";
//    private final String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=fd4d797928f14c7441729c83d6f83a27";
//    private final String LATEST_URL= "https://api.themoviedb.org/3/movie/top_rated?api_key=fd4d797928f14c7441729c83d6f83a27";

    private int idCount = 1;

    private RecyclerView recyclerViewMovie,recyclerViewTS;
    private TextView tvShowMoreMovie, tvShowMoreTs;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        indicatorTab= view.findViewById(R.id.tab_indicator2);
        categoryTab=view.findViewById(R.id.tabLayout);
        nestedScrollView=view.findViewById(R.id.nested_scroll);
        appBarLayout=view.findViewById(R.id.appbar);
        bannerMoviesviewPager = view.findViewById(R.id.banner_viewpager);
        mainRecycler = view.findViewById(R.id.main_recycler);
        ivSearch = view.findViewById(R.id.ivSearch);
        login=view.findViewById(R.id.ivLogin);
        tvShowMoreMovie = view.findViewById(R.id.tvShowMoreMovie);
        tvShowMoreTs = view.findViewById(R.id.tvShowMoreTS);
        recyclerViewMovie = view.findViewById(R.id.rvMovies);
        recyclerViewTS = view.findViewById(R.id.rvTvShow);


        tvShowMoreMovie.setOnClickListener(view1 -> startActivity(new Intent(requireActivity(), MoviesActivity.class)));
        tvShowMoreTs.setOnClickListener(view1 -> startActivity(new Intent(requireActivity(), TvActivity.class)));


        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(requireActivity(), SearchActivity.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(requireActivity(), HomeActivity.class));
            }
        });

        handleRest();
        setMainRecycler();
    }


    private void handleRest(){
        Timer slideTimer=new Timer();
        slideTimer.scheduleAtFixedRate(new AutoSlider(),3000,6000);
        indicatorTab.setupWithViewPager(bannerMoviesviewPager,true);

        homeBannerList=new ArrayList<>();
        homeBannerList.add(new CategoryItem(1,"Intersteller","https://i.postimg.cc/Jhhz5cGY/Intersteller.jpg","",""));
        homeBannerList.add(new CategoryItem(2,"The Pianist","https://i.postimg.cc/5yy2bW3L/piano.jpg","",""));
        homeBannerList.add(new CategoryItem(3,"Black Widow","https://i.postimg.cc/HxHHzcP6/blackwidow.webp","",""));
        homeBannerList.add(new CategoryItem(4,"Avatar","https://i.postimg.cc/fb8CkxsX/AVATAR1.webp","",""));

        tvShowBannerList=new ArrayList<>();
        tvShowBannerList.add(new CategoryItem(1,"GOT","https://i.postimg.cc/SsqwWLYK/GOTT.jpg","",""));
        tvShowBannerList.add(new CategoryItem(2,"Witcher","https://i.postimg.cc/W3sj8cqg/wit.jpg","",""));
        tvShowBannerList.add(new CategoryItem(3,"Dark","https://i.postimg.cc/1RH0JG2q/darkk.jpg","",""));
        tvShowBannerList.add(new CategoryItem(4,"Breaking Bad","https://i.postimg.cc/DzSFYPxy/Breaking-Bad-940.jpg","",""));

        movieBannerList=new ArrayList<>();
        movieBannerList.add(new CategoryItem(1,"Black Panther","https://i.postimg.cc/CKQHHSQ6/bl.webp","",""));
        movieBannerList.add(new CategoryItem(2,"Spider Man","https://i.postimg.cc/rmYnm7W5/Spiderman-Remastered-PC.jpg","",""));
        movieBannerList.add(new CategoryItem(3,"Bat Man","https://i.postimg.cc/85PxjJ8g/batman.jpg","",""));
        movieBannerList.add(new CategoryItem(4,"Joker","https://i.postimg.cc/mgYnjNBG/joker.jpg","",""));

        kidsBannerList=new ArrayList<>();
        kidsBannerList.add(new CategoryItem(1,"Finding Nemo","https://i.postimg.cc/fL1tkbrD/nemoo.webp","",""));
        kidsBannerList.add(new CategoryItem(2,"Spirited Away","https://i.postimg.cc/QNgnQWjB/spirited.jpg","",""));
        kidsBannerList.add(new CategoryItem(3,"Coco","https://i.postimg.cc/ZRbvXgyz/cocoo.jpg","",""));
        kidsBannerList.add(new CategoryItem(4,"Ratatoullie","https://i.postimg.cc/br6rXwfQ/rata.jpg","",""));

        setBannerMoviesPagerAdapter(homeBannerList);
        categoryTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition())
                {
                    case 1:
                        setScrollDefaultState();
                        setBannerMoviesPagerAdapter(tvShowBannerList);
                        return;
                    case 2:
                        setScrollDefaultState();
                        setBannerMoviesPagerAdapter(movieBannerList);
                        return;
                    case 3:
                        setScrollDefaultState();
                        setBannerMoviesPagerAdapter(kidsBannerList);
                        return;
                    default:
                        setScrollDefaultState();
                        setBannerMoviesPagerAdapter(homeBannerList);

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setBannerMoviesPagerAdapter( List<CategoryItem> moviebannerList)
    {
        bannerMoviesPagerAdapter=new BannerMoviesPagerAdapter(requireActivity(),moviebannerList);
        bannerMoviesviewPager.setAdapter(bannerMoviesPagerAdapter);
        indicatorTab.setupWithViewPager(bannerMoviesviewPager);
    }
    class AutoSlider extends TimerTask {

        @Override
        public void run() {

            try {
                if (isAdded()) {
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (bannerMoviesviewPager.getCurrentItem() < homeBannerList.size() - 1) {
                                bannerMoviesviewPager.setCurrentItem(bannerMoviesviewPager.getCurrentItem() + 1);
                            } else {
                                bannerMoviesviewPager.setCurrentItem(0);
                            }

                        }
                    });
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void setMainRecycler(){
//        mainRecycler.setLayoutManager(new LinearLayoutManager(requireActivity(),LinearLayoutManager.VERTICAL,false));
//        mainRecyclerAdapter=new MainRecyclerAdapter(requireActivity(), allCategoryList);
//        mainRecycler.setAdapter(mainRecyclerAdapter);
        loadDataFromApi();
    }

    private void loadDataFromApi(){


        // TOP RATED
        new GetData(TOP_RATED_URL, new MovieFetchedListener() {
            @Override
            public void onMovieFetched(List<CategoryItem> items) {

                ItemRecyclerAdapter itemRecyclerAdapter=new ItemRecyclerAdapter(requireActivity(),items);
                recyclerViewMovie.setAdapter(itemRecyclerAdapter);

            }
        }).execute();

        // TOP RATED
        new GetDataTvShows(TOP_RATED_TV_URL, new MovieFetchedListener() {
            @Override
            public void onMovieFetched(List<CategoryItem> items) {

                ItemRecyclerAdapter itemRecyclerAdapter=new ItemRecyclerAdapter(requireActivity(),items);
                recyclerViewTS.setAdapter(itemRecyclerAdapter);

            }
        }).execute();

//
//        new GetData(UPCOMING_URL, new MovieFetchedListener() {
//            @Override
//            public void onMovieFetched(List<CategoryItem> items) {
//
//                allCategoryList.add(new AllCategory(idCount,"UpComing Movies",items));
//                mainRecyclerAdapter.notifyItemInserted(allCategoryList.size());
//                idCount++;
//
//            }
//        }).execute();
//
//        new GetData(NOW_PLAYING_URL, new MovieFetchedListener() {
//            @Override
//            public void onMovieFetched(List<CategoryItem> items) {
//
//                allCategoryList.add(new AllCategory(idCount,"Now Playing Movies",items));
//                mainRecyclerAdapter.notifyItemInserted(allCategoryList.size());
//                idCount++;
//
//            }
//        }).execute();
//
//        new GetData(POPULAR_URL, new MovieFetchedListener() {
//            @Override
//            public void onMovieFetched(List<CategoryItem> items) {
//
//                allCategoryList.add(new AllCategory(idCount,"Popular Movies",items));
//                mainRecyclerAdapter.notifyItemInserted(allCategoryList.size());
//                idCount++;
//
//            }
//        }).execute();
//
//        new GetData(LATEST_URL, new MovieFetchedListener() {
//            @Override
//            public void onMovieFetched(List<CategoryItem> items) {
//
//                allCategoryList.add(new AllCategory(idCount,"Latest Movies",items));
//                mainRecyclerAdapter.notifyItemInserted(allCategoryList.size());
//                idCount++;
//
//            }
//        }).execute();

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
