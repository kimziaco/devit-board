package com.devit.board.controller;

import com.devit.board.dto.BoardRequestDto;
import com.devit.board.repository.BoardRepository;
import com.devit.board.service.BoardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@AllArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final BoardRepository BoardRepository;


    @GetMapping("/index")
    public String main() {
        return "index";
    }

    @GetMapping("/boards")
    public String list() {
        return "board";
    }

    @GetMapping("/header")
    public String header(){
        return "header";
    }

    @GetMapping("/footer")
    public String footer(){
        return "footer";
    }


    @GetMapping("/boards/write")
    public String write() {
        return "board-upload";
    }

    @PostMapping("/boards/write")
    public String write(@RequestBody BoardRequestDto boardRequestDto) {
        boardService.savePost(boardRequestDto);
        return "redirect:/";
    }

    @GetMapping("/boards/write/success")
    public String boardsuccess() {

        return "board-upload-success";
    }

    @GetMapping("/update")
    public String update() {
        return "board/update";
    }
}
