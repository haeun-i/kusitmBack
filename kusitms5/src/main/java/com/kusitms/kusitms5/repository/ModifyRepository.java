package com.kusitms.kusitms5.repository;

import com.kusitms.kusitms5.domain.Modify;
import com.kusitms.kusitms5.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ModifyRepository extends JpaRepository<Modify, Long> {

    @Query(value="SELECT * FROM store_modify WHERE store_id = :storeId ORDER BY modify_id DESC LIMIT 1", nativeQuery=true)
    Modify getLastModify(@Param("storeId") long storeId);

    @Query(value="select * from store_modify where user_id = :userId", nativeQuery = true)
    List<Modify> UserModifyList(@Param("userId") long userId);

}