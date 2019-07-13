package net.guides.springboot2.springboot2jpacrudexample.repository;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import net.guides.springboot2.springboot2jpacrudexample.exception.FileStorageException;
import net.guides.springboot2.springboot2jpacrudexample.exception.MyFileNotFoundException;
import net.guides.springboot2.springboot2jpacrudexample.exception.ResourceNotFoundException;
import net.guides.springboot2.springboot2jpacrudexample.model.DBFile;
import net.guides.springboot2.springboot2jpacrudexample.model.Materiel;

@Service
public class DBFileStorageService {

    @Autowired
    private DBFileRepository dbFileRepository;
    @Autowired
    private MaterielRepository materielRepository;

    public DBFile storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            DBFile dbFile = new DBFile(fileName, file.getContentType(), file.getBytes());

            return dbFileRepository.save(dbFile);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }


    public DBFile getFile(String fileId) {
        return dbFileRepository.findById(fileId)
                .orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));
    }

    /**Updated 
     * 
     * Call the imageService to save the image passing the file as an argument.

    The service basically copies the image content to a byte array, and finally 
    you assign this byte array to your entity. 
    */
    @Transactional
    public String saveImageFile(int materielId, MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            try {

                if(fileName.contains("..")) {
                    throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
                }

                Materiel materiel  = materielRepository.findById(materielId)
                .orElseThrow(() -> new ResourceNotFoundException("Materiel not found for "+
                "this id :: " + materielId));
        
                Byte[] byteObjects = new Byte[file.getBytes().length];

                int i = 0;
			    for(byte b : file.getBytes()){
				    byteObjects[i++] =  b;
                }
            
			    materiel.setImage(byteObjects);
                materielRepository.save(materiel);

                } catch (IOException ex) {
                    throw new FileStorageException("Could not store file "
                    + fileName + ". Please try again!", ex);
                }

        } catch (ResourceNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
        return fileName;

    }

    public void renderImageFromDB(int id, HttpServletResponse response)
            throws IOException, ResourceNotFoundException {

        Materiel materiel  = materielRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Materiel not found for "+
        "this id :: " + id));

		if(materiel != null){


			byte[] byteArray = new byte[materiel.getImage().length];
			int i= 0;

			for(Byte wrappedByte : materiel.getImage() ){
				byteArray[i++] = wrappedByte;
            }
            
			response.setContentType("image/jpeg");
			InputStream is = new ByteArrayInputStream(byteArray);
			IOUtils.copy(is,response.getOutputStream());

		}

	}
}