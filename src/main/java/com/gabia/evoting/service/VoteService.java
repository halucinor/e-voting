package com.gabia.evoting.service;

import com.gabia.evoting.domain.AgendaModel;
import com.gabia.evoting.domain.UserModel;
import com.gabia.evoting.domain.VoteModel;
import com.gabia.evoting.repository.AgendaRepository;
import com.gabia.evoting.repository.UserRepository;
import com.gabia.evoting.repository.VoteRepository;
import com.gabia.evoting.web.dto.VoteRequestDto;
import com.gabia.evoting.web.dto.VoteResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class VoteService {

    private final AgendaRepository agendaRepository;
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;

    @Transactional(isolation=Isolation.SERIALIZABLE)
    public VoteResponseDto vote(UserModel user,VoteRequestDto voteDto){

//        UserModel user = userRepository.findById(1L).orElseThrow(() -> new IllegalArgumentException("no user id : 1"));
        VoteResponseDto response = new VoteResponseDto();
        // Agenda check
        AgendaModel agenda =  agendaRepository.findById(voteDto.getAgendaId()).orElseThrow(IllegalArgumentException::new);

        response.builder().userId(user.getId()).AgendaId(agenda.getId()).type(voteDto.getType()).build();

        // User check or If Agenda is not started return false
        if(user.getVoteCount() < voteDto.getVoteCount() || agenda.getStatus() != AgendaModel.Status.START){
//            response.builder()
//                    .voteStatus("fail")
//                    .voteFailCount(voteDto.getVoteCount())
//                    .voteSuccessCount(0L).build();
            response.setVoteStatus("fail");
            response.setVoteFailCount(voteDto.getVoteCount());
            response.setVoteSuccessCount(0L);
            return response;
        }

        // Make dummy vote
        VoteModel voteModel = new VoteModel();
        voteModel.setUser(user);
        voteModel.setAgenda(agenda);
        voteModel.setType(voteDto.getType());

        Long successVote = 0L;

        // Agenda Limitation check
        if(agenda.getType() == AgendaModel.Type.LIMIT){

            //If it has limitation, we should check remain vote count
            Long sumOfVote = getSumOfVote(agenda);
            Long remainVote = agenda.getMaxVote() - sumOfVote;

            //If agenda still have remain vote count then make vote
            if(remainVote <= 0) {
                response.setVoteStatus("partial success");
                response.setVoteFailCount(voteDto.getVoteCount());
                response.setVoteSuccessCount(0L);
                return response;
            }

            successVote = Math.min(voteDto.getVoteCount(), remainVote);
            //when make vote, how many vote user can have -> min(remain, user vote count)
            voteModel.setCount(successVote);

        } else if (agenda.getType() == AgendaModel.Type.UNLIMIT) {
            //if Agenda has no Limitation
            successVote = voteDto.getVoteCount();
            voteModel.setCount(successVote);
        }
        // Make Vote
        voteModel.setVotingDateTime(LocalDateTime.now()); //vote time
        voteRepository.save(voteModel);

        updateUserVote(user, successVote);

        response.setVoteStatus("success");
        response.setVoteFailCount(voteDto.getVoteCount() - successVote);
        response.setVoteSuccessCount(successVote);

        return response;
    }

    public Long getSumOfVote(AgendaModel agenda){

        List<VoteModel> voteList = voteRepository.findAllByAgendaId(agenda.getId());
        Long total = 0L;

        // TODO : change with stream map
        for (VoteModel vote : voteList)
            total += vote.getCount();

        return total;
    }

    public void updateUserVote(UserModel user, Long subtract){
        user.setVoteCount(user.getVoteCount() - subtract);
        userRepository.save(user);
    }
}
