package com.kusitms.kusitms5.controller;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.*;
import com.kusitms.kusitms5.domain.Store;
import com.kusitms.kusitms5.dto.storeDto;
import com.kusitms.kusitms5.response.BasicResponse;
import com.kusitms.kusitms5.response.CommonResponse;
import com.kusitms.kusitms5.response.ErrorResponse;
import com.kusitms.kusitms5.service.StoreService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @GetMapping(value = "/store/getAll") // 모든 점포 목록 조회
    public ResponseEntity<? extends BasicResponse> storeListAll() {
        List<storeDto> stores = storeService.findAll();
        return ResponseEntity.ok().body(new CommonResponse<List<storeDto>>(stores));
    }

    @GetMapping(value = "/store/getOne") // 특정 시장 내의 모든 점포 목록 조회
    public ResponseEntity<? extends BasicResponse> storeList(String name) {
        List<storeDto> stores = storeService.findOne(name);
        return ResponseEntity.ok().body(new CommonResponse<List<storeDto>>(stores));
    }

    @GetMapping(value = "/store/get/gift") // 온누리상품권 사용 가능 점포 목록 조회
    public ResponseEntity<? extends BasicResponse> storeGiftList() {
        List<storeDto> stores = storeService.findGift();
        return ResponseEntity.ok().body(new CommonResponse<List<storeDto>>(stores));
    }
}
