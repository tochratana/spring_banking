package com.tochratana.mb_api.repository;

import com.tochratana.mb_api.domain.Segment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SegmentRepository extends JpaRepository<Segment, Integer> {
    Optional<Segment> findBySegmentAndIsDeletedFalse(String segment);
}