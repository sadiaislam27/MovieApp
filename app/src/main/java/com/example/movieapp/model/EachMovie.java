package com.example.movieapp.model;

public class EachMovie {
    private String releaseDate , name,imgUrl;
    private double ratings;

    public EachMovie() {
    }

    public EachMovie(String releaseDate, String name, String imgUrl, double ratings) {
        this.releaseDate = releaseDate;
        this.name = name;
        this.imgUrl = imgUrl;
        this.ratings = ratings;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRatings() {
        return ratings;
    }

    public void setRatings(double ratings) {
        this.ratings = ratings;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
