package com.kusitms.kusitms5.controller;

import com.kusitms.kusitms5.dto.CategoryPost;
import com.kusitms.kusitms5.dto.MarketNamePost;
import com.kusitms.kusitms5.dto.PriceModel;
import com.kusitms.kusitms5.response.BasicResponse;
import com.kusitms.kusitms5.response.CommonResponse;
import com.kusitms.kusitms5.service.QuoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class QuoteController {

    private final QuoteService quoteService;

    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    // 시장별 시세 -> 특정 시장의 : 어느 품목의 시세가 이럼
    @PostMapping("/quote/m-name")
    public ResponseEntity<? extends BasicResponse> findPriceByMarket(@RequestBody MarketNamePost marketName){
        List<PriceModel> priceModelList = quoteService.getPriceInfoByMarket(marketName.getMarketname());
        return ResponseEntity.ok().body(new CommonResponse<List<PriceModel>>(priceModelList));
    }

    // 품목별 시세 -> 특정 품목의 : 어느 시장의 시세가 이럼
    @PostMapping("/quote/a-name")
    public ResponseEntity<? extends BasicResponse> findPriceByCategory(@RequestBody CategoryPost category) {
        List<PriceModel> priceModelList = quoteService.getPriceInfoByCategory(category.getCategory());
        return ResponseEntity.ok().body(new CommonResponse<List<PriceModel>>(priceModelList));
    }
}
