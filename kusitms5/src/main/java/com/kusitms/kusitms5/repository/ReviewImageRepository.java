package com.kusitms.kusitms5.repository;

import com.kusitms.kusitms5.domain.EventImage;
import com.kusitms.kusitms5.domain.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long> {
    @Query(value="select * from review_image where review_id = :reviewId", nativeQuery = true)
    ReviewImage ReviewImageList(@Param("reviewId")long reviewId);


}