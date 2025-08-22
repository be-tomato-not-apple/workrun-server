package com.example.demo.repository;

import com.example.demo.domain.Welfare;
import org.springframework.data.domain.Page;
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
            "LEFT JOIN FETCH wit.interestTopic")
    Page<Welfare> findAllWithDetails(Pageable pageable);

    @Query("SELECT DISTINCT w FROM Welfare w " +
            "LEFT JOIN FETCH w.homeStatusList whs " +
            "LEFT JOIN FETCH whs.homeStatus " +
            "LEFT JOIN FETCH w.interestList wit " +
            "LEFT JOIN FETCH wit.interestTopic")
    List<Welfare> findAllWithDetails();
}
