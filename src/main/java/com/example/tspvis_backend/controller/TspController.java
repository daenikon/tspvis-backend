package com.example.tspvis_backend.controller;

import com.example.tspvis_backend.entity.Coordinate;
import com.example.tspvis_backend.service.BruteForceService;
import com.example.tspvis_backend.service.NearestNeighborService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tsp")
public class TspController {

    private final NearestNeighborService nearestNeighborService;
    private final BruteForceService bruteForceService;

    @Autowired
    public TspController(NearestNeighborService nearestNeighborService, BruteForceService bruteForceService) {
        this.nearestNeighborService = nearestNeighborService;
        this.bruteForceService = bruteForceService;
    }

    @PostMapping("/nearest-neighbor")
    public int[][] getNearestNeighbor(@RequestBody List<Coordinate> coordinates) {
        return nearestNeighborService.getNearestNeighbor(coordinates);
    }

    @PostMapping("/brute-force")
    public int[][] getBruteForce(@RequestBody List<Coordinate> coordinates) {
        return bruteForceService.getBruteForce(coordinates);
    }
}

