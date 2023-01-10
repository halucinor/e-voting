package com.gabia.evoting.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class VoteRequestDto implements Serializable {

    private Long AgendaId;

    private int voteCount;

}
