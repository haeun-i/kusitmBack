package com.kusitms.kusitms5.service;

import com.kusitms.kusitms5.domain.Love;
import com.kusitms.kusitms5.domain.Store;
import com.kusitms.kusitms5.dto.StoreDto;
import com.kusitms.kusitms5.repository.LoveRepository;

import com.kusitms.kusitms5.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LoveRepository loveRepository;
    private final UserRepository userRepository;

    @Transactional
    public void likes(long storeId, long userId){
        loveRepository.likes(storeId, userId);
    }

    @Transactional
    public void unlikes(long storeId, long userId){
        loveRepository.unlikes(storeId, userId);
    }

    public List<StoreDto> findLike(Long userId) {
        List<Love> loves = loveRepository.likeList(userId);
        List<StoreDto> likeStores = new ArrayList<>();
        for(Love love : loves) {
            Store store = love.getStore();
            StoreDto response = new StoreDto(store);
            likeStores.add(response);
        }
        return likeStores;
    }
}

