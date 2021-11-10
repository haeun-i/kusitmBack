package com.kusitms.kusitms5.service;

import com.kusitms.kusitms5.domain.Like;
import com.kusitms.kusitms5.domain.Store;
import com.kusitms.kusitms5.dto.storeDto;
import com.kusitms.kusitms5.repository.LikeRepository;
import com.kusitms.kusitms5.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;

    @Transactional
    public void likes(long storeId, long userId){
        likeRepository.likes(storeId, userId);
    }

    @Transactional
    public void unlikes(long storeId, long userId){
        likeRepository.unlikes(storeId, userId);
    }

    public List<storeDto> findLike(Long userId) {
        List<Like> likes = likeRepository.likeList(userId);
        List<storeDto> likeStores = new ArrayList<>();
        for(Like like : likes) {
            Store store = like.getStore();
            storeDto response = new storeDto(store);
            likeStores.add(response);
        }
        return likeStores;
    }
}

