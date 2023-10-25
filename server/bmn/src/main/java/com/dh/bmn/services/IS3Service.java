package com.dh.bmn.services;

import org.springframework.web.multipart.MultipartFile;


public interface IS3Service {
    String uploadFile(String bucketName, String filePath, MultipartFile file);
    void deleteFile (String bucketName, String fileKey);

}
