package com.example.movieapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.movieapp.MovieDetails;
import com.example.movieapp.R;
import com.example.movieapp.model.CategoryItem;

import java.util.List;

public class BannerMoviesPagerAdapter extends PagerAdapter {
    Context context;
    List<CategoryItem> moviebannerList;

    public BannerMoviesPagerAdapter(Context context,List<CategoryItem> moviebannerList) {
        this.context = context;
        this.moviebannerList = moviebannerList;
    }
    @Override
    public int getCount() {
        return moviebannerList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {

        return object == view;
        //return ((Fragment)object).getView() == view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view= LayoutInflater.from(context).inflate(R.layout.banner_movie_layout,container,false);
        ImageView bannerImage=view.findViewById(R.id.banner);

        Glide
                .with(context)
                .load(moviebannerList.get(position).getImageUrl())
                .placeholder(R.drawable.ic_baseline_broken_image_24)
                .into(bannerImage);

        container.addView(view);

        bannerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context, MovieDetails.class);
                i.putExtra("movie",moviebannerList.get(position));
                context.startActivity(i);
            }
        });
        return view;
    }
}
