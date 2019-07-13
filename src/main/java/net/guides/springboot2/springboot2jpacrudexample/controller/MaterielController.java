package net.guides.springboot2.springboot2jpacrudexample.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.tomcat.util.http.fileupload.IOUtils;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import net.guides.springboot2.springboot2jpacrudexample.exception.ResourceNotFoundException;
import net.guides.springboot2.springboot2jpacrudexample.model.*;
import net.guides.springboot2.springboot2jpacrudexample.payload.UploadFileResponse;
import net.guides.springboot2.springboot2jpacrudexample.repository.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class MaterielController {
    @Autowired
    private MaterielRepository materielRepository;
    @Autowired
    private DBFileStorageService DBFileStorageService;

    @GetMapping("/materiels")
    public List<Materiel> getAllMateriels() {
        return materielRepository.findAll();
    }

    @GetMapping("/materiels/{id}")
    public ResponseEntity<Materiel> getMaterielById(@PathVariable(value = "id") Integer refMateriel)
            throws ResourceNotFoundException {
        Materiel materiel = materielRepository.findById(refMateriel)
                .orElseThrow(() -> new ResourceNotFoundException("Materiel not found for this id :: " + refMateriel));

        return ResponseEntity.ok().body(materiel);
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
        materiel.setImage(materielDetails.getImage());
        materiel.setPuMateriel(materielDetails.getPuMateriel());
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
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @PostMapping("/materiels/{id}/image")
    public UploadFileResponse saveImageMateriel(@PathVariable(value = "id") int id,
            @RequestParam("file") MultipartFile file) {

        String fileName = DBFileStorageService.saveImageFile(id, file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
                .path(fileName).toUriString();

        return new UploadFileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
    }

    @GetMapping("materiels/{id}/imagemateriel")
    public void renderImageMateriel(@PathVariable(value = "id") int id,
     HttpServletResponse response) throws IOException, ResourceNotFoundException {

        DBFileStorageService.renderImageFromDB(id, response);        

	}
}