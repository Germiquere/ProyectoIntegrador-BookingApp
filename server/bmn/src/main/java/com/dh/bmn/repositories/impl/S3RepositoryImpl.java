package com.dh.bmn.repositories.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.dh.bmn.repositories.IS3Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.io.File;


@Repository
public class S3RepositoryImpl implements IS3Repository {

    private AmazonS3 s3Client;

    @Autowired
    public S3RepositoryImpl(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public void deleteFile (String bucketName, String fileKey) {
        s3Client.deleteObject(bucketName, fileKey);
    }

    public String uploadFile(String bucketName, String fileName, File fileObj) {
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
        fileObj.delete();
        return fileName;
    }
}

