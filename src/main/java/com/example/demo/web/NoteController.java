package com.example.demo.web;

import com.example.demo.domain.Note;
import com.example.demo.repository.NoteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {
    private final NoteRepository repo;

    public NoteController(NoteRepository repo) {
        this.repo = repo;
    }

    // 생성 테스트: POST /notes?msg=hello
    @PostMapping
    public ResponseEntity<Note> create(@RequestParam String msg) {
        Note saved = repo.save(new Note(msg));
        return ResponseEntity.ok(saved);
    }

    // 조회 테스트: GET /notes
    @GetMapping
    public ResponseEntity<List<Note>> list() {
        return ResponseEntity.ok(repo.findAll());
    }
}

