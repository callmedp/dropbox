package com.example.dropbox.model;

import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateFileRequest {

    private String fileName;

    @Lob
    private byte[] fileData;

}
