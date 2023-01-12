package com.gabia.evoting.web.dto;

import com.gabia.evoting.domain.VoteModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class VoteResponseDto implements Serializable {

    private String voteStatus;

    private Long userId;

    private Long AgendaId;

    private Long voteSuccessCount;
    private Long voteFailCount;
    private VoteModel.Type type;
    @Builder
    public void VoteRequestDto(String voteStatus, Long userId, Long AgendaId, Long voteSuccessCount, Long voteFailCount, VoteModel.Type type){
        this.voteStatus = voteStatus;
        this.userId = userId;
        this.AgendaId = AgendaId;
        this.voteSuccessCount = voteSuccessCount;
        this.voteFailCount = voteFailCount;
        this.type = type;
    }
}
