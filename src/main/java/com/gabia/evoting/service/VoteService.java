package com.gabia.evoting.service;

import com.gabia.evoting.domain.VoteModel;
import com.gabia.evoting.repository.AgendaRepository;
import com.gabia.evoting.repository.UserRepository;
import com.gabia.evoting.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class VoteService {

    private final UserRepository userRepository;

    private final AgendaRepository agendaRepository;

    private final VoteRepository voteRepository;

    public VoteModel vote(){

    }
}
