package com.kusitms.kusitms5.controller;

import com.amazonaws.services.ec2.model.Image;
import com.kusitms.kusitms5.domain.EventImage;
import com.kusitms.kusitms5.domain.Store;
import com.kusitms.kusitms5.dto.EventImageDto;
import com.kusitms.kusitms5.dto.MarketDto;
import com.kusitms.kusitms5.dto.ReviewImageDto;
import com.kusitms.kusitms5.response.BasicResponse;
import com.kusitms.kusitms5.response.CommonResponse;
import com.kusitms.kusitms5.service.ImageService;
import com.kusitms.kusitms5.service.S3Service;
import com.kusitms.kusitms5.service.StoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class GalleryController {
    private final S3Service s3Service;
    private final ImageService imageService;
    private final StoreService storeService;

    @PostMapping("/eventImage/post")
    public ResponseEntity<? extends BasicResponse> eventUpload(MultipartFile file) throws IOException {
        String imgPath = s3Service.upload(file);
        String imgName = file.getOriginalFilename();

        EventImageDto eventImageDto = new EventImageDto(imgName, imgPath, false);

        imageService.saveEventImage(eventImageDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/eventImage/all")
    public ResponseEntity<? extends BasicResponse> eventAll() throws IOException {
        List<EventImage> images = imageService.findEventImages();
        List<String> paths = new ArrayList<>();
        for(EventImage image : images){
            String path = image.getFilepath();
            paths.add(path);
        }
        return ResponseEntity.ok().body(new CommonResponse<List<String>>(paths));
    }

    @GetMapping("/eventImage/pay")
    public ResponseEntity<? extends BasicResponse> eventPay() throws IOException {
        List<EventImage> images = imageService.findPayEventImages();
        List<String> paths = new ArrayList<>();
        for(EventImage image : images){
            String path = image.getFilepath();
            paths.add(path);
        }
        return ResponseEntity.ok().body(new CommonResponse<List<String>>(paths));
    }

    @GetMapping("/storeImage/store") // 가게 별 리뷰 이미지 전부 반환
    public ResponseEntity<? extends BasicResponse> eventPay(String storeName) throws IOException {
        Store store = storeService.findRealOne(storeName);
        List<ReviewImageDto> images =  imageService.findStoreImage(store.getStoreId());
        List<String> paths = new ArrayList<>();
        for(ReviewImageDto image : images){
            String path = image.getFilePath();
            paths.add(path);
        }
        return ResponseEntity.ok().body(new CommonResponse<List<String>>(paths));
    }

}