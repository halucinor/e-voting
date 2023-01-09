package com.gabia.evoting.web.dto;

import com.gabia.evoting.domain.AgendaModel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AgendaResponseDto {
    private Long id;

    private String description;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private int max_vote;

    private AgendaModel.Status status;

    private AgendaModel.Type type;

    @Builder
    public AgendaResponseDto(AgendaModel entity){
        this.id = entity.getId();
        this.description = entity.getDescription();
        this.startDate = entity.getStartDate();
        this.endDate = entity.getEndDate();
        this.max_vote = entity.getMax_vote();
        this.status = entity.getStatus();
        this.type = entity.getType();
    }
}
