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

    @Query(
        value = "SELECT w.id, " +
            "       w.service_name, " +
            "       w.center, " +
            "       COALESCE(GROUP_CONCAT(DISTINCT hs.content), '') AS homeStatuses, " +
            "       COALESCE(GROUP_CONCAT(DISTINCT it.content), '') AS interestTopics " +
            "FROM welfare w " +
            "LEFT JOIN welfare_home_status whs ON w.id = whs.welfare_id " +
            "LEFT JOIN home_status hs ON whs.home_status_id = hs.id " +
            "LEFT JOIN welfare_interest_topic wit ON w.id = wit.welfare_id " +
            "LEFT JOIN interest_topic it ON wit.interest_topic_id = it.id " +
            "WHERE (:cursor IS NULL OR w.id < :cursor) " +
            "  AND (:keyword IS NULL OR w.service_name LIKE %:keyword%) " +
            "  AND (:homeStatusList IS NULL OR hs.content IN (:homeStatusList)) " +
            "  AND (:interestTopicList IS NULL OR it.content IN (:interestTopicList)) " +
            "GROUP BY w.id, w.service_name, w.center " +
            "ORDER BY w.id DESC " +
            "LIMIT :size",
        nativeQuery = true)
    List<Object[]> searchFilteredNativeCursor(
        @Param("cursor") Long cursor,
        @Param("keyword") String keyword,
        @Param("homeStatusList") List<String> homeStatusList,
        @Param("interestTopicList") List<String> interestTopicList,
        @Param("size") int size
    );
}
