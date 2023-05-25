package com.example.movieapp.model;

import android.os.Parcelable;

import java.io.Serializable;

public class CategoryItem implements Serializable
{
    Integer id;
    String movieName;
    String imageUrl;
    private String details,releaseDate;
    private double rating;
   // private double vote_average;

    public CategoryItem(Integer id, String movieName, String imageUrl, String details,String releaseDate) {
        this.id = id;
        this.movieName = movieName;
        this.imageUrl = imageUrl;
        this.details = details;
        this.releaseDate = releaseDate;
        rating = 0;
       // vote_average=0;
    }


    public CategoryItem(Integer id, String movieName, String imageUrl, String details,String releaseDate, double rating) {
        this.id = id;
        this.movieName = movieName;
        this.imageUrl = "https://image.tmdb.org/t/p/w500"+imageUrl; // vuleu change korbi na
        this.details = details;
        this.releaseDate = releaseDate;
        this.rating = rating;
       // this.vote_average=vote_average;
    }



    public double getRating() {
        return rating;
    }

    public float getActualRating(){
        return (float) rating/2f;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }
}
