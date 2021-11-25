package com.kusitms.kusitms5.controller;

import com.kusitms.kusitms5.dto.MarketDto;
import com.kusitms.kusitms5.dto.PopularList;
import com.kusitms.kusitms5.dto.StoreDto;
import com.kusitms.kusitms5.response.BasicResponse;
import com.kusitms.kusitms5.response.CommonResponse;
import com.kusitms.kusitms5.service.MarketService;
import com.kusitms.kusitms5.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class filterController {

    private final MarketService marketService;
    private final StoreService storeService;

    @GetMapping(value = "/market") // 모든 상설시장 목록 조회
    public ResponseEntity<? extends BasicResponse> marketAll() {
        List<MarketDto> markets = marketService.findAll();
        return ResponseEntity.ok().body(new CommonResponse<List<MarketDto>>(markets));
    }


    @GetMapping(value = "/market/filter1/0") // 모든 상설시장 목록 조회
    public ResponseEntity<? extends BasicResponse> marketPermanent() {
        List<MarketDto> markets = marketService.findPermanent();
        return ResponseEntity.ok().body(new CommonResponse<List<MarketDto>>(markets));
    }

    @GetMapping(value = "/market/filter1/1") // 모든 비상설시장 목록 조회
    public ResponseEntity<? extends BasicResponse> marketNotPermanent() {
        List<MarketDto> markets = marketService.findNotPermanent();
        return ResponseEntity.ok().body(new CommonResponse<List<MarketDto>>(markets));
    }


    @GetMapping(value = "/market/filter/gift") // 온누리상품권 사용 가능 점포 목록 조회
    public ResponseEntity<? extends BasicResponse> storeGiftList() {
        List<StoreDto> stores = storeService.findGift();
        return ResponseEntity.ok().body(new CommonResponse<List<StoreDto>>(stores));
    }

    @GetMapping(value = "/market/filter2/small") // 모든 소규모 시장 목록 조회
    public ResponseEntity<? extends BasicResponse> marketSmall() {
        List<MarketDto> markets = marketService.findSmall();
        return ResponseEntity.ok().body(new CommonResponse<List<MarketDto>>(markets));
    }

    @GetMapping(value = "/market/filter2/medium") // 모든 중간규모 시장 목록 조회
    public ResponseEntity<? extends BasicResponse> marketMedium() {
        List<MarketDto> markets = marketService.findMedium();
        return ResponseEntity.ok().body(new CommonResponse<List<MarketDto>>(markets));
    }

    @GetMapping(value = "/market/filter2/big") // 모든 대규모 시장 목록 조회
    public ResponseEntity<? extends BasicResponse> marketBig() {
        List<MarketDto> markets = marketService.findBig();
        return ResponseEntity.ok().body(new CommonResponse<List<MarketDto>>(markets));
    }


    @GetMapping(value = "/popular/store") // 많이 검색된 순서대로 조회
    public ResponseEntity<? extends BasicResponse> marketPopular() {
        return ResponseEntity.ok().body(new CommonResponse<List<MarketDto>>(PopularList.popular));
    }

}
