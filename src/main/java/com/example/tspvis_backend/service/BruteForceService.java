package com.example.tspvis_backend.service;

import com.example.tspvis_backend.entity.Coordinate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BruteForceService {

    private final DistanceService distanceService;

    public BruteForceService(DistanceService distanceService) {
        this.distanceService = distanceService;
    }

    public int[][] getBruteForce(List<Coordinate> coordinates) {
        if (coordinates.size() <= 1) {
            return new int[0][0];
        }

        List<List<Coordinate>> permutations = generatePermutations(coordinates.subList(1, coordinates.size()));
        List<Coordinate> optimalPath = null;
        double minDistance = Double.MAX_VALUE;

        for (List<Coordinate> permutation : permutations) {
            List<Coordinate> path = new ArrayList<>();
            path.add(coordinates.get(0));
            path.addAll(permutation);
            double totalDistance = calculateTotalDistance(path);

            if (totalDistance < minDistance) {
                minDistance = totalDistance;
                optimalPath = path;
            }
        }

        return parseToIndexes(coordinates, optimalPath);
    }

    private double calculateTotalDistance(List<Coordinate> path) {
        double totalDistance = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            totalDistance += distanceService.distance(path.get(i), path.get(i + 1));
        }
        return totalDistance;
    }

    private List<List<Coordinate>> generatePermutations(List<Coordinate> coordinates) {
        List<List<Coordinate>> result = new ArrayList<>();
        if (coordinates.size() == 1) {
            result.add(new ArrayList<>(coordinates));
            return result;
        }

        for (int i = 0; i < coordinates.size(); i++) {
            Coordinate current = coordinates.get(i);
            List<Coordinate> remaining = new ArrayList<>(coordinates);
            remaining.remove(i);

            List<List<Coordinate>> remainingPermutations = generatePermutations(remaining);
            for (List<Coordinate> perm : remainingPermutations) {
                perm.add(0, current);
                result.add(perm);
            }
        }
        return result;
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

