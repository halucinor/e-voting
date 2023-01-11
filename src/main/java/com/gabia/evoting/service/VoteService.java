package com.gabia.evoting.service;

import com.gabia.evoting.domain.AgendaModel;
import com.gabia.evoting.domain.UserModel;
import com.gabia.evoting.domain.VoteModel;
import com.gabia.evoting.repository.AgendaRepository;
import com.gabia.evoting.repository.UserRepository;
import com.gabia.evoting.repository.VoteRepository;
import com.gabia.evoting.web.dto.VoteRequestDto;
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
    public boolean Vote(UserModel user, VoteRequestDto voteDto){

        // User check
        if(user.getVoteCount() <= 0)
            return false;

        // Agenda check
        AgendaModel agenda =  agendaRepository.findById(voteDto.getAgendaId()).orElseThrow(IllegalArgumentException::new);

        // If Agenda is not started return false
        if(agenda.getStatus() != AgendaModel.Status.START)
            return false;

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
            if(remainVote <= 0 )
                return false;

            successVote = Math.min(voteDto.getVoteCount(), remainVote);
            //when make vote, how many vote user can have -> min(remain, user vote count)
            voteModel.setCount(successVote);

        } else if (agenda.getType() == AgendaModel.Type.UNLIMIT)
            //if Agenda has no Limitation
            successVote = voteDto.getVoteCount();
            voteModel.setCount(successVote);

        // Make Vote
        voteModel.setVotingDateTime(LocalDateTime.now()); //vote time
        voteRepository.save(voteModel);

        updateUserVote(user, successVote);

        return true;
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
