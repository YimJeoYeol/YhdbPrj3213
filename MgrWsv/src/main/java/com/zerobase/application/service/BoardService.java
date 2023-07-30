package com.zerobase.application.service;

import com.zerobase.application.dto.BoardDto;
import com.zerobase.domain.Board;
import com.zerobase.domain.User;

import com.zerobase.infrastructure.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {
    private final BoardRepository boardRepository;

    //게시글 저장
    @Transactional
    public Long write(BoardDto boardDto, User user) {
        boardDto.setUser(user);
        Board saveBoard = boardDto.toEntity();
        boardRepository.save(saveBoard);
        return saveBoard.getId();
    }

    //페이징 처리한 전체목록
    @Transactional
    public Page<Board> list(Pageable pageable){
        return boardRepository.findAllWithUser(pageable);
    }

    //페이징 처리x 전체 목록
    @Transactional
    public List<BoardDto> findAll(){
        return boardRepository.findAll().stream().map(new BoardDto()::toDto).collect(Collectors.toList());

    }
    @Transactional
    public Page<Board> categoryList(Pageable pageable, String category){
        return boardRepository.findByCategory(pageable, category);
    }

    //id로 정보를 던지는 상세보기
    @Transactional
    public BoardDto details(Long id) {
        Board board = boardRepository.findByIdWithUser(id); //.orElseThrow(() -> new IllegalArgumentException("아이디를 찾을 수 없습니다"));
        return board.toDto();
    }

    //DTO로 정보를 던지는 상세보기
    @Transactional
    public BoardDto findById(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시물을 찾을 수 없습니다 id= " + id));
        return board.toDto();
    }

    //조회수 업데이트
    @Transactional
    public int updateViews(Long id) {
        return boardRepository.updateViews(id);
    }

    //게시물 삭제
    @Transactional
    public void delete(Long id) {
        boardRepository.deleteById(id);
    }


    @Transactional
    public void update(Long id, BoardDto requestBoardDto) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("글을 찾을 수 없습니다."));
        board.updateBoard(requestBoardDto.getTitle(), requestBoardDto.getContent(), requestBoardDto.getCategory());
    }
    @Transactional
    public BoardDto findBoardByTitle(String title) {
        Board board = boardRepository.findBoardByTitle(title);
        return board.toDto();
    }
    @Transactional
    public BoardDto findByIdWithUser(Long id) {
        Board board = boardRepository.findByIdWithUser(id);
        return board.toDto();
    }
}
