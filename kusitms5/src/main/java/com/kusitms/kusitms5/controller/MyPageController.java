package com.kusitms.kusitms5.controller;

import com.kusitms.kusitms5.domain.Notice;
import com.kusitms.kusitms5.domain.Question;
import com.kusitms.kusitms5.dto.NoticeDto;
import com.kusitms.kusitms5.dto.QuestionDto;
import com.kusitms.kusitms5.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

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
    public ResponseEntity<Boolean> registerQuestion(
        @Valid @RequestBody QuestionDto questionDto
    ){
        return ResponseEntity.ok(myPageService.registerQuestion(questionDto));
    }
}
