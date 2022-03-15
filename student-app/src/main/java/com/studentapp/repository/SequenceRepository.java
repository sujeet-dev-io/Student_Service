package com.studentapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.studentapp.entity.Sequence;

@Repository
public interface SequenceRepository extends JpaRepository<Sequence, Integer> {

	List<Sequence> findAllByOrderByCreatedAtDesc();
}
