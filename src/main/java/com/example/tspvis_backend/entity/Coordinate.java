package com.example.tspvis_backend.entity;

public class Coordinate {
    private double lat;
    private double lng;

    public Coordinate() {
    }

    public Coordinate(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    // For debugging
    @Override
    public String toString() {
        return "Coordinate{" +
                "lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}

