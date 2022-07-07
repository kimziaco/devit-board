package com.devit.board.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class BoardRequestDto {
    private String price;
    private String title;
    private String content;
    private MultipartFile image;
}