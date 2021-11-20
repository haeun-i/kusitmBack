package com.kusitms.kusitms5.service;

import com.kusitms.kusitms5.domain.EventImage;
import com.kusitms.kusitms5.domain.Store;
import com.kusitms.kusitms5.domain.StoreImage;
import com.kusitms.kusitms5.dto.EventImageDto;
import com.kusitms.kusitms5.dto.StoreDto;
import com.kusitms.kusitms5.dto.StoreImageDto;
import com.kusitms.kusitms5.repository.EventImageRepository;
import com.kusitms.kusitms5.repository.StoreImageRepository;
import com.kusitms.kusitms5.repository.StoreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ImageService {
    private EventImageRepository eventImageRepository;
    private StoreImageRepository storeImageRepository;
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

    public void saveStoreImage(StoreImageDto storeImageDto) {
        storeImageRepository.save(storeImageDto.toEntity());
    }

    public List<StoreImageDto> findStoreImage(Long storeId) {
        Store store = storeRepository.findById(storeId);
        List<StoreImage> images = storeImageRepository.StoreImageList(storeId);
        List<StoreImageDto> imageDtos = new ArrayList<>();

        for(StoreImage image : images) {
            StoreImageDto response = new StoreImageDto(image.getTitle(), image.getFilepath(), store);
            imageDtos.add(response);
        }
        return imageDtos;
    }
}