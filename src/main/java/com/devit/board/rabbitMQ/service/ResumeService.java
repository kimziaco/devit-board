package com.devit.board.rabbitMQ.service;

import com.devit.board.entity.Board;
import com.devit.board.rabbitMQ.entity.Resume;
import com.devit.board.rabbitMQ.dto.ResumeDto;
import com.devit.board.repository.BoardRepository;
import com.devit.board.rabbitMQ.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final BoardRepository boardRepository;



    public Long saveResume(ResumeDto resumedto){
        Resume resume = new Resume(resumedto);
        Board board = boardRepository.findByBoardUid(resumedto.getBoardUid()).get();
        resume.setBoard(board);
        resumeRepository.save(resume);
        return resume.getId();

    }
    public List<Resume> list() {
        return resumeRepository.findAll();
    }
}
