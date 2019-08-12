package net.guides.springboot2.springboot2jpacrudexample.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.guides.springboot2.springboot2jpacrudexample.exception.ResourceNotFoundException;
import net.guides.springboot2.springboot2jpacrudexample.model.*;
import net.guides.springboot2.springboot2jpacrudexample.repository.*;

@RestController 
//@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class LocationController {
    
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private MaterielRepository materielRepository;
    @Autowired
    private ContratRepository contratRepository;

    @GetMapping("/locations")
    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    @GetMapping("/locations/{id}")
    public ResponseEntity<List<Location>> getLocationById(@PathVariable(value = "id") Integer contratId)
        throws ResourceNotFoundException {
        List<Location> locationList = locationRepository.findLocationById(contratId);
          //.orElseThrow(() -> new ResourceNotFoundException("Location's contrat not found for this id :: " + numContrat));
          if (locationList.isEmpty()){
              
          }
        return ResponseEntity.ok().body(locationList);
    }

    /*@PostMapping("/locations/contrats/{contratId}/materiels/{materielId}")
    public Location createLocation(@PathVariable(value = "contratId") int contratId,
        @PathVariable(value = "materielId") int materielId,
        @Valid @RequestBody Location location) throws ResourceNotFoundException {
            if (locationRepository.checkIfCoupleIdExist(contratId, materielId)==null){
                Contrat contrat = contratRepository.findById(contratId)
                .orElseThrow(() -> new ResourceNotFoundException("Contrat not found for this id :: " + contratId));
                Materiel materiel = materielRepository.findById(materielId)
                .orElseThrow(() -> new ResourceNotFoundException("Materiel not found for this id :: " + materielId));
                location.setContrat(contrat);
                location.setMateriel(materiel);
                location.getQteLiv();
                location = locationRepository.save(location);
            }else{
                location = null;
            }  
            return location;
    }*/

    @PostMapping("/locations/contrats/{contratId}/materiels/{materiels}")
    public List<Location> createLocation(@PathVariable(value = "contratId") int contratId,
        @PathVariable(value = "materiels") List<Materiel> materiels,
        @Valid @RequestBody List<Location> locations) throws ResourceNotFoundException {
            List<Location> items = new ArrayList<>();
            for (Location l: locations){
                for (Materiel m: materiels){
                    if (locationRepository.checkIfCoupleIdExist(contratId, m.getRefMateriel())==null){
                        Contrat contrat = contratRepository.findById(contratId)
                        .orElseThrow(() -> new ResourceNotFoundException("Contrat not found " 
                        +"for this id :: " + contratId));
                        Materiel materiel = materielRepository.findById(m.getRefMateriel())
                        .orElseThrow(() -> new ResourceNotFoundException("Materiel not found "
                        +"for this id :: " + m.getRefMateriel()));
                    
                    l.setContrat(contrat);
                    l.setMateriel(materiel);
                    l.getQteLiv();
                    Location location = locationRepository.save(l);
                    if (!items.contains(location)){
                        items.add(location);
                    }
                    }else{}
                }
            }
           
            return items;      
    }


   @PutMapping("/locations/{id}/contrats/{contratId}/materiels/{materielId}")
    public ResponseEntity<Location> updateLocation(@PathVariable(value = "id") Long locationId,
        @PathVariable(value = "contratId") Integer contratId, 
        @PathVariable(value = "materielId") Integer materielId,
         @Valid @RequestBody Location locationDetails) throws ResourceNotFoundException {
        Location location = locationRepository.findById(locationId)
        .orElseThrow(() -> new ResourceNotFoundException("Location not found"+ 
        " for this id :: " + locationId));
        Contrat contrat = contratRepository.findById(contratId)
        .orElseThrow(() -> new ResourceNotFoundException("Contrat not found " 
        +"for this id :: " + contratId));
        Materiel materiel = materielRepository.findById(materielId)
        .orElseThrow(() -> new ResourceNotFoundException("Materiel not found "
        +"for this id :: " + materielId));

        location.setQteLiv(locationDetails.getQteLiv());
        location.setContrat(contrat);
        location.setMateriel(materiel);
        final Location updatedLocation = locationRepository.save(location);
        return ResponseEntity.ok(updatedLocation);
   }
   

    @DeleteMapping("/locations/{id}")
    public Map<String, Boolean> deleteLocation(@PathVariable(value = "id") Long locationId)
         throws ResourceNotFoundException {
         Location location = locationRepository.findById(locationId)
        .orElseThrow(() -> new ResourceNotFoundException("Contrat not found for this id :: " + locationId));

        locationRepository.delete(location);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }


    @DeleteMapping("/locations/items/{locations}")
    public Map<String, Boolean> deleteListLocation(@PathVariable(value = "locations") List<Location> locations)
         throws ResourceNotFoundException {
            for (Location l: locations){
                Location location = locationRepository.findById(l.getLocationId())
                .orElseThrow(() -> new ResourceNotFoundException("Contrat not found for this id :: " + l.getLocationId()));
                locationRepository.delete(location);
            }
            
                Map<String, Boolean> response = new HashMap<>();
                response.put("deleted", Boolean.TRUE);
                return response;
            }
    }