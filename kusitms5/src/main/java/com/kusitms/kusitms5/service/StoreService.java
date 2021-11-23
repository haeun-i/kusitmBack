package com.kusitms.kusitms5.service;

import com.kusitms.kusitms5.domain.*;
import com.kusitms.kusitms5.dto.PopularList;
import com.kusitms.kusitms5.dto.reviewDto;
import com.kusitms.kusitms5.dto.StoreDto;
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


    @Transactional
    public Review addReview(String userName, Long storeId, String memo, int score) {

        User user = userRepository.findOneWithAuthoritiesByUsername(userName).get();
        Store store = storeRepository.findById(storeId);

        Review review = Review.createReview(user, store, memo, score);
        storeRepository.saveReview(review);

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
    public Report addReport(String userName, Long reviewId, String memo) {
        User user = userRepository.findOneWithAuthoritiesByUsername(userName).get();
        Review review = storeRepository.findReview(reviewId);

        Report report = Report.createReport(review, memo);
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

    @Transactional
    public void addClick(String name){
        List<Store> stores = storeRepository.findOne(name);
        for(Store store : stores) {
            long storeId = store.getStoreId();
            storeRepository.addClick(storeId);
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
            storeDtos.add(response);
        }
        return storeDtos;
    }

    @Transactional
    @Scheduled(cron="0 0 * * * *")
    public void findPopular() {
        List<Store> popularStores = storeRepository.findPopular();
        List<StoreDto> storeDtos = new ArrayList<>();
        for (Store store : popularStores) {
            StoreDto response = new StoreDto(store);
            storeDtos.add(response);
        }

        PopularList.popular = storeDtos;
        storeRepository.deleteClick();
    }

    public void updateStore(StoreDto store) {
        // 뭘 수정하지?
        Store findStore = storeRepository.findOne(store.getStoreName()).get(0);
        findStore.setStoreAddress(store.getStoreAddress());

    }
}