package net.guides.springboot2.springboot2jpacrudexample.controller;

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
@RequestMapping("/api/v1")
public class AgenceController {
    @Autowired
    private AgenceRepository agenceRepository;

    @GetMapping("/agences")
    public List<Agence> getAllAgences() {
        return agenceRepository.findAll();
    }

    @GetMapping("/agences/{id}")
    public ResponseEntity<Agence> getAgenceById(@PathVariable(value = "id") Integer numAgence)
        throws ResourceNotFoundException {
        Agence agence = agenceRepository.findById(numAgence)
          .orElseThrow(() -> new ResourceNotFoundException("Agence not found for this id :: " + numAgence));
        return ResponseEntity.ok().body(agence);
    }
    
    @PostMapping("/agences")
    public Agence createAgence(@Valid @RequestBody Agence agence) {
        return agenceRepository.save(agence);
    }

    @PutMapping("/agences/{id}")
    public ResponseEntity<Agence> updateAgence(@PathVariable(value = "id") Integer numAgence,
         @Valid @RequestBody Agence agenceDetails) throws ResourceNotFoundException {
        Agence agence = agenceRepository.findById(numAgence)
        .orElseThrow(() -> new ResourceNotFoundException("Agence not found for this id :: " + numAgence));

        agence.setNomAgence(agenceDetails.getNomAgence());
        final Agence updatedAgence = agenceRepository.save(agence);
        return ResponseEntity.ok(updatedAgence);
    }

    @DeleteMapping("/agences/{id}")
    public Map<String, Boolean> deleteAgence(@PathVariable(value = "id") Integer numAgence)
         throws ResourceNotFoundException {
         Agence agence = agenceRepository.findById(numAgence)
        .orElseThrow(() -> new ResourceNotFoundException("Agence not found for this id :: " + numAgence));

        agenceRepository.delete(agence);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}