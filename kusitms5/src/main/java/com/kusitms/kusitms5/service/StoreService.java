package com.kusitms.kusitms5.service;

import com.kusitms.kusitms5.domain.Store;
import com.kusitms.kusitms5.dto.storeDto;
import com.kusitms.kusitms5.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;

    public List<storeDto> findAll() {
        List<Store> stores = storeRepository.findAll();
        List<storeDto> storeDtos = new ArrayList<>();
        for(Store store : stores) {
            storeDto response = new storeDto(store);
            storeDtos.add(response);
        }
        return storeDtos;
    }

    public List<storeDto> findOne(String name) {
        List<Store> stores = storeRepository.findOne(name);
        List<storeDto> storeDtos = new ArrayList<>();
        for(Store store : stores) {
            storeDto response = new storeDto(store);
            storeDtos.add(response);
        }
        return storeDtos;
    }

    public List<storeDto> findGift() {
        List<Store> stores = storeRepository.findGift();
        List<storeDto> storeDtos = new ArrayList<>();
        for(Store store : stores) {
            storeDto response = new storeDto(store);
            storeDtos.add(response);
        }
        return storeDtos;
    }
}
