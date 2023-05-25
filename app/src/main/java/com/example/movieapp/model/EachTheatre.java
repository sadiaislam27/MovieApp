package com.example.movieapp.model;

public class EachTheatre {
    private String Location, Name;
    private double Ratings;

    public EachTheatre(String location, String name, double ratings) {
        Location = location;
        Name = name;
        Ratings = ratings;
    }

    public EachTheatre() {
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getRatings() {
        return Ratings;
    }

    public void setRatings(double ratings) {
        Ratings = ratings;
    }
}

