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

    public List<Market> findAll(){ // 상설장 목록 불러오기
        return em.createQuery("select m from Market m",
                Market.class)
                .getResultList();
    }

    public void addClick(Long market) {
        em.createQuery("UPDATE Market m SET m.marketClick = m.marketClick + 1" +
                "WHERE m.marketId = :market")
                .setParameter("market", market)
                .executeUpdate();
    }

    public void deleteClick(){
        em.createQuery("UPDATE Market m SET m.marketClick = 0")
                .executeUpdate();
    }

    public List<Market> findPopular(){ // 상위 인기 10위
        return em.createQuery("SELECT m FROM Market m ORDER BY m.marketClick DESC",
                Market.class)
                .setFirstResult(0)
                .setMaxResults(10)
                .getResultList();
    }

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
