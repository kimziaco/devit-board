package com.devit.board.rabbitMQ.entity;


import com.devit.board.entity.Board;
import com.devit.board.rabbitMQ.dto.ResumeDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@EntityListeners(AuditingEntityListener.class)
@Setter
@NoArgsConstructor
@Entity
@Table(name = "resume")
public class Resume{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String username;

    @Column(nullable = false, columnDefinition = "BINARY(16)")
    private UUID userUid;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardUid")
    @JsonIgnore
    private Board board;

    public Resume(ResumeDto resumedto) {
        this.username = resumedto.getUserName();
        this.userUid = resumedto.getUserUid();
    }


}
