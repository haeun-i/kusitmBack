package com.kusitms.kusitms5.controller;

import com.kusitms.kusitms5.response.BasicResponse;
import com.kusitms.kusitms5.service.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class ImageController {

    private final S3Uploader s3Uploader;

    @PostMapping("/images")
    public ResponseEntity<? extends BasicResponse> upload(@RequestPart("images") MultipartFile multipartFile) throws IOException {
        s3Uploader.upload(multipartFile, "kusitms5");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

