package com.example.tspvis_backend.service;

import com.example.tspvis_backend.entity.Coordinate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NearestNeighborService {

    private final DistanceService distanceService;

    public NearestNeighborService(DistanceService distanceService) {
        this.distanceService = distanceService;
    }

    public int[][] getNearestNeighbor(List<Coordinate> coordinates) {
        if (coordinates.size() <= 1) {
            return new int[0][0];
        }

        Coordinate start = coordinates.get(0);
        List<Coordinate> remainingPoints = new ArrayList<>(coordinates.subList(1, coordinates.size()));
        List<Coordinate> path = new ArrayList<>();
        path.add(start);

        Coordinate currentPoint = start;

        while (!remainingPoints.isEmpty()) {
            Coordinate closestPoint = findClosestPoint(currentPoint, remainingPoints);
            path.add(closestPoint);
            remainingPoints.remove(closestPoint);
            currentPoint = closestPoint;
        }

        return parseToIndexes(coordinates, path);
    }

    private Coordinate findClosestPoint(Coordinate currentPoint, List<Coordinate> remainingPoints) {
        Coordinate closestPoint = null;
        double minDistance = Double.MAX_VALUE;

        for (Coordinate point : remainingPoints) {
            double distance = distanceService.distance(currentPoint, point);
            if (distance < minDistance) {
                minDistance = distance;
                closestPoint = point;
            }
        }

        return closestPoint;
    }

    private int[][] parseToIndexes(List<Coordinate> startPositions, List<Coordinate> path) {
        List<int[]> indexPairs = new ArrayList<>();
        for (int i = 0; i < path.size() - 1; i++) {
            Coordinate fromCoordinate = path.get(i);
            Coordinate toCoordinate = path.get(i + 1);
            int fromDotIndex = startPositions.indexOf(fromCoordinate);
            int toDotIndex = startPositions.indexOf(toCoordinate);
            indexPairs.add(new int[]{fromDotIndex, toDotIndex});
        }
        int lastDotIndex = startPositions.indexOf(path.get(path.size() - 1));
        int firstDotIndex = startPositions.indexOf(path.get(0));
        indexPairs.add(new int[]{lastDotIndex, firstDotIndex});
        return indexPairs.toArray(new int[0][]);
    }
}

