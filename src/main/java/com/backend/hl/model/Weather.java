package com.backend.hl.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Weather {
    private String region;
    private String lat;
    private String lon;

    public Weather() {
    }

    public Weather(String region, String lat, String lon) {
        this.region = region;
        this.lat = lat;
        this.lon = lon;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }
}
