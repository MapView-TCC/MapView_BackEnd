package com.MapView.BackEnd.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

public interface StorageService {

    void store(MultipartFile file) throws IOException;
    Path loag(String filename);
    Resource loadAsResource(String filename);
    void init();
}
