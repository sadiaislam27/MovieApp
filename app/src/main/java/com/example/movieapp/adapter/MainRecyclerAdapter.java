package com.example.movieapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.movieapp.R;
import com.example.movieapp.model.AllCategory;
import com.example.movieapp.model.CategoryItem;

import java.util.List;

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.MainViewHolder> {
    Context context;
    List<AllCategory> allCategoryList;

    public MainRecyclerAdapter(Context context,List<AllCategory> categoryList) {
        this.context = context;
        this.allCategoryList = categoryList;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(context).inflate(R.layout.main_recycler_row_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
          holder.categoryName.setText(allCategoryList.get(position).getCategoryTitle());
          setItemRecycler(holder.itemRecycler,allCategoryList.get(position).getCategoryItemList());
    }

    @Override
    public int getItemCount() {
        return allCategoryList.size();
    }

    public static class MainViewHolder extends RecyclerView.ViewHolder{
        TextView categoryName;
        RecyclerView itemRecycler;

            public MainViewHolder(@NonNull View itemView) {
                super(itemView);
                categoryName=itemView.findViewById(R.id.tv_category);
                itemRecycler=itemView.findViewById(R.id.rv_category);
            }
        }

        private void setItemRecycler(RecyclerView recyclerView, List<CategoryItem> categoryItemList){
                ItemRecyclerAdapter itemRecyclerAdapter=new ItemRecyclerAdapter(context,categoryItemList);
                recyclerView.setLayoutManager(new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false));
                recyclerView.setAdapter(itemRecyclerAdapter);
        }

}
