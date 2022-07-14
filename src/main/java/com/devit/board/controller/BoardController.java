package com.devit.board.controller;
import com.devit.board.dto.BoardRequestDto;
import com.devit.board.entity.Board;
import com.devit.board.service.BoardService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.List;
import java.util.UUID;


@RestController
@AllArgsConstructor
public class BoardController {

    private final BoardService boardService;


    @GetMapping("/api/boards/list")
    public List<Board> list() {
        return boardService.list();
    }

    @PostMapping("/api/boards/upload")
    public String upload(@ModelAttribute BoardRequestDto boardRequestDto,@RequestHeader(name="Authorization",required = false) String data) throws IOException {
        System.out.println("here"+ data);
        boardService.savePost(boardRequestDto,data);
        return "업로드성공";
    }

//    @PostMapping("/api/boards/upload")
//    public String upload(@ModelAttribute BoardRequestDto boardRequestDto) throws IOException {
//        System.out.println("1");
//        boardService.savePost(boardRequestDto);
//        return "업로드를 성공했습니다.";
//    }


    @PutMapping("/api/boards/{id}")
    public Long updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto boardRequestDto) {
        boardService.update(id, boardRequestDto);
        return id;
    }
    @DeleteMapping("/api/boards/{id}")
    public String deleteBoard(@PathVariable Long id) {
        boardService.delete(id);
        return "board";
    }

    @GetMapping("/api/boards")
    public Page<Board> search(String keyword, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        return boardService.search(keyword, pageable);
    }

    @GetMapping("/api/boards/{id}")
    public Object boardDetail(@PathVariable UUID id){
        Board board = boardService.getDetail(id);
        if(board == null) {
            return "아이디가 존재하지 않습니다.";
        } else {
            return board;
        }
    }





}
