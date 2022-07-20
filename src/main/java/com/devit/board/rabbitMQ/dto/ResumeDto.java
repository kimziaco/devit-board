package com.devit.board.rabbitMQ.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.UUID;
@Setter
@Getter
@Component
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id", scope = ResumeDto.class)
public class ResumeDto implements Serializable {
    private UUID userUid;
    private String userName;
    private UUID boardUid;

    public ResumeDto(UUID userUid,  String userName, UUID boardUid) {
        this.userUid = userUid;
        this.userName = userName;
        this.boardUid = boardUid;
    }

    public ResumeDto() {
    }

    @Override
    public String toString() {
        return "Resume{" +
                " userUid='" + userUid + '\'' +
                ", userName='" + userName + '\'' +
                ", boardUid='" + boardUid + '\'' +
                '}';
    }
}
