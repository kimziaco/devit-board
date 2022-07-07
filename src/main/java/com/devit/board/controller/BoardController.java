package com.devit.board.controller;


import com.devit.board.dto.BoardRequestDto;
import com.devit.board.entity.Board;
import com.devit.board.repository.BoardRepository;
import com.devit.board.response.ResponseDetails;
import com.devit.board.service.BoardService;
import com.devit.board.util.HttpStatusChangeInt;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class BoardController {

    private final BoardService boardService;


    @GetMapping("/index")
    public String main() {
        return "index";
    }


    @GetMapping("/header")
    public String header() {
        return "header";
    }

    @GetMapping("/footer")
    public String footer() {
        return "footer";
    }


    @GetMapping("/boards/write")
    public String write() {
        return "board-upload";
    }

//    @GetMapping("/boards/list")
//    public ResponseEntity<?> getBoard(@RequestHeader("Authorization") String data) { //throws NoResourceException {
//
//        String[] chunks = data.split("\\.");
//        Base64.Decoder decoder = Base64.getDecoder();
//        String payload = new String(decoder.decode(chunks[1]));
//
//        JSONObject jsonObject = new JSONObject(payload);
//        String sample = jsonObject.getString("uuid");
//        UUID uuid = UUID.fromString(sample);
//
//        List<Board> board = BoardService.getList(pageable);
//
//        // 프론트에서 필요할때 요청, 작성자: uuid값
//        // User findUser = userService.findUser(uuid); ? 어떤정보가 찾아져야하는지 ?
//
//        int httpStatus = HttpStatusChangeInt.ChangeStatusCode("OK");
//        String path = "boards/list";
//
//        //paging을 줘야하면dto값 주기
//        ResponseDetails responseDetails = new ResponseDetails(new Date(), board, httpStatus, path);
//        return new ResponseEntity<>(responseDetails, HttpStatus.CREATED);
//
//    }
    @GetMapping("/api/boards/list")
    public List<Board> list(){
        return boardService.list();
    }


    @GetMapping("/boards/write/success")
    public String boardsuccess() {

        return "board-upload-success.html";
    }

    @GetMapping("/update")
    public String update() {
        return "board/update";
    }


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
    public Page<Board> search(String keyword, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable ,Model model) {
        return boardService.search(keyword,pageable);
    }
}
