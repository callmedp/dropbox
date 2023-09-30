package com.example.dropbox.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class FileResponse {

    private Long id;
    private String fileName;
    private Date createdAt;
    private Long fileSize;
    private Integer status;
}
