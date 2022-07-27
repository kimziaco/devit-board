package com.devit.board.controller;

import com.devit.board.dto.BoardRequestDto;
import com.devit.board.entity.Board;
import com.devit.board.service.BoardService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.List;
import java.util.UUID;


@Slf4j
@RestController
@AllArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final Logger logger = LoggerFactory.getLogger(BoardController.class.getName());

    @GetMapping("/list")
    public List<Board> list() {
        logger.info("게시글 조회");
        return boardService.list();
    }

    @PostMapping("/upload")
    public String upload(@ModelAttribute BoardRequestDto boardRequestDto, @RequestHeader(name = "Authorization", required = false) String data) throws IOException {
        logger.info("업로드 시작 Authorization:{} ",data);
        boardService.savePost(boardRequestDto, data);
        return "업로드성공";
    }

    @PostMapping("/{id}")
    public Long updateBoard(@PathVariable Long id, @ModelAttribute BoardRequestDto boardRequestDto) throws IOException {
        logger.info("게시글 수정하기, 수정하는 게시글의 id값 : {}",id);
        boardService.update(id, boardRequestDto);
        return id;
    }

    @DeleteMapping("/{id}")
    public String deleteBoard(@PathVariable Long id) {
        logger.info("게시글 삭제하기, 삭제하는 게시글의 id값 : {}",id);
        boardService.delete(id);
        return "삭제성공";
    }

    @GetMapping("/")
    public Page<Board> search(String keyword, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        logger.info("검색한 키워드 : {}", keyword);
        return boardService.search(keyword, pageable);
    }

    @GetMapping("/{id}")
    public Object boardDetail(@PathVariable UUID id) {
        logger.info("게시글 상세조회시 Uuid값 : {}", id);
        Board board = boardService.getDetail(id);

        if (board == null) {
            return "아이디가 존재하지 않습니다.";
        } else {
            return board;
        }
    }
}
