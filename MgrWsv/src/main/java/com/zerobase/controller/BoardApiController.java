package com.zerobase.controller;

import java.util.List;

import com.zerobase.application.dto.BoardDto;
import com.zerobase.application.security.auth.PrincipalDetails;
import com.zerobase.application.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@RestController
public class BoardApiController {

    @Autowired
    private final BoardService boardService;

    @PostMapping("/api/board")
    public ResponseEntity<Long> write(@RequestBody BoardDto boardDto, @AuthenticationPrincipal PrincipalDetails principalDetails ) {
        return ResponseEntity.ok(boardService.write(boardDto, principalDetails.getUser()));
    }

    @GetMapping("/api/board/{id}")
    public ResponseEntity<BoardDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(boardService.findById(id));
    }

    @GetMapping("/api/boards")
    public ResponseEntity<List<BoardDto>> findAll() {
        return ResponseEntity.ok(boardService.findAll());
    }

    @PostMapping("/api/board/delete{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        boardService.delete(id);
        return  ResponseEntity.ok(id);
    }

    @PostMapping("/api/board/update/{id}")
    public ResponseEntity<Long> update(@PathVariable Long id, @RequestBody BoardDto requestBoardDto) {
        boardService.update(id, requestBoardDto);
        return ResponseEntity.ok(id);

    }
}
