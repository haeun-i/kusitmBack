package com.kusitms.kusitms5.price;

import com.kusitms.kusitms5.dto.StoreDto;
import com.kusitms.kusitms5.response.BasicResponse;
import com.kusitms.kusitms5.response.CommonResponse;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PriceController {

    private final PriceService priceService;

    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @GetMapping("readAPI/")
    public ResponseEntity<? extends BasicResponse> getAPI() throws ParseException {
        priceService.getProductCodeByKeword();
        return ResponseEntity.ok().body(new CommonResponse<String>(".."));

    }
}
