package com.kusitms.kusitms5.controller;

import com.kusitms.kusitms5.domain.Notice;
import com.kusitms.kusitms5.domain.Question;
import com.kusitms.kusitms5.dto.NoticeDto;
import com.kusitms.kusitms5.dto.QuestionDto;
import com.kusitms.kusitms5.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("/mypage/notice")
    public List<NoticeDto> findNotice() {
        return myPageService.findNotice();
    }

    @GetMapping("mypage/question")
    public List<QuestionDto> findQuestion() {
        return myPageService.findQuestion();
    }

}
