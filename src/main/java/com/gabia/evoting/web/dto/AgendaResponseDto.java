package com.gabia.evoting.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gabia.evoting.domain.AgendaModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class AgendaResponseDto implements Serializable {
    private Long id;

    private String description;

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDate;

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDate;

    private int maxVote;

    private AgendaModel.Status status;

    private AgendaModel.Type type;

    public AgendaResponseDto(AgendaModel entity){
        this.id = entity.getId();
        this.description = entity.getDescription();
        this.startDate = entity.getStartDateTime();
        this.endDate = entity.getEndDateTime();
        this.maxVote = entity.getMaxVote();
        this.status = entity.getStatus();
        this.type = entity.getType();
    }
}
