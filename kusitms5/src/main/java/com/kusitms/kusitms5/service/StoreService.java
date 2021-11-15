package com.kusitms.kusitms5.service;

import com.kusitms.kusitms5.domain.*;
import com.kusitms.kusitms5.dto.reviewDto;
import com.kusitms.kusitms5.dto.StoreDto;
import com.kusitms.kusitms5.repository.StoreRepository;
import com.kusitms.kusitms5.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;


    @Transactional
    public Review addReview(Long storeId, String memo, int score) {
        User user = userRepository.findOneWithAuthoritiesByUsername("haeun").get();
        Store store = storeRepository.findById(storeId);

        Review review = Review.createReview(user, store, memo, score);
        storeRepository.saveReview(review);

        return review;
    }

    @Transactional
    public Modify addModify(Long storeId, String memo) {
        User user = userRepository.findOneWithAuthoritiesByUsername("haeun").get();
        Store store = storeRepository.findById(storeId);

        Modify modify = Modify.createModify(store, memo);
        storeRepository.saveModify(modify);

        return modify;
    }

    @Transactional
    public Report addReport(Long reviewId, String memo) {
        User user = userRepository.findOneWithAuthoritiesByUsername("haeun").get();
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
            storeDtos.add(response);
        }
        return storeDtos;
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
}