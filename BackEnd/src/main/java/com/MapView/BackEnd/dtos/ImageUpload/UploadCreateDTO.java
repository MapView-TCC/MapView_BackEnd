package com.MapView.BackEnd.dtos.ImageUpload;

import com.MapView.BackEnd.enums.EnumModelEquipment;
import org.springframework.web.multipart.MultipartFile;

public record UploadCreateDTO(MultipartFile file, EnumModelEquipment equipment) {
}
