package edu.ntnu.idatt2105.SpringbootBackend.model;

import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents an image file within the quiz system. This entity is used to store
 * images associated with quizzes, questions, or any other entities requiring visual representation.
 * The {@code Image} class includes information about the image file, such as its name, type, size,
 * and the actual binary data of the image stored in a byte array.
 * Images are identified by a unique UUID, which facilitates their retrieval and management
 * across the system. This entity allows for a robust handling of multimedia content, enhancing
 * the interactive and visual aspects of quizzes and questions.
 *
 * @author Vegard Johnsen
 * @version 0.1
 * @since 0.1
 */
@Entity
@Table(name = "image")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Image {

    /**
     * Unique identifier for the image. This is automatically generated and used for referencing the image entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * The name of the image file. This includes the extension and is used for identification purposes.
     */
    @Column(nullable = false)
    private String filename;

    /**
     * The MIME type of the image file, indicating the format (e.g., "image/png").
     */
    @Column(nullable = false)
    private String fileType;

    /**
     * The size of the image file in bytes. This is important for managing storage and optimizing performance.
     */
    @Column(nullable = false)
    private long size;

    /**
     * The binary data of the image, stored as a byte array. This allows the image to be stored directly in the database.
     */
    @Lob
    @Column(nullable = false, columnDefinition = "LONGBLOB")
    private byte[] data;
    
}
