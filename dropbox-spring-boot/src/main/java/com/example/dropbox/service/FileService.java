package com.example.dropbox.service;

import com.example.dropbox.entity.FileEntity;
import com.example.dropbox.model.FileMetaData;
import com.example.dropbox.model.FileResponse;
import com.example.dropbox.model.UpdateFileRequest;
import com.example.dropbox.repository.FileEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class FileService {

    @Autowired
    private FileEntityRepository fileEntityRepository;

    public FileResponse saveFile(MultipartFile multipartFile, String fileName) throws IOException {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileName(fileName);
        fileEntity.setFileData(multipartFile.getBytes());
        fileEntity.setCreatedAt(new Date()); // Set the timestamp
        fileEntity.setFileSize(multipartFile.getSize());

        fileEntityRepository.save(fileEntity);
        return new FileResponse(
                fileEntity.getId(),
                fileEntity.getFileName(),
                fileEntity.getCreatedAt(),
                fileEntity.getFileSize(),
                200
                );
    }

    public FileEntity getFileById(Long fileId) throws FileNotFoundException {
        Optional<FileEntity> fileDataOptional = fileEntityRepository.findById(fileId);

        if (fileDataOptional.isPresent()) {
            return fileDataOptional.get();
        } else {
            // Handle the case where the file with the given fileId is not found
            throw new FileNotFoundException("File not found with fileId: " + fileId);
        }
    }

    public boolean deleteFileById(Long fileId) {
        // Check if the file with the given ID exists
        if (fileEntityRepository.existsById(fileId)) {
            // If it exists, delete it
            fileEntityRepository.deleteById(fileId);
            return true; // File deleted successfully
        } else {
            // If it doesn't exist, return false to indicate file not found
            return false;
        }
    }

    public List<FileMetaData> getFiles() {

        return fileEntityRepository.findAllWithoutFileData();
    }

//    public boolean updateFile(Long fileId, UpdateFileRequest updateRequest) {
//        Optional<FileEntity> optionalFileEntity = fileEntityRepository.findById(fileId);
//
//        if (optionalFileEntity.isPresent()) {
//            FileEntity existingFileEntity = optionalFileEntity.get();
//
//            // Update file metadata if provided in the request
//            if (updateRequest.getFileName() != null) {
//                existingFileEntity.setFileName(updateRequest.getFileName());
//            }
//
//            // Update file content if provided in the request
//            if (updateRequest.getFileData() != null) {
//                existingFileEntity.setFileData(updateRequest.getFileData());
//                existingFileEntity.setFileSize(updateRequest.getFileData().length);
//            }
//
//            // Save the updated file entity
//            fileEntityRepository.save(existingFileEntity);
//
//            return true; // File updated successfully
//        } else {
//            return false; // File with fileId not found
//        }
//    }
}

