package com.kusitms.kusitms5.repository;

import com.kusitms.kusitms5.domain.Like;
import com.kusitms.kusitms5.domain.Store;
import com.kusitms.kusitms5.domain.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Like findLikesByStoreAndUser(Store store, User user);

    @Modifying
    @Query(value="INSERT INTO user_store(store_id, user_id) VALUES (:storeId, :userId)", nativeQuery = true)
    void likes(@Param("storeId")long storeId, @Param("userId")long userId);

    @Modifying
    @Query(value="DELETE FROM user_store WHERE store_id = :storeId AND user_id = :userId", nativeQuery = true)
    void unlikes(@Param("storeId")long storeId, @Param("userId")long userId);

    @Query(value="select * from user_store where user_id = :userId", nativeQuery = true)
    List<Like> likeList(@Param("userId")long userId);

}
