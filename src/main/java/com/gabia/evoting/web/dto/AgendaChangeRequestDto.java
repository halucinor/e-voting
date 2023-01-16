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
public class AgendaChangeRequestDto implements Serializable {
    private AgendaModel.Status status;
}
