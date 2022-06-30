package com.devit.board.service;

import com.devit.board.dto.BoardRequestDto;
import com.devit.board.repository.BoardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class BoardService {
    private BoardRepository boardRepository;

    @Transactional
    public Long savePost(BoardRequestDto boardRequestDto) {

        return boardRepository.save(boardRequestDto.toEntity()).getId();
    }
}
