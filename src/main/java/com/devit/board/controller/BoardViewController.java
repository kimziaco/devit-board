package com.devit.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardViewController {

    @GetMapping("/board")
    public String main() {
        return "board";
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
    public String boardWrite() {
        return "board-upload";
    }

    @GetMapping("/boards/write-success")
    public String boardUploadSuccess() {
        return "board-upload-success";
    }

    @GetMapping("/boards/write-fail")
    public String boardUploadFail() {
        return "board-upload-fail";
    }

    @GetMapping("/boards/detail")
    public String boardDetail() {
        return "board-detail";
    }

    @GetMapping("/boards/update")
    public String update(){
        return "board-update";
    }

    @GetMapping("/boards/update-success")
    public String boardUpdateSuccess(){
        return "board-update-success";
    }

    @GetMapping("/boards/update-fail")
    public String boardUpdateFail(){
        return "board-update-fail";
    }




}
