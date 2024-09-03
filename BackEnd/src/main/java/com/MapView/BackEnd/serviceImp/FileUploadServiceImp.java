package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.entities.Equipment;
import com.MapView.BackEnd.entities.FileStorageProperties;
import com.MapView.BackEnd.repository.EquipmentRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class FileUploadServiceImp {


    private final Path fileStorageLocation;
    private final EquipmentRepository equipmentRepository;


    public FileUploadServiceImp(FileStorageProperties fileStorageProperties, EquipmentRepository equipmentRepository) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
        this.equipmentRepository = equipmentRepository;
    }

    public ResponseEntity<String> uploadFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            Path targetLocation = fileStorageLocation.resolve(fileName);

            file.transferTo(targetLocation);

            equipament_image(targetLocation);
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/files/download/")
                    .path(fileName)
                    .toUriString();

            return ResponseEntity.ok("File uploaded successfully. Download link: " + fileDownloadUri);
        } catch (IOException ex) {
            ex.printStackTrace();
            return ResponseEntity.badRequest().body("File upload failed.");
        }
    }

    public void equipament_image(Path targetLocation){
        String targetLocatioString = targetLocation.toString();

        List<Equipment> allEquipments   =  equipmentRepository.findAll();

        for (Equipment equipment : allEquipments) {
            equipment.setImage(targetLocatioString);
        }

        equipmentRepository.saveAll(allEquipments);
    }

    public ResponseEntity<Resource> downloadFile(@PathVariable String filename,
                                                 HttpServletRequest request) throws IOException {
        Path filePath = fileStorageLocation.resolve(filename).normalize();
        try {
            Resource resource = new UrlResource(filePath.toUri());

            String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (MalformedURLException ex) {
            return ResponseEntity.badRequest().body(null);
        }

    }
    public ResponseEntity<List<String>> listFiles() throws IOException {
        List<String> fileNames = Files.list(fileStorageLocation)
                .map(Path::getFileName)
                .map(Path::toString)
                .collect(Collectors.toList());
        return ResponseEntity.ok(fileNames);
    }
}
