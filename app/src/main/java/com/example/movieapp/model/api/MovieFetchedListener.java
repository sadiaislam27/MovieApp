package com.example.movieapp.api;

import com.example.movieapp.model.CategoryItem;

import java.util.List;

public interface MovieFetchedListener {

    void onMovieFetched(List<CategoryItem> items);

}
