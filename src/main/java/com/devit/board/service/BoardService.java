package com.devit.board.service;

import com.devit.board.dto.BoardRequestDto;
import com.devit.board.entity.Board;
import com.devit.board.repository.BoardRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final S3Uploader s3Uploader;

    public Long savePost(BoardRequestDto boardRequestDto) throws IOException {
        String url = s3Uploader.upload(boardRequestDto.getImage(), "static");
        Board board = new Board(boardRequestDto, url);
        boardRepository.save(board);
        System.out.println(url);
        System.out.println(board.getId());
        return board.getId();
    }
}
