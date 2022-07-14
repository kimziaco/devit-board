package com.devit.board.entity;

import com.devit.board.dto.BoardRequestDto;
import com.devit.board.entity.Timestamped;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "board")
public class Board extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 30, nullable = false)
    private String price;

    @Column(length = 200, nullable = false)
    private String content;

    @Column(length = 200, nullable = false)
    private String imageUrl;

    @Column(nullable = false, unique = true, columnDefinition = "BINARY(16)")
    private UUID boardUid;

    @Column(nullable = false, unique = true, columnDefinition = "BINARY(16)")
    private UUID userUid;

    @Column(length = 20, nullable = false)
    private String username;

    @Builder
    public Board(Long id, String title, String price, String content, String imageUrl) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.content = content;
        this.imageUrl = imageUrl;
    }


    public Board(BoardRequestDto boardRequestDto, String url) {
        this.title = boardRequestDto.getTitle();
        this.price = boardRequestDto.getPrice();
        this.content = boardRequestDto.getContent();
        this.imageUrl = url;
    }

    public void update(BoardRequestDto boardRequestDto){
        this.title = boardRequestDto.getTitle();
        this.content = boardRequestDto.getContent();
        this.price = boardRequestDto.getPrice();
    }
}

