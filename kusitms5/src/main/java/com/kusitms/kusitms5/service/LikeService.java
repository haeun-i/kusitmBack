package com.kusitms.kusitms5.service;

import com.kusitms.kusitms5.domain.Love;
import com.kusitms.kusitms5.domain.Market;
import com.kusitms.kusitms5.domain.Store;
import com.kusitms.kusitms5.domain.User;
import com.kusitms.kusitms5.dto.StoreDto;
import com.kusitms.kusitms5.dto.UserDto;
import com.kusitms.kusitms5.repository.LoveRepository;

import com.kusitms.kusitms5.repository.StoreRepository;
import com.kusitms.kusitms5.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LoveRepository loveRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public void likes(long storeId, long userId){
        Store store = storeRepository.findById(storeId);

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

    // 시장과 연결된 사람들 리스트
    public List<User> findLikeUser(String storeName) {
        List<User> users = new ArrayList<>();
        // 1. 이름에 맞는 store 찾기
        Store findStore = storeRepository.findOne(storeName).get(0);
        // 2. 해당 store과 관련된 연관 데이터 불러오기
        List<Love> findLoveList = findStore.getLoves();
        for (Love origin :
                findLoveList) {
            User target = new User();
            BeanUtils.copyProperties(origin.getUser(), target);
            users.add(target);
        }

        return users;
    }

}

