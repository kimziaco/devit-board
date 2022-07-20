package com.devit.board.service;


import com.devit.board.dto.BoardRequestDto;
import com.devit.board.entity.Board;
import com.devit.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.IOException;
import java.util.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final S3Uploader s3Uploader;
    private final Logger logger = LoggerFactory.getLogger(BoardService.class.getName());


    public Long savePost(BoardRequestDto boardRequestDto, String data) throws IOException {

        String url;
        if (boardRequestDto.getImage() == null) {
            url = "default";
        } else {
            url = s3Uploader.upload(boardRequestDto.getImage(), "static");
            logger.info("업로드 하는 사진 url:{}",url);

        }
        Board board = new Board(boardRequestDto, url);

        UUID BoardUuid = UUID.randomUUID();
        UUID userUuid = getUuid(data);

        String username = getUsername(data);
        board.setUsername(username);
        board.setBoardUid(BoardUuid);
        board.setUserUid(userUuid);

        boardRepository.save(board);
        logger.info("업로드 하는 사진 url:{}",url);
        return board.getId();
    }

    public Long update(Long id, BoardRequestDto boardRequestDto) throws IOException {

        logger.info("수정 할 id를 가진 board 찾기");
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("잘못된 게시물 입니다.")
        );

        String url= board.getImageUrl();
        logger.info("업로드 하는 사진 url:{}",url);

        if (boardRequestDto.getImage() != null) {
            url = s3Uploader.upload(boardRequestDto.getImage(), "static");
        }

        logger.info("board 수정하기");
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
        log.info("검색한 키워드: {}",keyword);
        if (keyword == null || keyword.equals("")) {
            return boardRepository.findAll(pageable);
        } else {
            return boardRepository.findByTitleOrContentContaining(keyword, pageable);
        }
    }

    public UUID getUuid(String data) { //throws NoResourceException {

        String[] chunks = data.split("\\.");
        Base64.Decoder decoder = Base64.getDecoder();
        String payload = new String(decoder.decode(chunks[1]));
        logger.info("uuid payload 값: {}",payload);

        JSONObject jsonObject = new JSONObject(payload);
        String sample = jsonObject.getString("uuid");
        logger.info("디코딩한 uuid: {}",sample);
        return UUID.fromString(sample);
    }

    public String getUsername(String data) { //throws NoResourceException {

        String[] chunks = data.split("\\.");
        Base64.Decoder decoder = Base64.getDecoder();
        String payload = new String(decoder.decode(chunks[1]));
        logger.info("username payload 값: {}",payload);

        JSONObject jsonObject = new JSONObject(payload);
        logger.info("디코딩한 username: {}",jsonObject.getString("nickName"));
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
