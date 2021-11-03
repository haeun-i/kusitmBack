package com.kusitms.kusitms5.repository;

import com.kusitms.kusitms5.domain.Market;
import com.kusitms.kusitms5.domain.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class StoreRepository {
    private final EntityManager em;


    public List<Store> findAll(){ // 모든 시장의 점포 목록 불러오기
        return em.createQuery("select s from Store s",
                Store.class)
                .getResultList();
    }

    public List<Store> findOne(String name){ // 해당 시장에 해당하는 점포 목록 불러오기
        return em.createQuery("select s from Store s inner join fetch s.market m where m.marketName = :name",
                Store.class)
                .setParameter("name", name)
                .getResultList();
    }

    public List<Store> findGift(){ // 온누리 상품권 사용점포 목록 불러오기
        return em.createQuery("select s from Store s where s.storeGiftcard = true",
                Store.class)
                .getResultList();
    }

}
