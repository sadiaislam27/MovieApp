package com.example.movieapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.movieapp.MovieDetails;
import com.example.movieapp.R;
import com.example.movieapp.model.CategoryItem;

import java.util.List;

public class ItemRecyclerAdapter extends RecyclerView.Adapter<ItemRecyclerAdapter.ItemViewHolder> {
   Context context;
   List<CategoryItem> categoryItemList;

    public ItemRecyclerAdapter(Context context,List<CategoryItem> categoryItemList) {
        this.context = context;
        this.categoryItemList = categoryItemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.cat_recycler_row_items,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, final int position) {

        Glide.with(context)
                .load(categoryItemList.get(holder.getAdapterPosition()).getImageUrl())
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .error(R.drawable.ic_baseline_broken_image_24)
                .addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        e.printStackTrace();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(holder.itemImage);

        holder.itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Intent i=new Intent(context, MovieDetails.class);
                i.putExtra("movie",categoryItemList.get(position));
//                i.putExtra("movieId",categoryItemList.get(position).getId());
//                i.putExtra("movieName",categoryItemList.get(position).getMovieName());
//                i.putExtra("movieImageurl",categoryItemList.get(position).getImageUrl());
//                i.putExtra("movieDetails",categoryItemList.get(position).getDetails());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryItemList.size();
    }

    public static final class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage=itemView.findViewById(R.id.item_image);
        }
    }
}
