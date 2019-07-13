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

@RestController @CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class ContratController {
    @Autowired
    private ContratRepository contratRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AgenceRepository agenceRepository;

    @GetMapping("/contrats")
    public List<Contrat> getAllContrats() {
        return contratRepository.findAll();
    }

    @GetMapping("/contrats/{id}")
    public ResponseEntity<Contrat> getContratById(@PathVariable(value = "id") Integer numContrat)
        throws ResourceNotFoundException {
        Contrat contrat = contratRepository.findById(numContrat)
          .orElseThrow(() -> new ResourceNotFoundException("Contrat not found for this id :: " + numContrat));
        return ResponseEntity.ok().body(contrat);
    }

    @PostMapping("/contrats/clients/{clientId}/agences/{agenceId}")
    public Contrat createContrat(@PathVariable(value = "clientId") int numClient,
        @PathVariable(value = "agenceId") Integer numAgence,
        @Valid @RequestBody Contrat contrat) throws ResourceNotFoundException {
            Client client = clientRepository.findById(numClient)
            .orElseThrow(() -> new ResourceNotFoundException("Client not found for this id :: " + numClient));
            Agence agence = agenceRepository.findById(numAgence)
            .orElseThrow(() -> new ResourceNotFoundException("Agence not found for this id :: " + numAgence));
            contrat.setNumClient(client);
            contrat.setNumAgence(agence);
        return contratRepository.save(contrat);
    }

    @PutMapping("/contrats/{id}")
    public ResponseEntity<Contrat> updateContrat(@PathVariable(value = "id") Integer numContrat,
         @Valid @RequestBody Contrat contratDetails) throws ResourceNotFoundException {
        Contrat contrat = contratRepository.findById(numContrat)
        .orElseThrow(() -> new ResourceNotFoundException("Contrat not found for this id :: " + numContrat));

        contrat.setDateContrat(contratDetails.getDateContrat());
        contrat.setDureeContrat(contratDetails.getDureeContrat());
        contrat.setMontant(contratDetails.getMontant());
        contrat.setNumAgence(contratDetails.getNumAgence());
        contrat.setNumClient(contratDetails.getNumClient());
        final Contrat updatedContrat = contratRepository.save(contrat);
        return ResponseEntity.ok(updatedContrat);
    }

    @DeleteMapping("/contrats/{id}")
    public Map<String, Boolean> deleteContrat(@PathVariable(value = "id") Integer numContrat)
         throws ResourceNotFoundException {
         Contrat contrat = contratRepository.findById(numContrat)
        .orElseThrow(() -> new ResourceNotFoundException("Contrat not found for this id :: " + numContrat));

        contratRepository.delete(contrat);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}