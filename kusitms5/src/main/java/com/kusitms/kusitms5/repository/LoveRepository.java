package com.kusitms.kusitms5.repository;

import com.kusitms.kusitms5.domain.Love;
import com.kusitms.kusitms5.domain.Market;
import com.kusitms.kusitms5.domain.Store;
import com.kusitms.kusitms5.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LoveRepository extends JpaRepository<Love, Long> {
    Love findLovesByStoreAndUser(Store store, User user);


    @Modifying
    @Query(value="INSERT INTO kusitms5.loves (store_id, user_id) VALUES (:storeId, :userId)", nativeQuery = true)
    void likes(@Param("storeId")long storeId, @Param("userId")long userId);

    @Modifying
    @Query(value="DELETE FROM loves WHERE store_id = :storeId AND user_id = :userId", nativeQuery = true)
    void unlikes(@Param("storeId")long storeId, @Param("userId")long userId);

    @Query(value="SELECT * FROM loves WHERE user_id = :userId", nativeQuery = true)
    List<Love> likeList(@Param("userId")long userId);


}
