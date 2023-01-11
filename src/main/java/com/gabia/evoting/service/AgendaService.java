package com.gabia.evoting.service;


import com.gabia.evoting.domain.AgendaModel;
import com.gabia.evoting.repository.AgendaRepository;
import com.gabia.evoting.web.dto.AgendaRequestDto;
import com.gabia.evoting.web.dto.AgendaResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

//import javax.transaction.Transactional;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
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

    @Transactional
    public AgendaModel save(AgendaRequestDto agendaDto){
        AgendaModel agenda = AgendaModel.builder()
                .description(agendaDto.getDescription())
                .maxVote(agendaDto.getMaxVote())
                .status(agendaDto.getStatus())
                .type(agendaDto.getType())
                .build();

        agendaRepository.save(agenda);
        return agenda;
    }

    @Transactional
    public void delete(Long agendaId){
        AgendaModel agenda = agendaRepository.findById(agendaId)
                        .orElseThrow(() -> new IllegalArgumentException("no post for the id =" + agendaId));

        agendaRepository.delete(agenda);
    }

    @Transactional
    public AgendaModel startAgenda(Long agendaId){
        AgendaModel agenda = agendaRepository.findById(agendaId)
                .orElseThrow(() -> new IllegalArgumentException("no post for the id =" + agendaId));

        agenda.setStartDateTime(LocalDateTime.now(ZoneId.of("Asia/Seoul")));
        agenda.setStatus(AgendaModel.Status.START);

        agendaRepository.save(agenda);
        return agenda;
    }
    @Transactional
    public AgendaModel endAgenda(Long agendaId){
        AgendaModel agenda = agendaRepository.findById(agendaId)
                .orElseThrow(() -> new IllegalArgumentException("no post for the id =" + agendaId));

        agenda.setEndDateTime(LocalDateTime.now(ZoneId.of("Asia/Seoul")));
        agenda.setStatus(AgendaModel.Status.END);

        agendaRepository.save(agenda);
        return agenda;
    }
}
