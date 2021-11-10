package com.kusitms.kusitms5.repository;

import com.kusitms.kusitms5.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final EntityManager em;

    public User findOne(Long id) { // cart_id로 카트 찾아오는 함수
        return em.find(User.class, id);
    }

}
