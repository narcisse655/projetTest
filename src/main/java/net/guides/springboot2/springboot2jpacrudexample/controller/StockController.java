package net.guides.springboot2.springboot2jpacrudexample.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.guides.springboot2.springboot2jpacrudexample.exception.ResourceNotFoundException;
import net.guides.springboot2.springboot2jpacrudexample.model.Agence;
import net.guides.springboot2.springboot2jpacrudexample.model.Materiel;
import net.guides.springboot2.springboot2jpacrudexample.model.Stock;
import net.guides.springboot2.springboot2jpacrudexample.repository.AgenceRepository;
import net.guides.springboot2.springboot2jpacrudexample.repository.MaterielRepository;
import net.guides.springboot2.springboot2jpacrudexample.repository.StockRepository;

@RestController 
//@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class StockController {
    
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private MaterielRepository materielRepository;
    @Autowired
    private AgenceRepository agenceRepository;

    @GetMapping("/stocks")
    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    @GetMapping("/stocks/{id}")
    public ResponseEntity<List<Stock>> getLocationById(@PathVariable(value = "id") Integer agenceId)
        throws ResourceNotFoundException {
        List<Stock> stocks = stockRepository.findStockById(agenceId);
          //.orElseThrow(() -> new ResourceNotFoundException("Location's contrat not found for this id :: " + numContrat));
          if (stocks.isEmpty()){
              
          }
        return ResponseEntity.ok().body(stocks);
    }

    @PostMapping("/stocks/agences/{agenceId}/materiels/{materiels}")
    public List<Stock> createStock(@PathVariable(value = "agenceId") int agenceId,
        @PathVariable(value = "materiels") List<Materiel> materiels,
        @Valid @RequestBody List<Stock> stocks) throws ResourceNotFoundException {
            List<Stock> items = new ArrayList<>();
            for (Stock s: stocks){
                for (Materiel m: materiels){
                    if (stockRepository.checkIfCoupleIdExist(agenceId, m.getRefMateriel())==null){
                        Agence agence = agenceRepository.findById(agenceId)
                        .orElseThrow(() -> new ResourceNotFoundException("Agence not found " 
                        +"for this id :: " + agenceId));
                        Materiel materiel = materielRepository.findById(m.getRefMateriel())
                        .orElseThrow(() -> new ResourceNotFoundException("Materiel not found "
                        +"for this id :: " + m.getRefMateriel()));
                    
                    s.setAgence(agence);
                    s.setMateriel(materiel);
                    s.getQteDispo();
                    Stock stock = stockRepository.save(s);
                    if (!items.contains(stock)){
                        items.add(stock);
                    }
                    }else{}
                }
            }
           
            return items;      
    }


   /*@PutMapping("/locations/{id}/contrats/{contratId}/materiels/{materielId}")
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
            }*/
    }