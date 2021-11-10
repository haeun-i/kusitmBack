package com.kusitms.kusitms5.controller;

import com.kusitms.kusitms5.dto.storeDto;
import com.kusitms.kusitms5.response.BasicResponse;
import com.kusitms.kusitms5.response.CommonResponse;
import com.kusitms.kusitms5.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class userController {

    private final LikeService likeService;

    @PostMapping("/likes")
    public ResponseEntity<? extends BasicResponse> likes(@RequestParam("storeId") Long storeId,
                                                         @RequestParam("userId") Long userId){
        likeService.likes(storeId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/unlikes")
    public ResponseEntity<? extends BasicResponse> unlikes(@RequestParam("storeId") Long storeId,
                                                           @RequestParam("userId") Long userId){
        likeService.unlikes(storeId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/store/like")
    public ResponseEntity<? extends BasicResponse> likeList(@RequestParam("userId") Long userId){
        List<storeDto> likeStores = likeService.findLike(userId);
        return ResponseEntity.ok().body(new CommonResponse<List<storeDto>>(likeStores));
    }
}
