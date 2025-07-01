package com.umgc.application.location;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Location")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping
    public List<Location> findAll() {
        return locationService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Location> findById(@PathVariable Long id) {
        return locationService.findById(id);
    }

    // create a location
    @ResponseStatus(HttpStatus.CREATED) // 201
    @PostMapping
    public Location create(@RequestBody Location location) {
        return locationService.save(location);
    }

    // update a location
    @PutMapping
    public Location update(@RequestBody Location location) {
        return locationService.save(location);
    }

    // delete a location
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
    	locationService.deleteById(id);
    }


}

