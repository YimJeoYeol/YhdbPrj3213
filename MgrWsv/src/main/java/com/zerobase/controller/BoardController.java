package com.zerobase.controller;

import com.zerobase.application.dto.BoardDto;
import com.zerobase.application.dto.VoicedataDto;
import com.zerobase.application.service.BoardService;
import com.zerobase.domain.Board;
import com.zerobase.domain.Voicedata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class BoardController {

    @Autowired
    private final BoardService boardService;

    //전체 글 보기
    //@GetMapping("/")
    //public String index(Model model, @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
     //   Page<Board> board = boardService.list(pageable);
      //  model.addAttribute("boards", board);
       // return "index";
   // }



    @GetMapping("/category/freeboard")
    public String freeList(Model model, @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC)
    Pageable pageable){
        Page<Board> free = boardService.list(pageable);
        model.addAttribute("boards", free);
        return "category/freeboard";
    }

    @GetMapping("/category/knowledge")
    public String knowledgeList(Model model, @PageableDefault(size = 5, sort = "id",
            direction = Sort.Direction.DESC) Pageable pageable) {

        Page<Board> knowledge = boardService.list(pageable);
        model.addAttribute("boards", knowledge);
        return "category/knowledge";
    }


    @GetMapping("/board/writeForm")
    public String writeForm() {
        return "board/writeForm";
    }


    @GetMapping("/board/{id}")
    public String articles(@PathVariable Long id, Model model) {
        BoardDto articles = boardService.findByIdWithUser(id);
        model.addAttribute("board", articles);
        return "board/articles";
    }

    //== 게시글 수정 ==//
    @GetMapping("/board/{id}/updateForm")
    public String updateForm(@PathVariable Long id, Model model) {
        BoardDto articles = boardService.details(id);
        model.addAttribute("board", articles);
        return "board/updateForm";
    }
}