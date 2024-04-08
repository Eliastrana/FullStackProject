package edu.ntnu.idatt2105.SpringbootBackend.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ImageTest {

    private Image image;

    @BeforeEach
    public void setup() {
        image = new Image();
    }

    @Test
    public void testId() {
        UUID id = UUID.randomUUID();
        image.setId(id);
        assertEquals(id, image.getId());
    }

    @Test
    public void testFilename() {
        String filename = "testImage.jpg";
        image.setFilename(filename);
        assertEquals(filename, image.getFilename());
    }

    @Test
    public void testFileType() {
        String fileType = "image/jpeg";
        image.setFileType(fileType);
        assertEquals(fileType, image.getFileType());
    }

    @Test
    public void testSize() {
        long size = 1024L;
        image.setSize(size);
        assertEquals(size, image.getSize());
    }

    @Test
    public void testData() {
        byte[] data = new byte[]{1, 2, 3, 4};
        image.setData(data);
        assertNotNull(image.getData());
        assertEquals(data.length, image.getData().length);
    }

}