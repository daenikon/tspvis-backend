package com.example.tspvis_backend.service;

import com.example.tspvis_backend.entity.Coordinate;
import org.springframework.stereotype.Service;

@Service
public class DistanceService {

    private static final double R = 6371; // Radius of the Earth in km

    public double distance(Coordinate a, Coordinate b) {
        double lat1 = Math.toRadians(a.getLat());
        double lon1 = Math.toRadians(a.getLng());
        double lat2 = Math.toRadians(b.getLat());
        double lon2 = Math.toRadians(b.getLng());

        double dlat = lat2 - lat1;
        double dlon = lon2 - lon1;

        double sinDlat = Math.sin(dlat / 2);
        double sinDlon = Math.sin(dlon / 2);
        double aVal = sinDlat * sinDlat + Math.cos(lat1) * Math.cos(lat2) * sinDlon * sinDlon;
        double c = 2 * Math.atan2(Math.sqrt(aVal), Math.sqrt(1 - aVal));

        return R * c; // Distance in km
    }
}

