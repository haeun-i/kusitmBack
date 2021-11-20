package com.kusitms.kusitms5.repository;

import com.kusitms.kusitms5.domain.EventImage;
import com.kusitms.kusitms5.domain.StoreImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoreImageRepository extends JpaRepository<StoreImage, Long> {
    @Query(value="select * from store_image where store_id = :storeId", nativeQuery = true)
    List<StoreImage> StoreImageList(@Param("storeId")long storeId);


}