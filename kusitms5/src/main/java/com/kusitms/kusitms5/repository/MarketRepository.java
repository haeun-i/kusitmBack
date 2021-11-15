package com.kusitms.kusitms5.repository;

import com.kusitms.kusitms5.domain.Market;
import com.kusitms.kusitms5.domain.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MarketRepository {
    private final EntityManager em;

    public List<Market> findPermanent(){ // 상설장 목록 불러오기
        return em.createQuery("select m from Market m where m.marketType = 0",
                Market.class)
                .getResultList();
    }

    public List<Market> findNotPermanent(){ // 비상설장 목록 불러오기
        return em.createQuery("select m from Market m where m.marketType != 0",
                Market.class)
                .getResultList();
    }

    public List<Market> findBig(){ // 대형 시장 목록 불러오기
        return em.createQuery("select m from Market m where m.storeCnt > 200",
                Market.class)
                .getResultList();
    }

    public List<Market> findMedium(){ // 중형 시장 목록 불러오기
        return em.createQuery("select m from Market m where m.storeCnt <= 200 and m.storeCnt >= 100",
                Market.class)
                .getResultList();
    }

    public List<Market> findSmall(){ // 소형 시장 목록 불러오기
        return em.createQuery("select m from Market m where m.storeCnt < 100",
                Market.class)
                .getResultList();
    }

    public List<Market> findOne(String name){ // 검색 했을 때 이름으로 시장 정보 불러오기
        return em.createQuery("select m from Market m where m.marketName = :name",
                Market.class)
                .setParameter("name", name)
                .getResultList();
    }

}
