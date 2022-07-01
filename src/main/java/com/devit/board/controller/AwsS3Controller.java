package com.devit.board.controller;
import com.devit.board.dto.BoardRequestDto;
import com.devit.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.devit.board.service.S3Uploader;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class AwsS3Controller {

    private final S3Uploader s3Uploader;
    private final BoardService boardService;

    @PostMapping("/images")
    public String upload(@ModelAttribute BoardRequestDto boardRequestDto) throws IOException {
        boardService.savePost(boardRequestDto);
        return "test";
    }

}