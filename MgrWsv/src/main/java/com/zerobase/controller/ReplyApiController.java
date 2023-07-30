package com.zerobase.controller;

import com.zerobase.application.dto.ReplyDto;
import com.zerobase.application.security.auth.PrincipalDetails;
import com.zerobase.application.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReplyApiController {

    private final ReplyService replyService;

    @GetMapping("/api/board/{boardId}/replys")
    public ResponseEntity<List<ReplyDto>> replyList(@PathVariable Long boardId) {
        return ResponseEntity.ok(replyService.findAll(boardId));
    }

    @PostMapping("/api/board/{boardId}/reply")
    public ResponseEntity<Long> writeReply(@PathVariable("boardId") Long boardId,
                                           @RequestBody ReplyDto replyDto,
                                           @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return ResponseEntity.ok(replyService.writeReply(principalDetails.getUser(), boardId, replyDto));
    }

    @DeleteMapping("/api/board/{boardId}/reply/{replyId}")
    public ResponseEntity<Long> deleteReply(@PathVariable("replyId") Long replyId){
        replyService.deleteReply(replyId);
        return ResponseEntity.ok(replyId);
    }
}
