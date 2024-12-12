package com.MapView.BackEnd.entities;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties(prefix = "file")
@Getter
@Setter
public class FileStorageProperties {
    private String UploadDir;
}
