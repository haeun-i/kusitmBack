package com.kusitms.kusitms5.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kusitms.kusitms5.domain.Review;
import com.kusitms.kusitms5.domain.Store;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class reviewDto {

    String reviewUserName;
    String reviewMemo;
    int reviewScore;

    public reviewDto(Review review){
        reviewUserName = review.getUser().getUserName();
        reviewMemo = review.getReviewMemo();
        reviewScore = review.getReviewScore();
    }
}
