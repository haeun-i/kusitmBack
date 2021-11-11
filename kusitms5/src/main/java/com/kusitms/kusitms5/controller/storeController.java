package com.kusitms.kusitms5.controller;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.*;
import com.kusitms.kusitms5.domain.*;
import com.kusitms.kusitms5.dto.reviewDto;
import com.kusitms.kusitms5.dto.storeDto;
import com.kusitms.kusitms5.response.BasicResponse;
import com.kusitms.kusitms5.response.CommonResponse;
import com.kusitms.kusitms5.response.ErrorResponse;
import com.kusitms.kusitms5.service.LikeService;
import com.kusitms.kusitms5.service.StoreService;
import com.kusitms.kusitms5.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class storeController {
    private final UserService userService;
    private final StoreService storeService;


    @GetMapping(value = "/store/getOne") // 특정 점포 정보 조회
    public ResponseEntity<? extends BasicResponse> storeList(String name) {
        List<storeDto> stores = storeService.findOne(name);
        return ResponseEntity.ok().body(new CommonResponse<List<storeDto>>(stores));
    }

    @PostMapping(value = "/store/writeReview") // 리뷰 작성
    public ResponseEntity<? extends BasicResponse> writeReview(@RequestParam("storeId") Long storeId,
                                                               @RequestParam("memo") String memo,
                                                               @RequestParam("score") int score) {
        Review review = storeService.addReview(storeId, memo, score);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/store/writeReviewReport") // 리뷰 신고 작성
    public ResponseEntity<? extends BasicResponse> writeReviewReport(@RequestParam("reviewId") Long reviewId,
                                                               @RequestParam("memo") String memo) {
        Report report = storeService.addReport(reviewId, memo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/store/writeStoreModify") // 가게 수정 사항 등록
    public ResponseEntity<? extends BasicResponse> writeStoreModify(@RequestParam("storeId") Long storeId,
                                                                     @RequestParam("memo") String memo) {
        Modify modify = storeService.addModify(storeId, memo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/store/getReview") // 가게별 리뷰 확인
    public ResponseEntity<? extends BasicResponse> getReview(@RequestParam("storeId") Long storeId) {
        List<reviewDto> reviews = storeService.findReview(storeId);
        return ResponseEntity.ok().body(new CommonResponse<List<reviewDto>>(reviews));
    }


}