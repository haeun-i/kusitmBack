package com.kusitms.kusitms5.repository;

import com.kusitms.kusitms5.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class StoreRepository {
    private final EntityManager em;

    public Store findById(Long id){     // 레시피 검색
        return em.find(Store.class, id);
    }
    public Review findReview(Long id){     // 레시피 검색
        return em.find(Review.class, id);
    }

    public List<Store> findOne(String name){ // 이름을 통한 점포 검색 결과 불러오기
        return em.createQuery("select s from Store s where s.storeName = :name",
                Store.class)
                .setParameter("name", name)
                .getResultList();
    }

    public List<Store> findGift(){ // 온누리 상품권 사용점포 목록 불러오기
        return em.createQuery("select s from Store s where s.storeGiftcard = true",
                Store.class)
                .getResultList();
    }

    public void saveReview(Review review) {
        em.persist(review);
    } // 리뷰 작성

    public void saveReport(Report report) {
        em.persist(report);
    } // 리뷰 신고 작성

    public void saveModify(Modify modify) {
        em.persist(modify);
    } // 가게 수정사항 작성

    public void addClick(Long store) {
        em.createQuery("UPDATE Store s SET s.storeClick = s.storeClick + 1" +
                        "WHERE s.storeId = :store")
                .setParameter("store", store)
                .executeUpdate();
    }// 가게 수정사항 작성
    
    public List<Review> findReviewList(Store store){ // 가게 별 리뷰 전체 불러오기
        return em.createQuery("select r from Review r where r.store = :store",
                Review.class)
                .setParameter("store", store)
                .getResultList();
    }

    public void saveStore(Store store) {
        em.persist(store);
    }

    public List<Review> findUserReview(User user){ // 사용자가 작성한 리뷰 전체 불러오기
        return em.createQuery("select r from Review r where r.user = :user",
                Review.class)
                .setParameter("user", user)
                .getResultList();
    }

    public List<Store> findPopular(){ // 상위 인기 10위
        return em.createQuery("SELECT s FROM Store s ORDER BY s.storeClick DESC",
                Store.class)
                .setFirstResult(0)
                .setMaxResults(10)
                .getResultList();
    }

    public void deleteClick(){
        em.createQuery("UPDATE Store s SET s.storeClick = 0")
                .executeUpdate();
    }


}
