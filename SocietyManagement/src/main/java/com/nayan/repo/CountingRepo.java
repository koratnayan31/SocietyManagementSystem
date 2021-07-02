package com.nayan.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nayan.entity.Counting;

public interface CountingRepo extends JpaRepository<Counting,Short> {

}
