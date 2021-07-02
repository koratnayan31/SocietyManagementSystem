package com.nayan.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nayan.entity.Notice;

public interface NoticeRepo extends JpaRepository<Notice, Integer>{

}
