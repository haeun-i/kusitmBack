package com.kusitms.kusitms5.repository;

import com.kusitms.kusitms5.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Override
    List<Question> findAll();

    @Query(value="select * from question where user_id = :userId", nativeQuery = true)
    List<Question> UserQuestionList(@Param("userId")long userId);

}
