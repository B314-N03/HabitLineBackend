package com.backend.hl.dto;

public class WeatherDTO {
    private String region;
    private String lat;
    private String lon;

    public WeatherDTO(String region, String lat, String lon) {
        this.region = region;
        this.lat = lat;
        this.lon = lon;
    }

    public String getRegion() {
        return region;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }
}
