package com.dev.log.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;
import com.dev.log.domain.SampleVO;
import io.minio.*;


@RestController
@RequestMapping("/api/obj")
public class ObjectRestController {
    
    private final MinioClient minioClient;

    public ObjectRestController(MinioClient minioClient){
	    this.minioClient= minioClient;
    }

    @PostMapping(value="/file-upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> fileUpload(@RequestPart("file") MultipartFile file) {
       try {
            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket("multipart")
                    .object(file.getName())
                    .stream(file, file.available(), -1)
                    .build();
            minioClient.putObject(args);
        } catch (Exception e) {
            throw new RuntimeException("Error while fetching files in Minio", e);
        } 


        return ResponseEntity.ok("done");
    }
    

}
