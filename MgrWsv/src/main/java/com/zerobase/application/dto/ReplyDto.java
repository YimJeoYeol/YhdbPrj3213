package com.zerobase.application.dto;

import com.zerobase.domain.Board;
import com.zerobase.domain.Reply;
import com.zerobase.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplyDto {
    private Long id;
    private String content;
    private Board board;
    private User user;

    public Reply toEntity() {
        return Reply.builder()
                .id(id)
                .content(content)
                .board(board)
                .user(user)
                .build();
    }

    public ReplyDto toDto(Reply reply) {
        this.id = reply.getId();
        this.content = reply.getContent();
        this.board = reply.getBoard();
        this.user = reply.getUser();
        return this;
    }
}
