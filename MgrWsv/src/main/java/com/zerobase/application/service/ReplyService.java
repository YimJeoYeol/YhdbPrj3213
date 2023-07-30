package com.zerobase.application.service;

import com.zerobase.application.dto.ReplyDto;
import com.zerobase.domain.Board;
import com.zerobase.domain.Reply;
import com.zerobase.domain.User;
import com.zerobase.infrastructure.repository.BoardRepository;
import com.zerobase.infrastructure.repository.ReplyRepository;
import com.zerobase.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;

    @Transactional
    public Long writeReply(User user, Long boardId, ReplyDto replyDto) {
        //Board 안에 User과 같이 JOIN해서 들어가도록 해두었음.
        Board board = boardRepository.findByIdWithUser(boardId);
        replyDto.setBoard(board);

        //Repo로 조회한 Board에서 유저 정보를 가져와서 Dto로 넣음.
        replyDto.setUser(board.getUser());
        Reply reply = replyDto.toEntity();
        replyRepository.save(reply);
        return reply.getId();
    }

    @Transactional
    public List<ReplyDto> findAll(Long id){
        Board board = boardRepository.findByIdWithUser(id);
        List<Reply> replyList = board.getReplyList();
        return replyList.stream().map(new ReplyDto()::toDto).collect(Collectors.toList());

    }

    public void deleteReply(Long replyId) {
        Reply reply = replyRepository.findById(replyId).orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다. id = " + replyId));
        replyRepository.delete(reply);
    }
}
