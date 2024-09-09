package com.example.dreamshops.dto;

import com.example.dreamshops.model.Image;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImageDto {
    private Long id;
    private String name;
    private String downloadUrl;

    public static ImageDto toDto(Image entity){
        return new ImageDto(entity.getId(), entity.getFileName(), entity.getDownloadUrl());
    }
}
