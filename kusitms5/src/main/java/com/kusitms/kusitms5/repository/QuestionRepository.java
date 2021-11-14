package com.kusitms.kusitms5.repository;

import com.kusitms.kusitms5.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Override
    List<Question> findAll();

}
