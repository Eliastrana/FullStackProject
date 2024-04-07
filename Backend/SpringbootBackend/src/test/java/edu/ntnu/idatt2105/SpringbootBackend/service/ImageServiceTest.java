package edu.ntnu.idatt2105.SpringbootBackend.service;

import edu.ntnu.idatt2105.SpringbootBackend.exception.FileTooLargeException;
import edu.ntnu.idatt2105.SpringbootBackend.model.Image;
import edu.ntnu.idatt2105.SpringbootBackend.repository.ImageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class ImageServiceTest {

    @Mock
    private ImageRepository imageRepository;

    @InjectMocks
    private ImageService imageService;

@Test
void storeImage_Success() throws IOException {
    String originalFilename = "testImage.png";
    String contentType = "image/png";
    byte[] content = "test image content".getBytes();
    MockMultipartFile file = new MockMultipartFile("image", originalFilename, contentType, content);

    Image savedImage = Image.builder()
            .filename(originalFilename)
            .fileType(contentType)
            .size((long) content.length)
            .data(content)
            .build();
    when(imageRepository.save(any(Image.class))).thenReturn(savedImage);

    Image result = imageService.storeImage(file);

    assertNotNull(result);
    assertEquals(originalFilename, result.getFilename());
    assertEquals(contentType, result.getFileType());
    assertArrayEquals(content, result.getData());
    verify(imageRepository, times(1)).save(any(Image.class));
}

@Test
void storeImage_FileTooLarge_ThrowsException() {
    MockMultipartFile file = new MockMultipartFile("image", "bigImage.png", "image/png", new byte[11 * 1024 * 1024]); // 11 MB

    assertThrows(FileTooLargeException.class, () -> imageService.storeImage(file));
}

@Test
void getImage_Found() {
    UUID id = UUID.randomUUID();
    Image image = new Image();
    image.setId(id);
    when(imageRepository.findById(id)).thenReturn(Optional.of(image));

    Optional<Image> result = imageService.getImage(id);

    assertTrue(result.isPresent());
    assertEquals(id, result.get().getId());
}
@Test
void getImage_NotFound() {
    UUID id = UUID.randomUUID();
    when(imageRepository.findById(id)).thenReturn(Optional.empty());

    Optional<Image> result = imageService.getImage(id);

    assertFalse(result.isPresent());
}

}

