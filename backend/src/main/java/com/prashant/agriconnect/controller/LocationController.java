package com.prashant.agriconnect.controller;

import com.prashant.agriconnect.dto.LocationResponse;
import com.prashant.agriconnect.entity.Location;
import com.prashant.agriconnect.repository.LocationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class LocationController {
    private final LocationRepository locations;
    public LocationController(LocationRepository locations) { this.locations = locations; }

    @GetMapping("/locations")
    ResponseEntity<Map<String,Object>> locations() {
        List<LocationResponse> data = locations.findAll().stream().map(this::toResponse).toList();
        return ResponseEntity.ok(Map.of("msg", "Location Fetching success", "data", data));
    }

    @GetMapping("/locMarketplace")
    ResponseEntity<Map<String,Object>> addresses() {
        List<Map<String,Object>> data = locations.findAll().stream().map(l -> Map.<String,Object>of("id", l.getId(), "address", l.getAddress())).toList();
        return ResponseEntity.ok(Map.of("locations", data));
    }

    private LocationResponse toResponse(Location l) {
        var p = l.getProduct();
        return new LocationResponse(l.getId(), l.getLatitude(), l.getLongitude(), l.getAddress(), p.getId(), p.getName(), p.getPrice());
    }
}
