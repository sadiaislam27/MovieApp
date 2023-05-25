package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.movieapp.adapter.ItemRecyclerAdapter;
import com.example.movieapp.model.api.GetData;
import com.example.movieapp.api.MovieFetchedListener;
import com.example.movieapp.model.CategoryItem;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private EditText editTextSearch;
    private ImageView imageViewSearch;
    private RecyclerView recyclerView;

    private String QUERY_URL = "https://api.themoviedb.org/3/search/movie?api_key=fd4d797928f14c7441729c83d6f83a27&query=";

    private boolean isSearching = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        editTextSearch = findViewById(R.id.editTextSearch);
        imageViewSearch = findViewById(R.id.imageViewSearch);
        recyclerView =findViewById(R.id.recyclerView);

        imageViewSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = String.valueOf(editTextSearch.getText());
                if(key.isEmpty()) return;

                if(isSearching) return;

                String url = QUERY_URL+key;
                performSearch(url);


            }
        });

    }

    private void performSearch(String url){
        // TOP RATED
        new GetData(url, new MovieFetchedListener() {
            @Override
            public void onMovieFetched(List<CategoryItem> items) {
                ItemRecyclerAdapter adapter = new ItemRecyclerAdapter(SearchActivity.this,items);
                recyclerView.setAdapter(adapter);
                isSearching = false;

            }
        }).execute();
    }

}