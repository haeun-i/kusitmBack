package com.kusitms.kusitms5.service;

import com.kusitms.kusitms5.domain.EventImage;
import com.kusitms.kusitms5.domain.Review;
import com.kusitms.kusitms5.domain.ReviewImage;
import com.kusitms.kusitms5.domain.Store;
import com.kusitms.kusitms5.dto.EventImageDto;
import com.kusitms.kusitms5.dto.ReviewImageDto;
import com.kusitms.kusitms5.repository.EventImageRepository;
import com.kusitms.kusitms5.repository.ReviewImageRepository;
import com.kusitms.kusitms5.repository.StoreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ImageService {
    private EventImageRepository eventImageRepository;
    private ReviewImageRepository reviewImageRepository;
    private StoreRepository storeRepository;

    public void saveEventImage(EventImageDto eventImageDto) {
        eventImageRepository.save(eventImageDto.toEntity());
    }

    public List<EventImage> findEventImages(){
        return eventImageRepository.EventList();
    }

    public List<EventImage> findPayEventImages(){
        return eventImageRepository.EventPayList();
    }

    public void saveReviewImage(ReviewImageDto reviewImageDto) {
        reviewImageRepository.save(reviewImageDto.toEntity());
    }

    public List<ReviewImageDto> findStoreImage(Long storeId) {
        Store store = storeRepository.findById(storeId);
        List<Review> reviews = storeRepository.findReviewList(store);

        List<ReviewImageDto> imageDtos = new ArrayList<>();

        for(Review review : reviews) {
            ReviewImage image = reviewImageRepository.ReviewImageList(review.getReviewId());

            if(image != null) {
                ReviewImageDto response = new ReviewImageDto(image.getTitle(), image.getFilepath(), review);
                imageDtos.add(response);
            }

        }
        return imageDtos;
    }
}