package com.example.demo.repository;

import com.example.demo.domain.Institution;
import com.example.demo.web.dto.InstitutionDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InstitutionRepository extends JpaRepository<Institution, Long> {

    @Query("""
              SELECT new com.example.demo.web.dto.InstitutionDto(
                i.id, i.name, t.displayName, i.address, i.homepageUrl
              )
              FROM Institution i
              JOIN i.institutionType t
              WHERE (:code IS NULL OR t.code = :code)
            """)
    List<InstitutionDto> findAllSimple(@Param("code") String code);
}