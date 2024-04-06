package edu.ntnu.idatt2105.SpringbootBackend.model;

import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "image")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String filename;

    @Column(nullable = false)
    private String fileType;

    @Column(nullable = false)
    private long size;

    @Lob
    @Column(nullable = false, columnDefinition = "LONGBLOB")
    private byte[] data;

    public void setFileContent(byte[] bs) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setFileContent'");
    }
    
}
