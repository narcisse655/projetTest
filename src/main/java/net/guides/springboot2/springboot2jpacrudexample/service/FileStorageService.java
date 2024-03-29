package net.guides.springboot2.springboot2jpacrudexample.service;

import net.guides.springboot2.springboot2jpacrudexample.model.Materiel;
import net.guides.springboot2.springboot2jpacrudexample.repository.MaterielRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import net.guides.springboot2.springboot2jpacrudexample.exception.FileStorageException;
import net.guides.springboot2.springboot2jpacrudexample.exception.MyFileNotFoundException;
import net.guides.springboot2.springboot2jpacrudexample.property.FileStorageProperties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    @Autowired
    private MaterielRepository materielRepository;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }


    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }

    /*public void deleteFile(String fileName){
        String filePath = this.fileStorageLocation.toString();
        File fileFolder = new File(filePath);
        if (fileFolder != null){
            for (File file: fileFolder.listFiles()){
                if (!file.isDirectory()){
                    if (file.getName().equals(fileName)){
                        file.delete();
                    }
                }
            }
        }
    }*/

    public void deleteFile(String fileName){
        String filePath = this.fileStorageLocation.toString();
        File fileFolder = new File(filePath);
        if (fileFolder != null){
            for (File file: fileFolder.listFiles()){
                if (!file.isDirectory()){
                    if (file.getName().equals(fileName)){
                        if (getListMaterielsByFileName(fileName).size() <= 1){
                            file.delete();
                        }
                    }
                }
            }
        }
    }



    public List<Materiel> getListMaterielsByFileName(String fileName){
        List<Materiel> materiels = new ArrayList<>();
        materiels = materielRepository.findByFileName(fileName);
        return materiels;
    }

    //Unused
    public ResponseEntity<List<String>> getFiles(){
        List<String> files = new ArrayList<>();
        String filePath = this.fileStorageLocation.toString();
        File fileFolder = new File(filePath);
        if (fileFolder != null){
            for(final File file: fileFolder.listFiles()){
                if (!file.isDirectory()){
                    String encoderBase64 = null;
                    try{
                        String extension =  StringUtils.getFilenameExtension(file.getName());
                        FileInputStream fileInputStream = new FileInputStream(file);
                        byte[] bytes = new byte[(int)file.length()];
                        fileInputStream.read(bytes);
                        encoderBase64 = Base64.getEncoder().encodeToString(bytes);
                        files.add("data:image/"+extension+";base64,"+encoderBase64);
                        fileInputStream.close();
                    }catch(Exception e){

                    }

                }
            }
        }
        return new ResponseEntity<List<String>>(files, HttpStatus.OK);
    }

    
}
