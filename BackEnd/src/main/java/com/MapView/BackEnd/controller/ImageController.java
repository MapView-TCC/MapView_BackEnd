package com.MapView.BackEnd.controller;

import com.MapView.BackEnd.enums.EnumModelEquipment;
import com.MapView.BackEnd.serviceImp.EquipmentServiceImp;
import com.MapView.BackEnd.serviceImp.ImageServiceImp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

@RestController
@RequestMapping("/api/v1/image")
@Tag(name = "Image", description = "Operations related to image management")
public class ImageController {

    private final ImageServiceImp imageServiceImp;

    public ImageController(ImageServiceImp imageServiceImp) {
        this.imageServiceImp = imageServiceImp;
    }

    @Operation(summary = "Upload an image for equipment", description = "Upload an image file and associate it with an equipment.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Image successfully uploaded"),
            @ApiResponse(responseCode = "400", description = "Invalid file or equipment ID")
    })
    @PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Transactional
    public ResponseEntity<String> uploadImage(
            @Parameter(description = "Image upload data", required = true)
            @RequestParam("file") MultipartFile file, @RequestParam EnumModelEquipment type) {
        return imageServiceImp.uploadImageEquipment(file,type);
    }

}
