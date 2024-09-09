package com.example.dreamshops.service.image;

import com.example.dreamshops.dto.ImageDto;
import com.example.dreamshops.exceptions.EntityNotFoundException;
import com.example.dreamshops.model.Image;
import com.example.dreamshops.model.Product;
import com.example.dreamshops.repository.ImageRepo;
import com.example.dreamshops.service.product.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService implements IImageService{

    private final ImageRepo imageRepo;
    private final ProductService productService;

    public ImageService(ImageRepo imageRepo, ProductService productService) {
        this.imageRepo = imageRepo;
        this.productService = productService;
    }

    @Override
    public Image getImageById(Long id) {
        return imageRepo.findById(id).orElseThrow(()->
                new EntityNotFoundException("Image with id: " + id + " not found"));
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepo.findById(id).ifPresentOrElse(imageRepo::delete, ()->
                new EntityNotFoundException("Image with id: " + id + " not found"));
    }

    @Override
    public List<ImageDto> saveImage(List<MultipartFile> files, Long productId) {
        Product product = productService.getProduct(productId);
        List<ImageDto> imageDtos = new ArrayList<>();

        for(MultipartFile file : files){
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                imageRepo.save(image);
                String downloadUrl = "/api/v1/images/download/" + image.getId();
                image.setDownloadUrl(downloadUrl);
                imageDtos.add(ImageDto.toDto(image));

            } catch (IOException | SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return imageDtos;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image = getImageById(imageId);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepo.save(image);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
