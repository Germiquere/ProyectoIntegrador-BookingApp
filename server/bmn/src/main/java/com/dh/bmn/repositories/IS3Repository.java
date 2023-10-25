package com.dh.bmn.repositories;

import java.io.File;

public interface IS3Repository {

    String uploadFile(String bucketName, String fileName, File fileObj);
    void deleteFile(String bucketName, String fileKey);
}


