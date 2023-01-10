package com.gabia.evoting.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gabia.evoting.domain.AgendaModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class AgendaResponseDto implements Serializable {
    private Long id;

    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime endDate;

    private int max_vote;

    private AgendaModel.Status status;

    private AgendaModel.Type type;

    public AgendaResponseDto(AgendaModel entity){
        this.id = entity.getId();
        this.description = entity.getDescription();
        this.startDate = entity.getStartDatetime();
        this.endDate = entity.getEndDatetime();
        this.max_vote = entity.getMax_vote();
        this.status = entity.getStatus();
        this.type = entity.getType();
    }
}
