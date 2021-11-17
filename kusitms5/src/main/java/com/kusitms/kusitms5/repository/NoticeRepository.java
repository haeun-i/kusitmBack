package com.kusitms.kusitms5.repository;

import com.kusitms.kusitms5.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    @Override
    List<Notice> findAll();
}
