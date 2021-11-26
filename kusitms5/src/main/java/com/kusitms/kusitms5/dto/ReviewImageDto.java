package com.kusitms.kusitms5.dto;

import com.kusitms.kusitms5.domain.Review;
import com.kusitms.kusitms5.domain.ReviewImage;
import com.kusitms.kusitms5.domain.Store;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ReviewImageDto {
    private Long id;
    private String title;
    private String filePath;
    private Review review;

    public ReviewImage toEntity(){
        ReviewImage build = ReviewImage.builder()
                .id(id)
                .title(title)
                .filePath(filePath)
                .review(review)
                .build();
        return build;
    }

    @Builder
    public ReviewImageDto(String title, String filePath, Review review) {
        //this.id = id;
        this.title = title;
        this.filePath = filePath;
        this.review = review;
    }
}