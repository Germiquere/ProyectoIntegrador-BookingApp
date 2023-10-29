package com.dh.bmn.services.impl;

import com.dh.bmn.exceptions.RequestValidationException;
import com.dh.bmn.repositories.IS3Repository;
import com.dh.bmn.services.IS3Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

@Service
public class S3Service implements IS3Service {

    private static final Logger log = LoggerFactory.getLogger(S3Service.class);

    private IS3Repository s3Repository;

    @Autowired
    public S3Service(IS3Repository s3Repository) {
        this.s3Repository = s3Repository;
    }

    @Override
    public void deleteFile(String bucketName, String fileKey) {
        s3Repository.deleteFile(bucketName, fileKey);
    }

    @Override
    public String uploadFile(String bucketName, String filePath, MultipartFile file) {
        File fileObj = convertMultiPartFileToFile(file);
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        return s3Repository.uploadFile(bucketName, filePath + fileName, fileObj);
    }

    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            log.error("Error converting multipartFile to file", e);
        }
        return convertedFile;
    }

    public void validarUrlNula(URL url){
        if(url == null){
            throw new RequestValidationException("La URL no puede ser nula o estar vacia", HttpStatus.BAD_REQUEST.value());
        }
    }

    public void validarKey(String key){
        if(key == null || key.isBlank()){
            throw new RequestValidationException("La key no puede ser nula o estar vacia", HttpStatus.BAD_REQUEST.value());
        }
    }

}
