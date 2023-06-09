package com.gabia.evoting.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gabia.evoting.domain.AgendaModel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
public class AgendaRequestDto  implements Serializable {
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime endDate;

    private AgendaModel.Status status;

    private int maxVote;

    private AgendaModel.Type type;

    public AgendaRequestDto(AgendaModel agenda){
        this.description = agenda.getDescription();
        this.startDate = agenda.getStartDateTime();
        this.endDate = agenda.getEndDateTime();
        this.status = agenda.getStatus();
        this.maxVote = agenda.getMaxVote();
        this.type = agenda.getType();
    }
}
