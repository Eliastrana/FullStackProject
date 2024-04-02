package edu.ntnu.idatt2105.SpringbootBackend.service;

import edu.ntnu.idatt2105.SpringbootBackend.exception.FileTooLargeException;
import edu.ntnu.idatt2105.SpringbootBackend.model.Image;
import edu.ntnu.idatt2105.SpringbootBackend.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImageService {

    private static  final long MAX_FILE_SIZE = 10 * 1024 * 1024;

    @Autowired
    private ImageRepository imageRepository;

    public Image storeImage(MultipartFile file) throws IOException {

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new FileTooLargeException("File size exceeds the limit of 10MB: " + file.getSize() + " bytes");
        }
        String fileName = file.getOriginalFilename();
        Image image = Image.builder()
                .filename(fileName)
                .fileType(file.getContentType())
                .size(file.getSize())
                .data(file.getBytes())
                .build();
        return imageRepository.save(image);
    }

    public Optional<Image> getImage(UUID id) {
        return imageRepository.findById(id);
    }
}
