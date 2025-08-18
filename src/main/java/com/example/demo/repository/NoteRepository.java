package com.example.demo.repository;

import com.example.demo.domain.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> { }

