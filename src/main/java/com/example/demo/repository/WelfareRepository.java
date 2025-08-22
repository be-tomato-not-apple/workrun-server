package com.example.demo.repository;

import com.example.demo.domain.Welfare;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WelfareRepository extends JpaRepository<Welfare, Long> {

    @Query("SELECT w FROM Welfare w " +
            "LEFT JOIN FETCH w.homeStatusList whs " +
            "LEFT JOIN FETCH whs.homeStatus " +
            "LEFT JOIN FETCH w.interestList wit " +
            "LEFT JOIN FETCH wit.interestTopic " +
            "WHERE w.id = :id")
    Optional<Welfare> findByIdWithDetails(@Param("id") Long id);

    @Query("SELECT DISTINCT w FROM Welfare w " +
            "LEFT JOIN FETCH w.homeStatusList whs " +
            "LEFT JOIN FETCH whs.homeStatus " +
            "LEFT JOIN FETCH w.interestList wit " +
            "LEFT JOIN FETCH wit.interestTopic " +
            "WHERE (:cursor IS NULL OR w.id < :cursor) " +
            "ORDER BY w.id DESC")
    List<Welfare> findWithCursor(@Param("cursor") Long cursor, Pageable pageable);

    @Query("SELECT DISTINCT w FROM Welfare w " +
            "LEFT JOIN FETCH w.homeStatusList whs " +
            "LEFT JOIN FETCH whs.homeStatus " +
            "LEFT JOIN FETCH w.interestList wit " +
            "LEFT JOIN FETCH wit.interestTopic " +
            "ORDER BY w.id DESC")
    List<Welfare> findAllWithDetails();
}
