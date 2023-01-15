package com.gabia.evoting.web.dto;

import com.gabia.evoting.domain.UserModel;
import com.gabia.evoting.domain.VoteModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.User;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AgendaVoteResponseDto {

    private Long agendaId;
    private Long agree = 0L;

    private Long disAgree = 0L;
    private Long absstention = 0L;

    private List<UserInfo> userInfoList;

    public void addVote(VoteModel.Type type, Long count){
        if(type == VoteModel.Type.AGREE) {
            this.agree += count;
        } else if (type == VoteModel.Type.DISAGREE) {
            this.disAgree += count;
        }else {
            this.absstention += count;
        }
    }
}
