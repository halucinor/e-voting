package com.gabia.evoting.service;


import com.gabia.evoting.domain.AgendaModel;
import com.gabia.evoting.repository.AgendaRepository;
import com.gabia.evoting.web.dto.AgendaResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AgendaService {
    private final AgendaRepository agendaRepository;

    public List<AgendaResponseDto> findAll(){
        return agendaRepository.findAll().stream().map(AgendaResponseDto::new).collect(Collectors.toList());
    }
    public Optional<AgendaModel> findById(long agendaId){
        return agendaRepository.findById(agendaId);
    }


    // TODO
    public AgendaModel save(AgendaResponseDto agendaDto){
        AgendaModel agenda = new AgendaModel();

        agenda.setDescription(agendaDto.getDescription());
        agenda.setStartDate(agendaDto.getStartDate());
        agenda.setEndDate(agendaDto.getEndDate());
        agenda.setMax_vote(100000); // TODO change
        agenda.setStatus(agendaDto.getStatus());
        agenda.setType(agendaDto.getType());

        agendaRepository.save(agenda);
        return agenda;
    }

    // TODO
    public void delete(){

    }

}
