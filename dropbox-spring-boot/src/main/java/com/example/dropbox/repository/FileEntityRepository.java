package com.example.dropbox.repository;

import com.example.dropbox.entity.FileEntity;
import com.example.dropbox.model.FileMetaData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileEntityRepository extends JpaRepository<FileEntity, Long> {

    @Query("SELECT new com.example.dropbox.model.FileMetaData(f.id, f.fileName) FROM FileEntity f")
    List<FileMetaData> findAllWithoutFileData();
}
