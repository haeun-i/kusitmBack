package com.kusitms.kusitms5.service;

import com.kusitms.kusitms5.domain.*;
import com.kusitms.kusitms5.dto.NoticeDto;
import com.kusitms.kusitms5.dto.QuestionDto;
import com.kusitms.kusitms5.dto.StoreDto;
import com.kusitms.kusitms5.repository.*;
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
    private final MarketRepository marketRepository;
    private final StoreRepository storeRepository;

    public MyPageService(NoticeRepository noticeRepository, QuestionRepository questionRepository, UserRepository userRepository, MarketRepository marketRepository, StoreRepository storeRepository) {
        this.noticeRepository = noticeRepository;
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.marketRepository = marketRepository;
        this.storeRepository = storeRepository;
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
    @Transactional
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


    // 제보하기 : 사용자가 해당 위치에 점포를 등록하는 것
    // params : 가게위치(store_address),
    // 시장이름(market_id-market_info),
    // 가게이름(store_name),
    // 시장종류(market_id-market_type),
    // 종류(할거면 컬럼 추가해야함),
    // 운영시간(추가 할 부분 없는데, 추가해?)

    // 제보하기, 등록하기
    @Transactional
    public StoreDto reportStore(StoreDto storeDto) {
        Store store = new Store();
        Market market = marketRepository.findOne(storeDto.getMarketName()).stream().findFirst().get();


        market.setStoreCnt((market.getStoreCnt()+1)); // cnt 증가
        // 1. market_info에서 해당이름의 id찾기 -> store_info에 등록 + (상설, 비상설)
        store.setMarket(market);

        // 2. 가게이름 등록
        store.setStoreName(storeDto.getStoreName());
        // 3. 가게주소 등록
        store.setStoreAddress(storeDto.getStoreAddress());

        // 4. 온누리 가능
        store.setStoreGiftcard(storeDto.isStoreGiftcard());

        store.setStoreCategory(storeDto.getStoreCategory());
        store.setStorePhone(storeDto.getStorePhone());
        store.setStoreTime(storeDto.getStoreTime());
        store.setStoreLink(storeDto.getStoreLink());
        store.setStoreScore(0);
        storeRepository.saveStore(store);

        return storeDto;
    }
}
