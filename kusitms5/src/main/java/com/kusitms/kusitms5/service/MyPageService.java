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

    // ??????
    // Question(title, content, username) User(username)
    @Transactional
    public boolean registerQuestion(QuestionDto questionDto) {
        // 1. questionDto??? title, content??? question??? ??????
        Question question = new Question(questionDto.getTitle(), questionDto.getContent());

        // 2. ?????? username??? ???????????? User??????
        Optional<User> user = userRepository.findByUsername(questionDto.getUsername());
        if(user != null) {
            // 3. qeustionDto??? username??? User??? ??????
            question.setUser(user.get());
            // 4. question ??????
            questionRepository.save(question);
            return true;
        }

        return false;
    }


    // ???????????? : ???????????? ?????? ????????? ????????? ???????????? ???
    // params : ????????????(store_address),
    // ????????????(market_id-market_info),
    // ????????????(store_name),
    // ????????????(market_id-market_type),
    // ??????(????????? ?????? ???????????????),
    // ????????????(?????? ??? ?????? ?????????, ??????????)

    // ????????????, ????????????
    @Transactional
    public StoreDto reportStore(StoreDto storeDto) {
        Store store = new Store();
        Market market = marketRepository.findOne(storeDto.getMarketName()).stream().findFirst().get();


        market.setStoreCnt((market.getStoreCnt()+1)); // cnt ??????
        // 1. market_info?????? ??????????????? id?????? -> store_info??? ?????? + (??????, ?????????)
        store.setMarket(market);

        // 2. ???????????? ??????
        store.setStoreName(storeDto.getStoreName());
        // 3. ???????????? ??????
        store.setStoreAddress(storeDto.getStoreAddress());

        // 4. ????????? ??????
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
