package com.example.movieapp.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.movieapp.R;
import com.example.movieapp.model.EachMovie;
import com.example.movieapp.model.EachTheatre;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ItemViewHolder> {
    Context context;
    List<EachMovie> movies;

    public MovieAdapter(Context context,List<EachMovie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.movie_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, final int position) {

        EachMovie movie = movies.get(position);

        holder.tvName.setText(movie.getName());
        holder.tvReleaseDate.setText(movie.getReleaseDate());
        holder.tvRating.setText(movie.getRatings()+"");

        Glide.with(context)
                .load(movie.getImgUrl())
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .error(R.drawable.ic_baseline_broken_image_24)
                .into(holder.tvImage);

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static final class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName,tvReleaseDate, tvRating;
        private ImageView tvImage;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvImage=itemView.findViewById(R.id.item_image);
            tvName=itemView.findViewById(R.id.textViewName1);
            tvReleaseDate=itemView.findViewById(R.id.textViewRelease1);
            tvRating=itemView.findViewById(R.id.textViewRatings1);
        }
    }
}

