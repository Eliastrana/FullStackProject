package edu.ntnu.idatt2105.SpringbootBackend.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import edu.ntnu.idatt2105.SpringbootBackend.model.Image;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ImageRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testImagePersistence() {
        // Create a new Image instance
        Image image = new Image();
        image.setFilename("testImage.jpg");
        image.setFileType("image/jpeg");
        image.setSize(1024);
        image.setData(new byte[]{1, 2, 3, 4}); // Example data

        // Persist the image
        image = entityManager.persistAndFlush(image);

        // Retrieve the image
        Image foundImage = entityManager.find(Image.class, image.getId());

        // Assert the retrieved image's properties
        assertNotNull(foundImage);
        assertEquals(image.getFilename(), foundImage.getFilename());
        assertEquals(image.getFileType(), foundImage.getFileType());
        assertEquals(image.getSize(), foundImage.getSize());
        assertArrayEquals(image.getData(), foundImage.getData());
    }
}
