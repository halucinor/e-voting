package com.gabia.evoting.web.dto;

import com.gabia.evoting.domain.VoteModel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserInfo {
        Long id;
        String email;

        VoteModel.Type type;

        Long voteCnt;

        @Builder
        public UserInfo(Long id, String email, VoteModel.Type type, Long voteCnt){
                this.id = id;
                this.email = email;
                this.type = type;
                this.voteCnt = voteCnt;
        }
}
