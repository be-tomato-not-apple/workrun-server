package com.example.demo.repository;

import com.example.demo.domain.BookmarkType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookmarkTypeRepository extends JpaRepository<BookmarkType, Long> {
    
    Optional<BookmarkType> findByTypeCode(String typeCode);
}
