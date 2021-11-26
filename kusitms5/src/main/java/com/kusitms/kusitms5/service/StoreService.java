package com.kusitms.kusitms5.service;

import com.kusitms.kusitms5.domain.*;
import com.kusitms.kusitms5.dto.MarketDto;
import com.kusitms.kusitms5.dto.PopularList;
import com.kusitms.kusitms5.dto.reviewDto;
import com.kusitms.kusitms5.dto.StoreDto;
import com.kusitms.kusitms5.repository.MarketRepository;
import com.kusitms.kusitms5.repository.ModifyRepository;
import com.kusitms.kusitms5.repository.StoreRepository;
import com.kusitms.kusitms5.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final ModifyRepository modifyRepository;
    private final MarketRepository marketRepository;


    @Transactional
    public Review addReview(String userName, Long storeId, String memo, double score) {

        User user = userRepository.findOneWithAuthoritiesByUsername(userName).get();
        Store store = storeRepository.findById(storeId);

        Review review = Review.createReview(user, store, memo, score);
        storeRepository.saveReview(review);
        double newScore = this.resetStoreScore(storeId);
        storeRepository.resetScore(newScore, storeId);
        return review;
    }

    @Transactional
    public Modify addModify(String userName, Long storeId, String memo) {

        User user = userRepository.findOneWithAuthoritiesByUsername(userName).get();
        Store store = storeRepository.findById(storeId);

        Modify modify = Modify.createModify(user, store, memo);
        storeRepository.saveModify(modify);

        return modify;
    }

    @Transactional
    public Report addReport(String userName, String reviewMemo, String memo) {
        User user = userRepository.findOneWithAuthoritiesByUsername(userName).get();
        List<Review> review = storeRepository.fineReview(reviewMemo);

        Report report = Report.createReport(review.get(0), memo);
        storeRepository.saveReport(report);

        return report;
    }

    public List<reviewDto> findReview(Long storeId) {
        Store store = storeRepository.findById(storeId);
        List<Review> reviews = storeRepository.findReviewList(store);
        List<reviewDto> reviewDtos = new ArrayList<>();
        for(Review review : reviews) {
            reviewDto response = new reviewDto(review);
            reviewDtos.add(response);
        }
        return reviewDtos;
    }

    public double resetStoreScore(Long storeId){
        Store store = storeRepository.findById(storeId);
        List<Review> reviews = storeRepository.findReviewList(store);
        double score = 0;
        int reviewCnt = 0;
        for(Review review : reviews){
            reviewCnt = reviewCnt + 1;
            score = score + review.getReviewScore();
        }
        return score/reviewCnt;
    }

    public Review findReviewbyMemo(String memo) {
        return storeRepository.fineReview(memo).get(0);
    }

    public List<StoreDto> findOne(String name) {
        List<Store> stores = storeRepository.findOne(name);
        List<StoreDto> storeDtos = new ArrayList<>();
        for(Store store : stores) {
            StoreDto response = new StoreDto(store);

            if(modifyRepository.getLastModify(store.getStoreId()) == null){
                response.setUserName("admin");
            }
            else{
                Modify m = modifyRepository.getLastModify(store.getStoreId());
                response.setUserName(m.getUser().getNickname());
            }

            storeDtos.add(response);
        }
        return storeDtos;
    }

    public Store findRealOne(String name) {
        return storeRepository.findOne(name).get(0);
    }

    public List<StoreDto> findAll() { // 상설장 목록 불러오기
        List<Store> stores = storeRepository.findAll();
        List<StoreDto> storeDtos = new ArrayList<>();
        for(Store store : stores) {
            StoreDto response = new StoreDto(store);

            if(modifyRepository.getLastModify(store.getStoreId()) == null){
                response.setUserName("admin");
            }
            else{
                Modify m = modifyRepository.getLastModify(store.getStoreId());
                response.setUserName(m.getUser().getNickname());
            }

            storeDtos.add(response);
        }
        return storeDtos;
    }

    @Transactional
    public void addClick(String name){
        List<Market> markets = marketRepository.findOne(name);
        for(Market market : markets) {
            long marketId = market.getMarketId();
            marketRepository.addClick(marketId);
        }
    }

    public Store findOneById(Long storeId) {
        Store store = storeRepository.findById(storeId);

        return store;
    }


    public List<StoreDto> findGift() {
        List<Store> stores = storeRepository.findGift();
        List<StoreDto> storeDtos = new ArrayList<>();
        for(Store store : stores) {
            StoreDto response = new StoreDto(store);

            if(modifyRepository.getLastModify(store.getStoreId()) == null){
                response.setUserName("admin");
            }
            else{
                Modify m = modifyRepository.getLastModify(store.getStoreId());
                response.setUserName(m.getUser().getNickname());
            }

            storeDtos.add(response);
        }
        return storeDtos;
    }

    @Transactional
    @Scheduled(cron="0 0 0 * * *")
    public void findPopular() {
        List<Market> popularMarket = marketRepository.findPopular();
        List<MarketDto> marketDtos = new ArrayList<>();
        for (Market market : popularMarket) {
            MarketDto response = new MarketDto(market);
            marketDtos.add(response);
        }

        PopularList.popular = marketDtos;
        marketRepository.deleteClick();
    }

    public void updateStore(StoreDto store) {
        Store findStore = storeRepository.findOne(store.getStoreName()).get(0);
        findStore.UpdateStore(store);
    }
}