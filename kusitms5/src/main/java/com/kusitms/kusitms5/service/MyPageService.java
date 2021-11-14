package com.kusitms.kusitms5.service;

import com.kusitms.kusitms5.domain.Notice;
import com.kusitms.kusitms5.domain.Question;
import com.kusitms.kusitms5.domain.User;
import com.kusitms.kusitms5.dto.NoticeDto;
import com.kusitms.kusitms5.dto.QuestionDto;
import com.kusitms.kusitms5.repository.NoticeRepository;
import com.kusitms.kusitms5.repository.QuestionRepository;
import com.kusitms.kusitms5.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MyPageService {

    private final NoticeRepository noticeRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    public MyPageService(NoticeRepository noticeRepository, QuestionRepository questionRepository, UserRepository userRepository) {
        this.noticeRepository = noticeRepository;
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
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

            QuestionDto target = new QuestionDto(origin.getTitle(),origin.getContent(),origin.getUser().getUsername());
            questionDtoList.add(target);
        }

        return questionDtoList;
    }

    // 등록
    // Question(title, content, username) User(username)
    public boolean registerQuestion(QuestionDto questionDto) {
        // 1. questionDto의 title, content을 question에 복사
        Question question = new Question(questionDto.getTitle(), questionDto.getContent());

        // 2. 해당 username에 해당하는 User찾기
        Optional<User> user = userRepository.findByUsername(questionDto.getUsername());
        if(user != null) {
            // 3. qeustionDto의 username을 User에 매핑
            question.setUser(user.get());
            // 4. question 저장
            questionRepository.save(question);
            return true;
        }

        return false;
    }
}
