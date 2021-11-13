package com.kusitms.kusitms5.service;

import com.kusitms.kusitms5.domain.Notice;
import com.kusitms.kusitms5.domain.Question;
import com.kusitms.kusitms5.dto.NoticeDto;
import com.kusitms.kusitms5.dto.QuestionDto;
import com.kusitms.kusitms5.repository.NoticeRepository;
import com.kusitms.kusitms5.repository.QuestionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class MyPageService {

    private final NoticeRepository noticeRepository;
    private final QuestionRepository questionRepository;

    public MyPageService(NoticeRepository noticeRepository, QuestionRepository questionRepository) {
        this.noticeRepository = noticeRepository;
        this.questionRepository = questionRepository;
    }

    @Transactional
    public List<NoticeDto> findNotice() {

        List<NoticeDto> noticeDtoList = new ArrayList<>();
        List<Notice> NoticeList = noticeRepository.findAll();

        for (Notice origin:
                NoticeList) {
            NoticeDto target = new NoticeDto();
            BeanUtils.copyProperties(origin, target);
            noticeDtoList.add(target);
        }

        return noticeDtoList;
    }

    @Transactional
    public List<QuestionDto> findQuestion() {
        List<QuestionDto> questionDtoList = new ArrayList<>();
        List<Question> questionList = questionRepository.findAll();

        for (Question origin:
             questionList) {

            QuestionDto target = new QuestionDto(origin.getId(),origin.getTitle(),origin.getContent(),origin.getUser().getUsername());
            questionDtoList.add(target);
        }

        return questionDtoList;
    }
}
