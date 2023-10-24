package com.dh.bmn.services;

import com.dh.bmn.entity.vm.Asset;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;


public interface AwsService {
    String uploadFile(String bucketName, String filePath, MultipartFile file);
    String getS3FileContent(String bucketName, String fileName) throws IOException;
    List<Asset> getS3Files(String bucketName) throws IOException;
    byte[] downloadFile(String bucketName, String fileName) throws IOException;
    void moveObject(String bucketName, String fileKey, String destinationFileKey);
    void deleteObject (String bucketName, String fileKey);

}
