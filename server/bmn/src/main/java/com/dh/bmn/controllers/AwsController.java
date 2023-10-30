package com.dh.bmn.controllers;

import com.dh.bmn.dtos.responses.AssetResponseDto;
import com.dh.bmn.entity.Asset;
import com.dh.bmn.services.IS3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.net.MalformedURLException;
import java.net.URL;

@RestController
@Secured("ADMIN")
@RequestMapping(value = "/s3")
public class AwsController {
    private IS3Service awsService;

    @Value("${aws.s3.base-url}")
    private String s3BaseUrl;

    private URL assetUrl;

    @Autowired
    public AwsController(IS3Service awsService) {
        this.awsService = awsService;
    }

    @DeleteMapping("/deleteFile")
    public ResponseEntity<String> deleteFile(@RequestParam(value = "bucketName") String bucketName, @RequestParam(value = "fileName") String fileName) {
        awsService.deleteFile(bucketName, fileName);
        return new ResponseEntity<>("Archivo eliminado con exito", HttpStatus.OK);
    }

    @PostMapping("/uploadFile")
    public ResponseEntity<AssetResponseDto> uploadFile(@RequestParam(value = "bucketName") String bucketName, @RequestParam(value = "filePath") String filePath, @RequestParam(value = "file") MultipartFile file) {
        String key = awsService.uploadFile(bucketName, filePath, file);

        String assetUrlString = s3BaseUrl + "/" + bucketName + "/" + key;

        try {
            assetUrl = new URL(assetUrlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

//        Asset asset = Asset.builder()
//                .key(key)
//                .url(assetUrl)
//                .build();

        AssetResponseDto asset = new AssetResponseDto(key, assetUrl);

        return new ResponseEntity<>(asset, HttpStatus.OK);
    }


}
