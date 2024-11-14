package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.entities.Equipment;
import com.MapView.BackEnd.entities.FileStorageProperties;
import com.MapView.BackEnd.entities.Image;
import com.MapView.BackEnd.enums.EnumModelEquipment;
import com.MapView.BackEnd.repository.*;
import jakarta.persistence.EntityManager;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@Service
public class ImageServiceImp {

    private final EquipmentRepository equipmentRepository ;
    private final Path fileStorageLocation;
    private final ImageRepository imageRepository;

    public ImageServiceImp(EquipmentRepository equipmentRepository, FileStorageProperties fileStorageProperties, ImageRepository imageRepository) {
        this.equipmentRepository = equipmentRepository;
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize(); // pegar o caminho que está no application.properties
        this.imageRepository = imageRepository;
    }

    public ResponseEntity<String> uploadImageEquipment (MultipartFile file, EnumModelEquipment equipType){
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            Path targetLocation = fileStorageLocation.resolve(fileName);
            file.transferTo(targetLocation);

            equipment_image(targetLocation,equipType);

            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/v1/image/download/")
                    .path(fileName)
                    .toUriString();

            return ResponseEntity.ok("File uploaded successfully. Download link: " + fileDownloadUri);
        } catch (IOException ex) {
            return ResponseEntity.badRequest().body("File upload failed.");
        }

    }

    public void equipment_image(Path targetLocation, EnumModelEquipment equipmentModel){
        String targetLocationString = targetLocation.toString();

        List<Equipment> allEquipments = equipmentRepository.findByModel(equipmentModel);

        Image image = imageRepository.findByModel(equipmentModel);
        if (image == null) {
            image = new Image(targetLocationString, equipmentModel);
            image = imageRepository.save(image);
        } else {
            image.setImage(targetLocationString);
            imageRepository.save(image);
        }

        for (Equipment equipment : allEquipments) {
            equipment.setId_image(image);
        }
        equipmentRepository.saveAll(allEquipments);
    }

}
