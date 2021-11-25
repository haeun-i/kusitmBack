package com.kusitms.kusitms5.controller;

import com.kusitms.kusitms5.domain.*;
import com.kusitms.kusitms5.dto.MarketDto;
import com.kusitms.kusitms5.dto.ReviewImageDto;
import com.kusitms.kusitms5.dto.reviewDto;
import com.kusitms.kusitms5.dto.StoreDto;
import com.kusitms.kusitms5.response.BasicResponse;
import com.kusitms.kusitms5.response.CommonResponse;
import com.kusitms.kusitms5.service.*;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class storeController {
    private final UserService userService;
    private final StoreService storeService;
    private final MarketService marketService;
    private final S3Service s3Service;
    private final ImageService imageService;


    @GetMapping(value = "/store/getOne") // 특정 점포정보 조회
    public ResponseEntity<? extends BasicResponse> storeList(String name) {
        List<StoreDto> stores = storeService.findOne(name);
        return ResponseEntity.ok().body(new CommonResponse<List<StoreDto>>(stores));
    }

    @GetMapping(value = "/market/getOne") // 특정 시장정보 조회
    public ResponseEntity<? extends BasicResponse> marketList(String name) {
        List<MarketDto> markets = marketService.findOne(name);
        storeService.addClick(name);
        return ResponseEntity.ok().body(new CommonResponse<List<MarketDto>>(markets));
    }



    @GetMapping(value = "/store") // 특정 점포정보 조회
    public ResponseEntity<? extends BasicResponse> storeAll() {
        List<StoreDto> stores = storeService.findAll();
        return ResponseEntity.ok().body(new CommonResponse<List<StoreDto>>(stores));
    }


    @PostMapping(value = "/store/writeReview") // 리뷰 작성
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", required = true, dataType = "String", paramType = "header")
    })
    public ResponseEntity<? extends BasicResponse> writeReview(MultipartFile file,
                                                               String storeName,
                                                               String memo,
                                                               double score) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Store store = storeService.findRealOne(storeName);

        Review review = storeService.addReview(userName, store.getStoreId(), memo, score);

        String imgPath = s3Service.upload(file);
        String imgName = file.getOriginalFilename();

        ReviewImageDto reviewImageDto = new ReviewImageDto(imgName, imgPath, review);
        imageService.saveReviewImage(reviewImageDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/store/writeReviewReport") // 리뷰 신고 작성
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", required = true, dataType = "String", paramType = "header")
    })
    public ResponseEntity<? extends BasicResponse> writeReviewReport(@RequestParam("reviewMemo") String reviewMemo,
                                                               @RequestParam("memo") String memo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        Review review = storeService.findReviewbyMemo(reviewMemo);
        Report report = storeService.addReport(userName, review.getReviewMemo(), memo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/store/writeStoreModify") // 가게 수정 사항 등록
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", required = true, dataType = "String", paramType = "header")
    })
    public ResponseEntity<? extends BasicResponse> writeStoreModify(@RequestParam("storeName") String storeName,
                                                                     @RequestParam("memo") String memo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        Store store = storeService.findRealOne(storeName);
        Modify modify = storeService.addModify(userName, store.getStoreId(), memo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/store/getReview") // 가게별 리뷰 확인
    public ResponseEntity<? extends BasicResponse> getReview(@RequestParam("storeName") String storeName) {
        Store store = storeService.findRealOne(storeName);
        List<reviewDto> reviews = storeService.findReview(store.getStoreId());
        return ResponseEntity.ok().body(new CommonResponse<List<reviewDto>>(reviews));
    }



}