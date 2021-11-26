package com.kusitms.kusitms5.repository;

import com.kusitms.kusitms5.domain.EventImage;
import com.kusitms.kusitms5.domain.Modify;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventImageRepository extends JpaRepository<EventImage, Long> {

    @Query(value="select * from event_image WHERE pay=false", nativeQuery = true)
    List<EventImage> EventList();

    @Query(value="select * from event_image WHERE pay=true", nativeQuery = true)
    List<EventImage> EventPayList();
}