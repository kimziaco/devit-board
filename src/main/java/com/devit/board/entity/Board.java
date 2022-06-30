package com.devit.board.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "board")
public class Board extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 10, nullable = false)
    private String price;

    @Column(length = 200, nullable = false)
    private String content;

    @Builder
    public Board(Long id, String title, String price, String content) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.content = content;
    }


}
