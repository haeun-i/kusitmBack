package com.kusitms.kusitms5.repository;
import com.kusitms.kusitms5.domain.User;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

// repository : JPA레파지토리를 상속함
public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = "authorities")
        // username을 기준으로 User정보를 가져올 때 권한 정보도 같이 가져오게 된다.
    Optional<User> findOneWithAuthoritiesByUsername(String username);
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
    boolean existsByNickname(String nickname);

    @Modifying
    @Transactional
    @Query(value="UPDATE user_info SET user_click = :cnt WHERE user_id = :userId", nativeQuery = true)
    void saveCnt(@Param("cnt")int cnt, @Param("userId")long userId);

}
