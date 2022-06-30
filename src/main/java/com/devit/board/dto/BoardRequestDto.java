package com.devit.board.dto;

import com.devit.board.entity.Board;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardRequestDto {
    private Long id;
    private String price;
    private String title;
    private String content;
    private MultipartFile image;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public Board toEntity() {
        Board boardEntity = Board.builder()
                .id(id)
                .price(price)
                .title(title)
                .content(content)
                .build();
        return boardEntity;
    }

    @Builder
    public BoardRequestDto(Long id, String title, String content, String price,MultipartFile image, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.price = price;
        this.title = title;
        this.content = content;
        this.image = image;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
