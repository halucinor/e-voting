package com.gabia.evoting.web.dto;

import com.gabia.evoting.domain.VoteModel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserVoteCountDto {
    private Long voteCnt;
}
