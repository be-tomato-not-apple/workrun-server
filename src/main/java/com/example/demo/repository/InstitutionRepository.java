package com.example.demo.repository;


import com.example.demo.domain.Institution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InstitutionRepository extends JpaRepository<Institution, Long>, JpaSpecificationExecutor<Institution> {

    // LIKE 검색 + 코드 필터
    @Query("""
              SELECT i FROM Institution i
                LEFT JOIN i.institutionType t
              WHERE (:code IS NULL OR t.code = :code)
                AND (
                  :q IS NULL OR
                  LOWER(i.name)    LIKE LOWER(CONCAT('%', :q, '%')) OR
                  LOWER(i.address) LIKE LOWER(CONCAT('%', :q, '%'))
                )
            """)
    Page<Institution> search(
            @Param("q") String q,
            @Param("code") String code,
            Pageable pageable
    );
}
