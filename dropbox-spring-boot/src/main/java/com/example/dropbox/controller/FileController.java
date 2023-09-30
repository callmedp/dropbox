package com.example.dropbox.controller;

import com.example.dropbox.entity.FileEntity;
import com.example.dropbox.model.FileMetaData;
import com.example.dropbox.model.FileResponse;
import com.example.dropbox.model.UpdateFileRequest;
import com.example.dropbox.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<FileResponse> uploadFile(@RequestPart("file")MultipartFile file,
                                     @RequestParam("filename") String filename
                                    ) {

        try {
            String originalFileName = file.getOriginalFilename();
            FileResponse fileResponse = fileService.saveFile(file, originalFileName);
            fileResponse.setStatus(200);
            return ResponseEntity.ok(fileResponse);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new FileResponse());
        }
    }


    @GetMapping("/{fileId}")
    public ResponseEntity<ByteArrayResource> getFile(@PathVariable Long fileId) throws FileNotFoundException {
        try {
            FileEntity fileData = fileService.getFileById(fileId);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileData.getFileName() + "\"")
                    .body(new ByteArrayResource(fileData.getFileData()));
        }
        catch(Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<String> deleteFile(@PathVariable Long fileId) {
        try {
            // Attempt to delete the file by its fileId
            boolean deleted = fileService.deleteFileById(fileId);

            if (deleted) {
                return ResponseEntity.ok("File deleted successfully");
            } else {
                return ResponseEntity.notFound().build(); // File with fileId not found
            }
        } catch (Exception e) {
            // Handle exceptions, e.g., database errors or other issues
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting file");
        }
    }

    @GetMapping
    public List<FileMetaData> listFiles() {
        // Retrieve a list of file metadata from the database
        return fileService.getFiles();
    }

//    @PutMapping("/{fileId}")
//    public ResponseEntity<String> updateFile(@PathVariable Long fileId, @RequestBody UpdateFileRequest updateRequest) {
//        try {
//            // Perform the update operation using the fileService
//            boolean updated = fileService.updateFile(fileId, updateRequest);
//
//            if (updated) {
//                return ResponseEntity.ok("File updated successfully");
//            } else {
//                return ResponseEntity.notFound().build(); // File with fileId not found
//            }
//        } catch (Exception e) {
//            // Handle exceptions, e.g., database errors or other issues
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating file");
//        }
//    }
}
