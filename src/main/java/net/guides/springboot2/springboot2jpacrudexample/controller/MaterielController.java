package net.guides.springboot2.springboot2jpacrudexample.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.guides.springboot2.springboot2jpacrudexample.exception.ResourceNotFoundException;
import net.guides.springboot2.springboot2jpacrudexample.model.Materiel;
import net.guides.springboot2.springboot2jpacrudexample.repository.MaterielRepository;
import net.guides.springboot2.springboot2jpacrudexample.service.FileStorageService;

@RestController
//@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class MaterielController {
    @Autowired
    private MaterielRepository materielRepository;
    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping("/materiels")
    public List<Materiel> getAllMateriels() {
        return materielRepository.findAll();
    }

    @GetMapping("/materiels/list")
    public Page<Materiel> showPage(@RequestParam(defaultValue = "0") int page){
        return materielRepository.findAll(new PageRequest(page, 5));
    }

    @GetMapping("/materiels/{id}")
    public ResponseEntity<Materiel> getMaterielById(@PathVariable(value = "id") Integer refMateriel)
            throws ResourceNotFoundException {
        Materiel materiel = materielRepository.findById(refMateriel)
                .orElseThrow(() -> new ResourceNotFoundException("Materiel not found for this id :: " + refMateriel));

        return ResponseEntity.ok().body(materiel);
    }

    @GetMapping("/materiels/list/{id}")
    public ResponseEntity<List<Materiel>> getMaterielsById(@PathVariable(value = "id") Integer refMateriel)
            throws ResourceNotFoundException {
        List<Materiel> materiels = new ArrayList<>();
        Materiel materiel = materielRepository.findById(refMateriel)
                .orElseThrow(() -> new ResourceNotFoundException("Materiel not found for this id :: " + refMateriel));
        materiels.add(materiel);
        return ResponseEntity.ok().body(materiels);
    }

    @PostMapping("/materiels")
    public Materiel createMateriel(@Valid @RequestBody Materiel materiel) {
        return materielRepository.save(materiel);
    }

    @PutMapping("/materiels/{id}")
    public ResponseEntity<Materiel> updateMateriel(@PathVariable(value = "id") Integer refMateriel,
            @Valid @RequestBody Materiel materielDetails) throws ResourceNotFoundException {
        Materiel materiel = materielRepository.findById(refMateriel)
                .orElseThrow(() -> new ResourceNotFoundException("Materiel not found for this id :: " + refMateriel));
        materiel.setDesignMateriel(materielDetails.getDesignMateriel());
        materiel.setFileName(materielDetails.getFileName());
        materiel.setPuMateriel(materielDetails.getPuMateriel());
		materiel.setDescription(materielDetails.getDescription());
        final Materiel updatedMateriel = materielRepository.save(materiel);
        return ResponseEntity.ok(updatedMateriel);
    }

    @DeleteMapping("/materiels/{id}")
    public Map<String, Boolean> deleteMateriel(@PathVariable(value = "id") Integer refMateriel)
            throws ResourceNotFoundException {
        Materiel materiel = materielRepository.findById(refMateriel)
                .orElseThrow(() -> new ResourceNotFoundException("Materiel not found for this id :: " + refMateriel));

        materielRepository.delete(materiel);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Materiel deleted", Boolean.TRUE);
        return response;
    }


}