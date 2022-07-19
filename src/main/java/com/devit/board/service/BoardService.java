package com.devit.board.service;

import com.devit.board.dto.BoardRequestDto;
import com.devit.board.entity.Board;
import com.devit.board.repository.BoardRepository;
import com.devit.board.response.ResponseDetails;
import com.devit.board.util.HttpStatusChangeInt;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestHeader;

import java.io.IOException;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final S3Uploader s3Uploader;


    public Long savePost(BoardRequestDto boardRequestDto, String data) throws IOException {

        String url;
        if (boardRequestDto.getImage() == null) {
            url = "default";
        } else {
            url = s3Uploader.upload(boardRequestDto.getImage(), "static");
        }
        Board board = new Board(boardRequestDto, url);
        UUID BoardUuid = UUID.randomUUID();
        UUID userUuid = getUuid(data);

        String username = getUsername(data);
        board.setUsername(username);
        board.setBoardUid(BoardUuid);
        board.setUserUid(userUuid);


        boardRepository.save(board);
        System.out.println(url);
        System.out.println(board.getId());
        return board.getId();
    }

    public Long update(Long id, BoardRequestDto boardRequestDto) throws IOException {

        String url;
        if (boardRequestDto.getImage() == null) {
            url = "default";
        } else {
            url = s3Uploader.upload(boardRequestDto.getImage(), "static");
        }

        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("잘못된 게시물 입니다.")
        );

        board.update(boardRequestDto, url);
        return board.getId();
    }


    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    public Page<Board> getBoardList(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    public List<Board> list() {
        return boardRepository.findAll();
    }

    public Page<Board> search(String keyword, Pageable pageable) {
        System.out.println("1" + keyword); //
        if (keyword == null || keyword.equals("")) {
            return boardRepository.findAll(pageable);
        } else {
            return boardRepository.findByTitleContaining(keyword, pageable);
        }

    }

    public UUID getUuid(String data) { //throws NoResourceException {

        String[] chunks = data.split("\\.");
        Base64.Decoder decoder = Base64.getDecoder();
        String payload = new String(decoder.decode(chunks[1]));

        JSONObject jsonObject = new JSONObject(payload);
        String sample = jsonObject.getString("uuid");
        return UUID.fromString(sample);
    }

    public String getUsername(String data) { //throws NoResourceException {

        String[] chunks = data.split("\\.");
        Base64.Decoder decoder = Base64.getDecoder();
        String payload = new String(decoder.decode(chunks[1]));

        JSONObject jsonObject = new JSONObject(payload);
        return jsonObject.getString("nickName");
    }

    public Board getDetail(UUID id) {
        Optional<Board> boardOptional = boardRepository.findByBoardUid(id);
        if (boardOptional.isEmpty()) {
            return null;
        } else {
            return boardOptional.get();
        }
    }

}
