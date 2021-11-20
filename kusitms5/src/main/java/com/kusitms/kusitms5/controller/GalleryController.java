package com.kusitms.kusitms5.controller;

import com.kusitms.kusitms5.domain.EventImage;
import com.kusitms.kusitms5.domain.Store;
import com.kusitms.kusitms5.dto.EventImageDto;
import com.kusitms.kusitms5.dto.MarketDto;
import com.kusitms.kusitms5.dto.StoreImageDto;
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
        return ResponseEntity.ok().body(new CommonResponse<List<EventImage>>(images));
    }

    @GetMapping("/eventImage/pay")
    public ResponseEntity<? extends BasicResponse> eventPay() throws IOException {
        List<EventImage> images = imageService.findPayEventImages();
        return ResponseEntity.ok().body(new CommonResponse<List<EventImage>>(images));
    }

    @PostMapping("/storeImage/post")
    public ResponseEntity<? extends BasicResponse> storeUpload(MultipartFile file, Long storeId) throws IOException {
        String imgPath = s3Service.upload(file);
        String imgName = file.getOriginalFilename();
        Store store = storeService.findOneById(storeId);

        StoreImageDto storeImageDto = new StoreImageDto(imgName, imgPath, store);

        imageService.saveStoreImage(storeImageDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/storeImage/{storeId}")
    public ResponseEntity<? extends BasicResponse> eventPay(@Valid @RequestParam Long storeId) throws IOException {
        List<StoreImageDto> images =  imageService.findStoreImage(storeId);
        return ResponseEntity.ok().body(new CommonResponse<List<StoreImageDto>>(images));
    }

}