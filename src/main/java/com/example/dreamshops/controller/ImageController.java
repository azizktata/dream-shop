package com.example.dreamshops.controller;

import com.example.dreamshops.dto.ImageDto;
import com.example.dreamshops.model.Image;
import com.example.dreamshops.service.image.ImageService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/images")
public class ImageController {
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long id) throws SQLException {
        Image image = imageService.getImageById(id);
        ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1, (int) image.getImage().length()));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getFileType()))
                .header("Content-disposition", "attachment; filename=" + image.getFileName())
                .body(resource);
    }

    @PostMapping("/upload")
    public List<ImageDto> uploadImage(@RequestParam("files") List<MultipartFile> files, @RequestParam("productId") Long productId) {
        return imageService.saveImage(files, productId);
    }

    @PutMapping("/{id}")
    public ImageDto updateImage(@RequestParam("file") MultipartFile file, @PathVariable Long id){
        return imageService.updateImage(file, id);
    }

    @DeleteMapping("/{id}")
    public void deleteImage(@PathVariable Long id){
        imageService.deleteImageById(id);
    }
}
