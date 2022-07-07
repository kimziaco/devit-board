package com.devit.board.controller;

import com.devit.board.dto.BoardRequestDto;
import com.devit.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class AwsS3Controller {

    private final BoardService boardService;

    @PostMapping("/api/boards/write")
    public String upload(@ModelAttribute BoardRequestDto boardRequestDto,@RequestHeader("Authorization")String data) throws IOException {
        boardService.savePost(boardRequestDto,data);
        return "업로드를 성공했습니다.";
    }

}