package net.guides.springboot2.springboot2jpacrudexample.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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

import net.guides.springboot2.springboot2jpacrudexample.exception.MyFileNotFoundException;
import net.guides.springboot2.springboot2jpacrudexample.exception.ResourceNotFoundException;
import net.guides.springboot2.springboot2jpacrudexample.model.DBFile;
import net.guides.springboot2.springboot2jpacrudexample.model.Materiel;
import net.guides.springboot2.springboot2jpacrudexample.payload.UploadFileResponse;
import net.guides.springboot2.springboot2jpacrudexample.repository.DBFileRepository;
import net.guides.springboot2.springboot2jpacrudexample.repository.DBFileStorageService;
import net.guides.springboot2.springboot2jpacrudexample.repository.MaterielRepository;

@RestController
//@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class MaterielController {
    @Autowired
    private MaterielRepository materielRepository;
    @Autowired
    private DBFileRepository dbFileRepository;
    @Autowired
    private DBFileStorageService dbFileStorageService;

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
        materiel.setFileId(materielDetails.getFileId());
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

     /*  @PostMapping("/materiels/{id}/image")
    public UploadFileResponse saveImageMateriel(@PathVariable(value = "id") int id,
            @RequestParam("file") MultipartFile file) {

                DBFile dbFile = dbFileStorageService.storeFile(id, file);

                String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/api/v1/materiels/")
                        .path(String.valueOf(id))
                        .path("/image-materiel")
                        //.path(dbFile.getId())
                        .toUriString();
        
                return new UploadFileResponse(dbFile.getFileName(), fileDownloadUri,
                        file.getContentType(), file.getSize());
        
    } */

    /* @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        DBFile dbFile = dbFileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(dbFile.getId())
                .toUriString();

        return new UploadFileResponse(dbFile.getFileName(), fileDownloadUri,
                file.getContentType(), file.getSize());
    } */

    @PostMapping("/materiels/uploadFile")
    public DBFile uploadFile(@RequestParam("file") MultipartFile file) {
        DBFile dbFile = dbFileStorageService.storeFile(file);
        return dbFile;
    }


    
   /*  @GetMapping("/materiels/{id}/imagemateriel")
    public void downloadFile(@PathVariable(value = "id") int id, 
    HttpServletResponse response){
        // Load file from database
        dbFileStorageService.renderImageFromDB(id, response);
    }
 */
   @GetMapping("/materiels/{id}/imagemateriel")
   public ResponseEntity<Resource> downloadFile(@PathVariable(value="id") int id) {
        // Load file from database
        DBFile dbFile = dbFileStorageService.getFile(id);
        return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(dbFile.getFileType()))
        .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + dbFile.getFileName() + "\"")
        .body(new ByteArrayResource(dbFile.getData()));
    }

    @GetMapping("/materiels/{id}/imagedetails")
    public DBFile getImageDetails(@PathVariable(value="id") int id){
        Materiel materiel = materielRepository.findById(id).get();
        DBFile dbf = dbFileRepository.findById(materiel.getFileId()).get();
        return dbf;
    }
}