package com.kusitms.kusitms5.controller;

import com.kusitms.kusitms5.domain.*;
import com.kusitms.kusitms5.dto.MarketDto;
import com.kusitms.kusitms5.dto.NoticeDto;
import com.kusitms.kusitms5.dto.QuestionDto;
import com.kusitms.kusitms5.dto.StoreDto;
import com.kusitms.kusitms5.service.MarketService;
import com.kusitms.kusitms5.service.MyPageService;
import com.kusitms.kusitms5.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;
    private final MarketService marketService;
    private final UserService userService;

    @GetMapping("/mypage/notice")
    public List<NoticeDto> findNotice() {
        return myPageService.findNotice();
    }

    @GetMapping("/mypage/question")
    public List<QuestionDto> findQuestion() {
        return myPageService.findQuestion();
    }

    // true:저장완료, false: 저장못함
    @PostMapping("/mypage/register-question")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", required = true, dataType = "String", paramType = "header")
    })
    public ResponseEntity<Boolean> registerQuestion(
        @Valid @RequestBody QuestionDto questionDto
    ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();
        Optional<User> user = userService.getUserWithAuthorities(id);

        questionDto.setUsername(user.get().getNickname());
        return ResponseEntity.ok(myPageService.registerQuestion(questionDto));
    }


    @PostMapping("mypage/report-store")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", required = true, dataType = "String", paramType = "header")
    })
    public ResponseEntity<StoreDto> reportStore( // 시장이름, 점포이름, 점포주소, 시장종류
            @Valid @RequestBody StoreDto storeDto
    ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();
        Optional<User> user = userService.getUserWithAuthorities(id);

        storeDto.setUserName(user.get().getNickname());

        return ResponseEntity.ok(myPageService.reportStore(storeDto));
    }



}
