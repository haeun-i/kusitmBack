package com.kusitms.kusitms5.service;

import com.kusitms.kusitms5.domain.Love;
import com.kusitms.kusitms5.domain.Store;
import com.kusitms.kusitms5.dto.storeDto;
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

    public List<storeDto> findLike(Long userId) {
        List<Love> loves = loveRepository.likeList(userId);
        List<storeDto> likeStores = new ArrayList<>();
        for(Love love : loves) {
            Store store = love.getStore();
            storeDto response = new storeDto(store);
            likeStores.add(response);
        }
        return likeStores;
    }
}

