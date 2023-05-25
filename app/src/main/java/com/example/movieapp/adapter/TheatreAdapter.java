package com.example.movieapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.example.movieapp.model.EachTheatre;

import java.util.List;

public class TheatreAdapter extends RecyclerView.Adapter<TheatreAdapter.ItemViewHolder> {
    Context context;
    List<EachTheatre> theatres;

    public TheatreAdapter(Context context,List<EachTheatre> theatres) {
        this.context = context;
        this.theatres = theatres;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //change cat_recylcer_row_items
        return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.theatre_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, final int position) {

        EachTheatre theatre = theatres.get(position);

        holder.tvName.setText(theatre.getName());
        holder.tvLocation.setText(theatre.getLocation());
        holder.tvRating.setText(theatre.getRatings()+"");

    }

    @Override
    public int getItemCount() {
        return theatres.size();
    }

    public static final class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName,tvLocation, tvRating;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.textViewName1);
            tvLocation=itemView.findViewById(R.id.textViewLocation1);
            tvRating=itemView.findViewById(R.id.textViewRatings1);
        }
    }
}
