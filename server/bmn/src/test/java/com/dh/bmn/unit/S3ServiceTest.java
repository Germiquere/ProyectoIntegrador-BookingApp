package com.dh.bmn.unit;

import com.amazonaws.services.s3.AmazonS3;
import com.dh.bmn.exceptions.RequestValidationException;
import com.dh.bmn.repositories.impl.S3RepositoryImpl;
import com.dh.bmn.services.impl.S3Service;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.net.URL;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;

@SpringBootTest(classes = S3ServiceTest.class)
@ExtendWith(MockitoExtension.class)
public class S3ServiceTest {

    @Mock
    private S3RepositoryImpl s3Repository;

    @Mock
    AmazonS3 amazonS3;
    @InjectMocks
    private S3Service s3Service;

    @BeforeEach
    public void setup() {

        s3Repository = mock(S3RepositoryImpl.class);
        amazonS3 = mock(AmazonS3.class);
        s3Service = new S3Service(s3Repository);
    }

    @Test
    public void testUploadFile() {
        String bucketName = "bikemenowbucket";
        String filePath = "imagenesS3/";
        String fileName = "1698280678574.jpg";

        MultipartFile file = createMockMultipartFile(fileName);

        Mockito.lenient().doReturn("https://s3.amazonaws.com//bikemenowbucket/imagenesS3/1698280678574.jpg")
        .when(s3Repository).uploadFile(eq(bucketName), startsWith("imagenesS3/"), any(File.class));

        String result = s3Service.uploadFile(bucketName, filePath, file);

        Assertions.assertEquals("https://s3.amazonaws.com//bikemenowbucket/imagenesS3/1698280678574.jpg", result);
    }

    private MultipartFile createMockMultipartFile(String fileName) {
        return new MockMultipartFile("file", fileName, "image/jpeg", new byte[0]);

    }

    @Test
    public void testDeleteFile() {
        String bucketName = "bikemenowbucket";
        String fileName = "imagenesS3/1698280678574.jpg";

        Mockito.doNothing().when(s3Repository).deleteFile(eq(bucketName), eq(fileName));

        s3Service.deleteFile(bucketName, fileName);

        Mockito.verify(s3Repository).deleteFile(eq(bucketName), eq(fileName));
    }


    @Test
    public void testValidarUrlNula() {
        URL urlNula = null;

        Assertions.assertThrows(RequestValidationException.class, () -> {
            s3Service.validarUrlNula(urlNula);
        });
    }

    @Test
    public void testValidarKey() {
        String key = null;

        Assertions.assertThrows(RequestValidationException.class, () -> {
            s3Service.validarKey(key);
        });
    }
}
